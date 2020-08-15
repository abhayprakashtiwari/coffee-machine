package co.apt.service.impl;

import co.apt.constant.OutputConstants;
import co.apt.service.OutputService;

/**
 * This class implementation outputs to console from templated messages in constants to console
 */
public class OutputServiceImpl implements OutputService {

    /**
     * print formatted dispense message from constants to console
     * @param beverageName name of beverage to dispense from one the outlets
     */
    @Override
    public void dispense(String beverageName){
        System.out.printf(OutputConstants.itemPrepared, beverageName);
    }

    /**
     * print formatted item unavailable message from constants to console
     * @param beverageName name of beverage which was to be dispensed
     * @param missingItem  name of item missing in the inventory
     */
    @Override
    public void outputUnavailable(String beverageName, String missingItem){
        System.out.printf(OutputConstants.itemUnavailable, beverageName, missingItem);
    }

    /**
     * print formatted item insufficient message from constants to console
     * @param beverageName     name of beverage which was to be dispensed
     * @param insufficientItem name of item insufficient in the inventory
     */
    @Override
    public void outputInsufficient(String beverageName, String insufficientItem){
        System.out.printf(OutputConstants.itemInsufficient, beverageName, insufficientItem);
    }

    /**
     * print formatted refill required message from constants to console
     * @param lowItem name of item low in quantity to be refilled
     */
    @Override
    public void refillRequired(String lowItem) {
        System.out.printf(OutputConstants.refill, lowItem);
    }

    /**
     * print formatted refilled message from constants to console
     * @param key   name of item refilled
     * @param value quantity with which item was refilled in the inventory
     */
    @Override
    public void refilled(String key, Integer value) {
        System.out.printf(OutputConstants.refilled, key, value);
    }
}
