package com.valr.test.control;

import com.valr.test.control.common.LimitOrderServiceException;
import com.valr.test.model.orderlimit.LimitOrder;
import com.valr.test.model.orderlimit.LimitOrderResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;
import java.util.UUID;

/**
 * User: raisandeepkmr
 * Date: 2021/08/22
 */
public class LimitOrderService {
    public LimitOrderResponse createLimitOrder(String userId, LimitOrder limitOrder) throws LimitOrderServiceException {
        if (InMemoryOrderService.getInstance().placeOrder(userId, limitOrder))
            return LimitOrderResponse.builder().id(UUID.randomUUID().toString()).build();
        throw new LimitOrderServiceException("Cannot create limit order", HttpResponseStatus.INTERNAL_SERVER_ERROR);
    }

    public List<LimitOrder> getTradeHistory(String userId, String currPair) throws LimitOrderServiceException {
        return InMemoryOrderService.getInstance().getTradeHistory(userId, currPair);
    }

    public List<LimitOrder> getOrderBook(String currPair) throws LimitOrderServiceException {
        return InMemoryOrderService.getInstance().getOrderBook(currPair);
    }
}
