package co.apt;

import co.apt.model.Machine;
import co.apt.service.InventoryManagementService;
import co.apt.service.RefillService;
import co.apt.service.impl.InventoryManagementServiceImpl;
import co.apt.service.impl.OutputServiceImpl;
import co.apt.service.impl.RefillServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RefillServiceImplTest {


    RefillService refillService;
    InventoryManagementService inventoryManagementService;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Map<String, Integer> testInventory = Stream.of(new Object[][] {
            {"hot_water", 1000},
            {"hot_milk", 500},
            {"sugar", 10},
            {"tea_leaves_syrup",10},
    }).collect(Collectors.toMap(data -> (String)data[0], data->(Integer)data[1]));


    private Map<String, Integer> beverage1 = Stream.of(new Object[][] {
            {"hot_water", 100},
            {"hot_milk", 50},
            {"sugar", 7}
    }).collect(Collectors.toMap(data -> (String)data[0], data->(Integer)data[1]));

    private Map<String, Integer> beverage2 = Stream.of(new Object[][] {
            {"hot_water", 100},
            {"hot_milk", 50},
            {"sugar", 2},
            {"tea_leaves_syrup", 6}
    }).collect(Collectors.toMap(data -> (String)data[0], data->(Integer)data[1]));




    @Before
    public void init(){
        Machine machine = new Machine();
        machine.setTotalItemsQuantity(testInventory);
        Map<String, Map<String,Integer>> recipes = new HashMap<>();
        recipes.put("beverage1", beverage1);
        recipes.put("beverage2", beverage2);
        machine.setBeverages(recipes);
        inventoryManagementService = new InventoryManagementServiceImpl();
        OutputServiceImpl outputServiceImpl = new OutputServiceImpl();
        refillService = new RefillServiceImpl(machine, outputServiceImpl, inventoryManagementService);
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void test_enumerateItemsRunningLow(){
        inventoryManagementService.modifyInventory(testInventory, beverage1);
        inventoryManagementService.modifyInventory(testInventory,beverage2);
        refillService.enumerateItemsRunningLow(testInventory);
        Assert.assertEquals("Refill required for : tea_leaves_syrup\nRefill required for : sugar\n", outContent.toString());
    }

    @Test
    public void test_refillItem(){
        testInventory.replace("hot_water", 10);
        refillService.refillItem("hot_water", 1000, testInventory);
        Assert.assertTrue(1010 == testInventory.get("hot_water"));
        Assert.assertEquals("Refilled item hot_water by quantity 1000\n", outContent.toString());
    }
}
