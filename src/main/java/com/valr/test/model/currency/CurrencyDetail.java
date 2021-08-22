package com.valr.test.model.currency;

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
public class CurrencyDetail {
    String type;
    String value;
    Long quantity;
}
