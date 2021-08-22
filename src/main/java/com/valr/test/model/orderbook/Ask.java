package com.valr.test.model.orderbook;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Ask {
    String side;
    String quantity;
    String price;
    String currencyPair;
    String orderCount;
}
