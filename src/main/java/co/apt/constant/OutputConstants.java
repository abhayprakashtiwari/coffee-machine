package co.apt.constant;

/**
 * Constants in template form to be used by various output services
 */
public interface OutputConstants {

    String configFile = "config.json";

    String itemUnavailable = "%s cannot be prepared because %s is not available\n";

    String itemInsufficient = "%s cannot be prepared because item %s is not sufficient\n";

    String itemPrepared = "%s is prepared\n";

    String refill = "Refill required for : %s\n";

    String refilled = "Refilled item %s by quantity %d\n";
}
