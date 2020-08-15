package co.apt.service.impl;

import co.apt.model.Machine;
import co.apt.service.InventoryManagementService;
import co.apt.service.OutputService;
import co.apt.service.RefillService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implementation precomputes minimum values required in the inventory from all the recipes of beverages
 */
public class RefillServiceImpl implements RefillService {

    private Map<String, Integer> minItemValueNeeded = new HashMap<>();
    private OutputService outputService;
    private InventoryManagementService inventoryManagementService;


    /**
     * This constructor precomputes minimum item values in the recipes and sets to member
     * @param machine reference of inventory store and all recipes from the configuration
     * @param outputService service to output the beverage as per the response
     * @param inventoryManagementService service which manages modification of inventory for verification and dispensing
     */
    public RefillServiceImpl(Machine machine, OutputService outputService, InventoryManagementService inventoryManagementService){
        preComputeMinItemValues(machine);
        this.outputService = outputService;
        this.inventoryManagementService = inventoryManagementService;
    }

    /**
     * Initialize minimum items as inventory values, then for each recipe compare the minimum value for key and set to
     * min value. Also, ingredient not included in inventory from a recipe is ignored as minimum value required
     * @param machine reference of inventory store and all recipes from the configuration
     *
     */
    private void preComputeMinItemValues(Machine machine) {
        minItemValueNeeded.putAll(machine.getTotalItemsQuantity());
        Collection<Map<String, Integer>> recipes = machine.getBeverages().values();
        for(Map<String,Integer> recipe : recipes){
            for(Map.Entry<String,Integer> entry : recipe.entrySet()){
                if(minItemValueNeeded.containsKey(entry.getKey())  &&  entry.getValue() < minItemValueNeeded.get(entry.getKey())){
                    minItemValueNeeded.replace(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    /**
     * For each item in inventory compare the value with minimum required and pass the key to output service for message
     * @param inventory map of items and quantity values
     */
    @Override
    public void enumerateItemsRunningLow(Map<String,Integer> inventory){
        for(Map.Entry<String,Integer> item : inventory.entrySet()){
            if(item.getValue()<minItemValueNeeded.get(item.getKey())){
                outputService.refillRequired(item.getKey());
            }
        }
    }

    /**
     * Call refill item from inventory service and output service to message item refilled
     * @param key       name of the item to be refilled
     * @param value     quantity of the item to be refilled in the inventory
     * @param inventory map of items and quantity values
     */
    @Override
    public void refillItem(String key, Integer value, Map<String, Integer> inventory){
        inventoryManagementService.refillItem(key, value, inventory);
        outputService.refilled(key,value);
    }


}
