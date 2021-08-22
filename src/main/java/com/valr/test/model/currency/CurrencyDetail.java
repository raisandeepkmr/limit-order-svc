package com.valr.test.model.currency;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyDetail {
    String type;
    String value;
    String quantity;
}
