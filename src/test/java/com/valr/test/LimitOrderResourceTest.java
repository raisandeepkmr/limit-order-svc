package com.valr.test;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
class LimitOrderResourceTest {
    Vertx vertx = Vertx.vertx();

    @BeforeEach
    void setUp() throws Throwable  {
        VertxTestContext testContext = new VertxTestContext();

//        vertx.deployVerticle(LimitOrderApp.class.getName(),
//                testContext.asyncAssertSuccess());
    }

    @AfterEach
    void tearDown() {
    }
}