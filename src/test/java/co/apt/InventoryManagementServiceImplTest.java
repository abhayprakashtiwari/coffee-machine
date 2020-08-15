package co.apt;

import co.apt.service.InventoryManagementService;
import co.apt.service.impl.InventoryManagementServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InventoryManagementServiceImplTest {

    private InventoryManagementService inventoryManagementService;

    private Map<String, Integer> testInventory = Stream.of(new Object[][] {
            {"hot_water", 1000},
            {"hot_milk", 500},
            {"sugar", 10},
    }).collect(Collectors.toMap(data -> (String)data[0], data->(Integer)data[1]));

    private Map<String, Integer> testRecipe = Stream.of(new Object[][] {
            {"hot_water", 100},
            {"hot_milk", 50},
            {"sugar", 1}
    }).collect(Collectors.toMap(data -> (String)data[0], data->(Integer)data[1]));

    @Before
    public void init(){
        inventoryManagementService = new InventoryManagementServiceImpl();
    }

    @Test
    public void test_modifyInventory(){
        inventoryManagementService.modifyInventory(testInventory, testRecipe);
        Map<String, Integer> resultInventoryMap = new HashMap<>();
        resultInventoryMap.put("hot_water",900);
        resultInventoryMap.put("hot_milk",450);
        resultInventoryMap.put("sugar",9);
        Assert.assertTrue(areEqual(testInventory, resultInventoryMap));
    }

    private boolean areEqual(Map<String, Integer> first, Map<String, Integer> second) {
        if (first.size() != second.size()) {
            return false;
        }
        return first.entrySet().stream()
                .allMatch(e -> e.getValue().equals(second.get(e.getKey())));
    }

    @Test
    public void test_missingItemInInventory(){
        testRecipe.put("ginger_syrup",5);
        String missingItem = inventoryManagementService.missingItemInInventory(testInventory, testRecipe);
        Assert.assertSame(missingItem, "ginger_syrup");
        testRecipe.remove("ginger_syrup");
    }

    @Test
    public void test_insufficientItemInInventory(){
        testRecipe.replace("sugar", 15);
        String insufficientItem = inventoryManagementService.insufficientItemInInventory(testInventory, testRecipe);
        Assert.assertSame(insufficientItem, "sugar");
        testRecipe.replace("sugar",10);
    }

    @Test
    public void test_refillItem(){
        inventoryManagementService.refillItem("sugar", 35, testInventory);
        Assert.assertSame(testInventory.get("sugar"), 45);
    }


}
