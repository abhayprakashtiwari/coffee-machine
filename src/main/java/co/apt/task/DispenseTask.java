package co.apt.task;

import co.apt.model.Config;
import co.apt.service.InventoryManagementService;
import co.apt.service.OutputService;

import java.util.Map;

/**
 * DispenseTask represents each order in the queue (pool). Instance needs to beverage name to be prepared, configuration
 * reference, inventory and output service reference to run. When instantiated and queued in the executor at the time of
 *  execution the instance run fetches recipe of the beverage first. Acquires lock to the inventory store and checks for
 *  item missing or insufficient, if all ingredients are available modifies the inventory with consumed quantities of
 *  ingredients and releases to the lock to dispense the beverage. In case when an item is unavailable or insufficient
 *  it returns releasing the acquired lock with modifiying or dispensing beverage.
 */
public class DispenseTask implements Runnable {

    private String beverageName;
    private Config config;
    private InventoryManagementService inventoryManagementService;
    private OutputService outputService;

    /**
     * Constructor to build a task for dispensing a beverage
     * @param beverageName beverage to be dispensed
     * @param config configuration of application with recipe and inventory state
     * @param inventoryManagementService service which manages modification of inventory for verification and dispensing
     * @param outputService service to output the beverage as per the response
     */
    public DispenseTask(String beverageName, Config config, InventoryManagementService inventoryManagementService, OutputService outputService){
        this.beverageName = beverageName;
        this.config = config;
        this.inventoryManagementService = inventoryManagementService;
        this.outputService = outputService;
    }

    /**
     * Fetch recipe from config reference.
     * Acquire lock from inventory store
     * Verify any item missing or insufficient if yes return
     * If all item available modify inventory and release the lock, dispense the beverage
     */
    @Override
    public void run() {
        Map<String, Integer> recipe = fetchRecipe(beverageName);
        try {
            inventoryManagementService.acquireLock();
            if(missingItem(recipe) || insufficientItem(recipe)) return;
            inventoryManagementService.modifyInventory(config.getMachine().getTotalItemsQuantity(), recipe);
        } finally {
            inventoryManagementService.releaseLock();
        }
        dispense(beverageName);
    }

    private boolean missingItem(Map<String, Integer> recipe){
        String item = inventoryManagementService.missingItemInInventory(config.getMachine().getTotalItemsQuantity(), recipe);
        if(item!=null){
            itemUnavailable(beverageName, item);
            return true;
        }
        return false;
    }

    private boolean insufficientItem(Map<String, Integer> recipe){
        String item = inventoryManagementService.insufficientItemInInventory(config.getMachine().getTotalItemsQuantity(), recipe);
        if(item!=null){
            itemInsufficient(beverageName, item);
            return true;
        }
        return false;
    }

    private void dispense(String beverageName) {
        outputService.dispense(beverageName);
    }

    private void itemInsufficient(String beverageName, String item) {
        outputService.outputInsufficient(beverageName, item);
    }

    private void itemUnavailable(String beverageName, String item) {
        outputService.outputUnavailable(beverageName, item);
    }

    private Map<String, Integer> fetchRecipe(String beverageName) {
        Map<String, Integer> recipe = this.config.getMachine().getBeverages().get(beverageName);
        return recipe;
    }

}
