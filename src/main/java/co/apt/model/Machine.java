package co.apt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Machine {

    private Map<String, Integer> outlets;

    @JsonProperty("total_items_quantity")
    private ConcurrentHashMap<String, Integer> totalItemsQuantity;

    private Map<String, Map<String, Integer>> beverages;

}
