package com.valr.test.boundry;

import com.valr.test.model.orderlimit.LimitOrder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
class LimitOrderResourceTest {
    private static LimitOrder limitOrder;
    static {
        limitOrder = LimitOrder.builder()
                .side("SELL")
                .quantity("123")
                .price("1000")
                .pair("BTCZAR")
                .postOnly(true)
                .customerOrderId("123")
                .timeInForce("12:00")
                .build();
    }

    @BeforeAll
    @DisplayName("Deploy a verticle")
    static void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
        vertx.deployVerticle(new LimitOrderResource(), testContext.succeeding(id -> testContext.completeNow()));
    }

    @Test
    @DisplayName("Health check test")
    void testHealth(Vertx vertx, VertxTestContext testContext) {
        HttpClient client = vertx.createHttpClient();
        client.request(HttpMethod.GET, 8080, "localhost", "/health")
            .compose(req -> req.send().compose(HttpClientResponse::body))
            .onComplete(testContext.succeeding(buffer -> {
            testContext.verify(() -> {
                JsonObject result = buffer.toJsonObject();
                Assertions.assertTrue(result.getString("status").equals("UP"));
                testContext.completeNow();
            });
        }));
    }

    @Test
    @DisplayName("Place limit order test!")
    void testPlaceLimitOrder(Vertx vertx, VertxTestContext testContext) {
        WebClient client = WebClient.create(vertx);
        client.post(8080, "localhost", "/orders/limit")
                .putHeader("x-user-id","testuser")
                .sendJson(limitOrder)
                .onSuccess(result -> {
                    Assertions.assertEquals(result.statusCode(), HttpResponseStatus.CREATED.code());
                    testContext.completeNow();
                });
    }

    @Test
    @DisplayName("Get 404 for trade history test!")
    void testGetOrderBook(Vertx vertx, VertxTestContext testContext) {
        WebClient client = WebClient.create(vertx);
        client.get(8080, "localhost", "/BTCZAR/tradehistory")
                .putHeader("x-user-id","testuser1")
                .send()
                .onSuccess(result -> {
                    Assertions.assertEquals(result.statusCode(), HttpResponseStatus.NOT_FOUND.code());
                    testContext.completeNow();
                });
    }

    @Test
    @DisplayName("Get trade history test!")
    void testValidGetOrderBook(Vertx vertx, VertxTestContext testContext) {
        testPlaceLimitOrder(vertx, testContext);
        WebClient client = WebClient.create(vertx);
        client.get(8080, "localhost", "/BTCZAR/tradehistory")
                .putHeader("x-user-id","testuser")
                .send()
                .onSuccess(result -> {
                    Assertions.assertEquals(result.statusCode(), HttpResponseStatus.OK.code());
                    testContext.completeNow();
                });
    }

    @Test
    @DisplayName("Get order book test!")
    void testGetRecentTrades(Vertx vertx, VertxTestContext testContext) {
        testPlaceLimitOrder(vertx, testContext);
        WebClient client = WebClient.create(vertx);
        client.get(8080, "localhost", "/BTCZAR/orderbook")
                .putHeader("x-user-id","testuser")
                .send()
                .onSuccess(result -> {
                    Assertions.assertEquals(result.statusCode(), HttpResponseStatus.OK.code());
                    testContext.completeNow();
                });
    }

    @AfterAll
    @DisplayName("Tear down vertx!")
    static void tearDown(Vertx vertx, VertxTestContext testContext) {
        vertx.close(testContext.succeeding(response -> {
            testContext.completeNow();
        }));
    }
}