package co.apt.service;

import co.apt.model.Machine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class RefillService {

    Map<String, Integer> minItemValueNeeded = new HashMap<>();
    private OutputService outputService;
    private InventoryManagementService inventoryManagementService;

    /**
     * @param machine
     * @param outputService
     * @param inventoryManagementService
     */
    public RefillService(Machine machine, OutputService outputService, InventoryManagementService inventoryManagementService){
        preComputeMinItemValues(machine);
        this.outputService = outputService;
        this.inventoryManagementService = inventoryManagementService;
    }

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
     * @param inventory
     */
    public void enumerateItemsRunningLow(Map<String,Integer> inventory){
        for(Map.Entry<String,Integer> item : inventory.entrySet()){
            if(item.getValue()<minItemValueNeeded.get(item.getKey())){
                outputService.refillRequired(item.getKey());
            }
        }
    }

    /**
     * @param key
     * @param value
     * @param inventory
     */
    public void refillItem(String key, Integer value, Map<String, Integer> inventory){
        inventoryManagementService.refillItem(key, value, inventory);
        outputService.refilled(key,value);
    }


}
