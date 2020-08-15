package co.apt.service;

import java.util.Map;

/**
 * Inventory management service handles all the checks, modification and refilling the inventory items.
 * Inorder to avoid multiple threads modifying inventory at once, service exposes acquire and release lock
 */
public interface InventoryManagementService {

    /**
     * Locks inventory resource, to be used when threads concurrently reads/writes
     */
    void acquireLock();

    /**
     * Release lock acquired after reading/writing inventory resource
     */
    void releaseLock();

    /**
     * Modify inventory ingredients as per the recipe ingredients
     * @param inventory map of items and quantity values
     * @param recipe map of ingredients required with quantity as corresponding values
     */
    void modifyInventory(Map<String, Integer> inventory, Map<String, Integer> recipe);

    /**
     * Determine missing ingredient in the inventory from ingredients required for recipe
     * @param inventory map of items and quantity values
     * @param recipe map of ingredients required with quantity as corresponding values
     * @return missing ingredient name
     */
    String missingItemInInventory(Map<String, Integer> inventory, Map<String, Integer> recipe);

    /**
     * Determine insufficient ingredient in the inventory quantity as per the corresponding recipe ingredient quantities
     * @param inventory map of items and quantity values
     * @param recipe map of ingredients required with quantity as corresponding values
     * @return insufficient ingredient name
     */
    String insufficientItemInInventory(Map<String, Integer> inventory, Map<String, Integer> recipe);

    /**
     * Refill item in inventory with quantity value
     * @param item item name to be refilled in the inventory
     * @param quantity value to be refilled by in the inventory
     * @param inventory map of items and quantity values
     */
    void refillItem(String item, Integer quantity, Map<String,Integer> inventory);
}
