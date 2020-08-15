package co.apt;

import co.apt.config.ConfigParser;
import co.apt.service.DispenseTask;
import co.apt.service.InventoryManagementService;
import co.apt.service.OutputService;
import co.apt.service.RefillService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Coffee machine
 *
 */
public class CoffeeMachine
{
    public static void main( String[] args ) throws InterruptedException, ExecutionException {
        System.out.println( "Coffee machine" );
        ConfigParser configParser = new ConfigParser();
        try {
            configParser.loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputService outputService = new OutputService();
        InventoryManagementService inventoryManagementService = new InventoryManagementService();
        RefillService refillService = new RefillService(configParser.config.getMachine(), outputService, inventoryManagementService);
        ExecutorService service = Executors.newFixedThreadPool(configParser.config.getMachine().getOutlets().get("count_n"));
        String[] order1 = {"hot_tea", "hot_coffee", "black_tea", "green_tea"};
        String[] order2 = {"hot_tea", "black_tea","green_tea", "hot_coffee"};
        String[] order3 = {"hot_coffee", "black_tea","green_tea", "hot_tea"};
        for(String order : order2){
            service.submit(new DispenseTask(order, configParser.config, inventoryManagementService, outputService));
        }

        service.shutdown();
        Thread.sleep(3000);
        refillService.enumerateItemsRunningLow(configParser.config.getMachine().getTotalItemsQuantity());
        refillService.refillItem("hot_water",1000, configParser.config.getMachine().getTotalItemsQuantity());
    }


}
