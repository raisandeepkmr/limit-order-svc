package com.valr.test.control;

import com.valr.test.model.currency.CurrencyDetail;

import java.util.HashMap;
import java.util.Map;

public class InMemoryOrderService {
    Map<String, CurrencyDetail> currencies = new HashMap<>();
    Map<String, String> orders = new HashMap<>();
}
