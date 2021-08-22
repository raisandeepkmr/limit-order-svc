package com.valr.test.model.orderlimit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: raisandeepkmr
 * Date: 2021/08/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LimitOrder {
    String side;
    String quantity;
    String price;
    String pair;
    Boolean postOnly;
    String customerOrderId;
    String timeInForce;
}
