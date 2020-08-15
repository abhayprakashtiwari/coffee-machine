package co.apt;

import co.apt.config.Configuration;
import co.apt.model.Config;
import co.apt.model.Machine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigurationTest {

    Configuration configuration;

    private Map<String, Integer> testInventory = Stream.of(new Object[][] {
            {"hot_water", 500},
            {"hot_milk", 500},
            {"sugar_syrup", 100},
            {"tea_leaves_syrup",30},
    }).collect(Collectors.toMap(data -> (String)data[0], data->(Integer)data[1]));


    private Map<String, Integer> beverage1 = Stream.of(new Object[][] {
            {"hot_water", 200},
            {"hot_milk", 100},
            {"tea_leaves_syrup", 30}
    }).collect(Collectors.toMap(data -> (String)data[0], data->(Integer)data[1]));

    private Map<String, Integer> beverage2 = Stream.of(new Object[][] {
            {"hot_water", 100},
            {"sugar_syrup", 50},
    }).collect(Collectors.toMap(data -> (String)data[0], data->(Integer)data[1]));



    @Before
    public void init(){
        configuration = new Configuration();
    }

    @Test
    public void test_loadConfig(){
        try {
            configuration.loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Config expectedConfig = buildExpectedConfig();
        Assert.assertEquals(expectedConfig, configuration.config);
    }

    private Config buildExpectedConfig() {
        Config expectedConfig = new Config();
        Machine machine = new Machine();
        Map<String, Integer> outlets = new HashMap<>();
        outlets.put("count_n",1);
        machine.setOutlets(outlets);
        Map<String, Map<String,Integer>> recipes = new HashMap<>();
        recipes.put("hot_tea", beverage1);
        recipes.put("hot_coffee", beverage2);
        machine.setTotalItemsQuantity(testInventory);
        machine.setBeverages(recipes);
        expectedConfig.setMachine(machine);
        return expectedConfig;
    }


}
