package com.valr.test.control;

import com.valr.test.control.common.LimitOrderServiceException;
import com.valr.test.model.orderlimit.LimitOrder;

import java.util.List;

/**
 * User: raisandeepkmr
 * Date: 2021/08/22
 */
public class LimitOrderService {
    public String createLimitOrder(String userId, LimitOrder limitOrder) throws LimitOrderServiceException {
        if (InMemoryOrderService.getInstance().placeOrder(userId, limitOrder))
            return "Limit order created successfully.";
        return "Cannot create limit order at the moment.";
    }

    public List<LimitOrder> getTradeHistory(String userId, String currPair) throws LimitOrderServiceException {
        return InMemoryOrderService.getInstance().getTradeHistory(userId, currPair);
    }

    public List<LimitOrder> getOrderBook(String currPair) throws LimitOrderServiceException {
        return InMemoryOrderService.getInstance().getOrderBook(currPair);
    }
}
