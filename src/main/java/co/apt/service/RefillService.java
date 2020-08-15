package co.apt.service;

import java.util.Map;

/**
 * Refill service identifies item running low as per the minimum quantity of items required by any available recipe.
 * Refills item in the inventory with the quantity.
 */
public interface RefillService {
    /**
     * Evaluate items low in quantity in the inventory
     * @param inventory map of items and quantity values
     */
    void enumerateItemsRunningLow(Map<String,Integer> inventory);

    /**
     * Refill item in inventory with value
     * @param key name of the item to be refilled
     * @param value quantity of the item to be refilled in the inventory
     * @param inventory  map of items and quantity values
     */
    void refillItem(String key, Integer value, Map<String, Integer> inventory);
}
