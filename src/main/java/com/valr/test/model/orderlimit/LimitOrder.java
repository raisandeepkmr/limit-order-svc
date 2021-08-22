package com.valr.test.model.orderlimit;

import lombok.Data;

@Data
public class LimitOrder {
    String side;
    String quantity;
    String price;
    String pair;
    Boolean postOnly;
    String customerOrderId;
    String timeInForce;
}
