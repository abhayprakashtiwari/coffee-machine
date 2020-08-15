package co.apt.service;

import co.apt.constant.OutputConstants;

public class OutputService {

    /**
     * @param beverageName
     */
    public void dispense(String beverageName){
        System.out.printf(OutputConstants.itemPrepared, beverageName);
    }

    /**
     * @param beverageName
     * @param missingItem
     */
    public void outputUnavailable(String beverageName, String missingItem){
        System.out.printf(OutputConstants.itemUnavailable, beverageName, missingItem);
    }

    /**
     * @param beverageName
     * @param insufficientItem
     */
    public void outputInsufficient(String beverageName, String insufficientItem){
        System.out.printf(OutputConstants.itemInsufficient, beverageName, insufficientItem);
    }

    /**
     * @param lowItem
     */
    public void refillRequired(String lowItem) {
        System.out.printf(OutputConstants.refill, lowItem);
    }

    /**
     * @param key
     * @param value
     */
    public void refilled(String key, Integer value) {
        System.out.printf(OutputConstants.refilled, key, value);
    }
}
