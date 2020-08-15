package co.apt.service;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class InventoryManagementService {

    public ReentrantLock mutex = new ReentrantLock();

    /**
     * @param inventory
     * @param recipe
     */
    public void modifyInventory(Map<String, Integer> inventory, Map<String, Integer> recipe){
        recipe.forEach((k,v)-> inventory.put(k, inventory.get(k)-v));
    }

    /**
     * @param inventory
     * @param recipe
     * @return
     */
    public String missingItemInInventory(Map<String, Integer> inventory, Map<String, Integer> recipe){
        for (String ingredient : recipe.keySet()){
            if(!inventory.containsKey(ingredient)) return ingredient;
        }
        return null;
    }

    /**
     * @param inventory
     * @param recipe
     * @return
     */
    public String insufficientItemInInventory(Map<String, Integer> inventory, Map<String, Integer> recipe){
        for(Map.Entry<String, Integer> entry : recipe.entrySet()){
            if(entry.getValue() > inventory.get(entry.getKey())) return entry.getKey();
        }
        return null;
    }

    /**
     * @param item
     * @param quantity
     * @param inventory
     */
    public void refillItem(String item, Integer quantity, Map<String,Integer> inventory){
        inventory.put(item, inventory.get(item)+quantity);
    }


}
