package com.valr.test.control;

import com.valr.test.control.common.TestHelperUtil;
import com.valr.test.model.orderbook.OrderBook;

public class OrderBookService {
    public OrderBook getOrderBook(String currPair) {
        return TestHelperUtil.testOrderBook();
    }
}
