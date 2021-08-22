package com.valr.test.boundry;

import com.valr.test.control.LimitOrderService;
import com.valr.test.control.OrderBookService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LimitOrderResource extends AbstractVerticle {
    LimitOrderService limitOrderService = new LimitOrderService();
    OrderBookService orderBookService = new OrderBookService();

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        log.info("Starting vertx server!!");
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        //***************************************ORDER BOOK ROUTE
        router.get("/:currPair/orderbook")
        .respond(ctx -> {
            String currPair = ctx.pathParam("currPair");
            return Future.succeededFuture(orderBookService.getOrderBook(currPair));
        });
        //***************************************LIMIT ORDER ROUTE
        router.post("/orders/limit")
        .handler(BodyHandler.create())
        .respond(ctx -> {
            JsonObject limitOrder = ctx.getBodyAsJson();
            return Future.succeededFuture(limitOrderService.createLimitOrder(limitOrder));
        });
        server.requestHandler(router).listen(8080);
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        log.info("Shutting down vertx!!");
    }
}
