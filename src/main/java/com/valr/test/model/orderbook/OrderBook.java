package com.valr.test.model.orderbook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * User: raisandeepkmr
 * Date: 2021/08/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderBook {
    @JsonProperty("Asks")
    List<Ask> asks;
}

