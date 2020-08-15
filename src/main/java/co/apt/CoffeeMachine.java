package co.apt;

import co.apt.config.Configuration;
import co.apt.task.DispenseTask;
import co.apt.service.InventoryManagementService;
import co.apt.service.OutputService;
import co.apt.service.RefillService;
import co.apt.service.impl.InventoryManagementServiceImpl;
import co.apt.service.impl.OutputServiceImpl;
import co.apt.service.impl.RefillServiceImpl;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Coffee machine
 *
 */
public class CoffeeMachine
{
    public static void main( String[] args ) {
        //Load configuration
        Configuration configuration = new Configuration();
        try {
            configuration.loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Initialize services
        OutputService outputService = new OutputServiceImpl();
        InventoryManagementService inventoryManagementService = new InventoryManagementServiceImpl();
        RefillService refillService = new RefillServiceImpl(configuration.config.getMachine(), outputService, inventoryManagementService);

        //Based of no of outlets maintain thread pool of the count size
        ExecutorService service = Executors.newFixedThreadPool(configuration.config.getMachine().getOutlets().get("count_n"));
        String[] order1 = {"hot_tea", "hot_coffee", "black_tea", "green_tea"};
//        String[] order2 = {"hot_tea", "black_tea","green_tea", "hot_coffee"};
//        String[] order3 = {"hot_coffee", "black_tea","green_tea", "hot_tea"};
        for(String order : order1){
            service.submit(new DispenseTask(order, configuration.config, inventoryManagementService, outputService));
        }
        service.shutdown();

        //Sleep 1 sec for all threads to finish
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Refill service calls
        refillService.enumerateItemsRunningLow(configuration.config.getMachine().getTotalItemsQuantity());
        refillService.refillItem("hot_water",1000, configuration.config.getMachine().getTotalItemsQuantity());
    }


}
