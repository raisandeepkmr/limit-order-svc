package com.valr.test.control;

import com.valr.test.control.common.LimitOrderServiceException;
import com.valr.test.model.currency.CurrencyDetail;
import com.valr.test.model.orderlimit.LimitOrder;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.*;
import java.util.stream.Collectors;

/**
 * User: raisandeepkmr
 * Date: 2021/08/22
 */
public class InMemoryOrderService {
    private static InMemoryOrderService instance;
    private static Map<String, CurrencyDetail> currencies = new HashMap<>();
    private static Map<String, List<LimitOrder>> orders = new HashMap<>();

    private InMemoryOrderService() {
        currencies.put("BTC", CurrencyDetail.builder().type("BTC").value("750000").quantity(1000L).build());
        currencies.put("ETH", CurrencyDetail.builder().type("XRP").value("50000").quantity(10000L).build());
        currencies.put("XRP", CurrencyDetail.builder().type("XRP").value("25").quantity(15000L).build());
        currencies.put("DOT", CurrencyDetail.builder().type("DOT").value("540").quantity(8000L).build());
    }

    public static synchronized InMemoryOrderService getInstance() {
        if(instance == null) {
            instance = new InMemoryOrderService();
        }
        return instance;
    }

    /**
     *
     * @param userId
     * @param limitOrder
     * @return
     * @throws LimitOrderServiceException
     */
    public synchronized boolean placeOrder(String userId, LimitOrder limitOrder) throws LimitOrderServiceException {
        if(orders.keySet().contains(userId)) {
            orders.get(userId).add(limitOrder);
        } else {
            List<LimitOrder> userLimitOrders = new ArrayList<>();
            userLimitOrders.add(limitOrder);
            orders.put(userId, userLimitOrders);
        }
        return true;
    }

    /**
     *
     * @param currPair
     * @return
     * @throws LimitOrderServiceException
     */
    public synchronized List<LimitOrder> getOrderBook(String currPair) throws LimitOrderServiceException {
        List<LimitOrder> limitOrders = orders.entrySet().stream().map(Map.Entry::getValue).flatMap(Collection::stream)
                .filter(x -> x.getPair().equals(currPair)).collect(Collectors.toList());
        return limitOrders;
    }

    /**
     *
     * @param userId
     * @param currPair
     * @return
     * @throws LimitOrderServiceException
     */
    public synchronized List<LimitOrder> getTradeHistory(String userId, String currPair) throws LimitOrderServiceException {
        if(orders.get(userId) == null) throw new LimitOrderServiceException("No trade history available for user: " + userId, HttpResponseStatus.NOT_FOUND);
        return orders.get(userId).stream().filter(x -> x.getPair().equals(currPair)).collect(Collectors.toList());
    }
}