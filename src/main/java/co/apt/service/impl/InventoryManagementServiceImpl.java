package co.apt.service.impl;

import co.apt.service.InventoryManagementService;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class implementation maintains mutex to be handled by service consumer during read/write operations in case of
 * concurrent calls
 */
public class InventoryManagementServiceImpl implements InventoryManagementService {

    private ReentrantLock mutex = new ReentrantLock();

    /**
     * Reduces value of the item by corresponding value from recipe
     * @param inventory map of items and quantity values
     * @param recipe    map of ingredients required with quantity as corresponding values
     */
    @Override
    public void modifyInventory(Map<String, Integer> inventory, Map<String, Integer> recipe){
        recipe.forEach((k,v)-> inventory.put(k, inventory.get(k)-v));
    }

    /**
     * Return missing key (item name) from inventory comparing keys (items required) in recipe
     * @param inventory map of items and quantity values
     * @param recipe    map of ingredients required with quantity as corresponding values
     * @return
     */
    @Override
    public String missingItemInInventory(Map<String, Integer> inventory, Map<String, Integer> recipe){
        for (String ingredient : recipe.keySet()){
            if(!inventory.containsKey(ingredient)) return ingredient;
        }
        return null;
    }

    /**
     * Return key for which the inventory value (ingredient quantity) is less than required from recipe key (ingredient)
     * @param inventory map of items and quantity values
     * @param recipe    map of ingredients required with quantity as corresponding values
     * @return
     */
    @Override
    public String insufficientItemInInventory(Map<String, Integer> inventory, Map<String, Integer> recipe){
        for(Map.Entry<String, Integer> entry : recipe.entrySet()){
            if(entry.getValue() > inventory.get(entry.getKey())) return entry.getKey();
        }
        return null;
    }

    /**
     * Increment the existing value in inventory by the value (quantity) passed for the item (key)
     * @param item      item name to be refilled in the inventory
     * @param quantity  value to be refilled by in the inventory
     * @param inventory map of items and quantity values
     */
    @Override
    public void refillItem(String item, Integer quantity, Map<String,Integer> inventory){
        inventory.put(item, inventory.get(item)+quantity);
    }

    /**
     * lock mutex maintained by the instance
     */
    @Override
    public void acquireLock() {
        mutex.lock();
    }

    /**
     * release mutex lock maintained by the instance
     */
    @Override
    public void releaseLock() {
        mutex.unlock();
    }
}
