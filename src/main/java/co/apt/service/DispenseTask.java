package co.apt.service;

import co.apt.model.Config;

import java.util.Map;

/**
 *
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
     *
     */
    @Override
    public void run() {
        Map<String, Integer> recipe = fetchRecipe(beverageName);
        try {
            inventoryManagementService.mutex.lock();
            if(missingItem(recipe) || insufficientItem(recipe)) return;
            inventoryManagementService.modifyInventory(config.getMachine().getTotalItemsQuantity(), recipe);
        } finally {
            inventoryManagementService.mutex.unlock();
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
