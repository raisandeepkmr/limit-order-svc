package com.valr.test.control;

import io.vertx.core.json.JsonObject;

public class LimitOrderService {
    public String createLimitOrder(JsonObject limitOrder) {
        return "Limit order created! Side: " + limitOrder.getString("side");
    }
}
