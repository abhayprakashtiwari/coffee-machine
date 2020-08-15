package co.apt.service;

/**
 * Output service to be used as one way communication interface for the user
 */
public interface OutputService {
    /**
     * Dispense beverage from the outlet
     * @param beverageName name of beverage to dispense from one the outlets
     */
    void dispense(String beverageName);

    /**
     * Output message for item unavailable
     * @param beverageName name of beverage which was to be dispensed
     * @param missingItem name of item missing in the inventory
     */
    void outputUnavailable(String beverageName, String missingItem);

    /**
     * Output message for insufficient item
     * @param beverageName name of beverage which was to be dispensed
     * @param insufficientItem name of item insufficient in the inventory
     */
    void outputInsufficient(String beverageName, String insufficientItem);

    /**
     * Output message for item which needs refill
     * @param lowItem name of item low in quantity to be refilled
     */
    void refillRequired(String lowItem);

    /**
     * Output message for item refilled
     * @param key name of item refilled
     * @param value quantity with which item was refilled in the inventory
     */
    void refilled(String key, Integer value);
}
