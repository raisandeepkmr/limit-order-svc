package com.valr.test.model.orderbook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderBook {
    @JsonProperty("Asks")
    List<Ask> asks;
}

