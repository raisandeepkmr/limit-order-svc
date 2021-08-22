package com.valr.test;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class LimitOrderAppTest extends AbstractVerticle {
    Vertx vertx = Vertx.vertx();

    @BeforeEach
    void setUp(VertxTestContext testContext) {
        vertx = Vertx.vertx();

//        vertx.deployVerticle(LimitOrderApp.class.getName(),
//                testContext.asyncAssertSuccess());
    }

    @AfterEach
    void tearDown() {
    }
}