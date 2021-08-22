package com.valr.test;

import com.valr.test.boundry.LimitOrderResource;
import io.vertx.core.Vertx;

/**
 * User: raisandeepkmr
 * Date: 2021/08/22
 */
public class Main
{
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new LimitOrderResource());
    }
}
