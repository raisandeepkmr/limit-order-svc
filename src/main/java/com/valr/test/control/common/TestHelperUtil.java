package com.valr.test.control.common;

import com.valr.test.model.orderbook.Ask;
import com.valr.test.model.orderbook.OrderBook;

import java.util.ArrayList;

public class TestHelperUtil {
    public static OrderBook testOrderBook() {
        OrderBook orderBook = OrderBook.builder().asks(new ArrayList<>()).build();
        orderBook.getAsks().add(Ask.builder()
                .side("SELL")
                .quantity("123")
                .price("3000")
                .currencyPair("USDZAR")
                .orderCount("1000")
                .build());
        orderBook.getAsks().add(Ask.builder()
                .side("BUY")
                .quantity("123")
                .price("3000")
                .currencyPair("USDZAR")
                .orderCount("1000")
                .build());
        orderBook.getAsks().add(Ask.builder()
                .side("SELL")
                .quantity("123")
                .price("3000")
                .currencyPair("USDCAD")
                .orderCount("1000")
                .build());
        return orderBook;
    }
}
