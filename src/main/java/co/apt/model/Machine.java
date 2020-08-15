package co.apt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * Class to parse and hold config, recipe and inventory
 */
@Data
public class Machine {

    private Map<String, Integer> outlets;

    @JsonProperty("total_items_quantity")
    private Map<String, Integer> totalItemsQuantity;

    private Map<String, Map<String, Integer>> beverages;

}
