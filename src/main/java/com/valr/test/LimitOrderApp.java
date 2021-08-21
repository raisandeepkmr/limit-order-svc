package com.valr.test;

import com.valr.test.control.LimitOrderService;
import com.valr.test.control.OrderBookService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LimitOrderApp extends AbstractVerticle {
    LimitOrderService limitOrderService = new LimitOrderService();
    OrderBookService orderBookService = new OrderBookService();

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        log.info("Starting vertx server!!");
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        Route orderBook = router.route("/:currPair/orderbook");
        orderBook.handler(ctx -> {
            String currPair = ctx.pathParam("currPair");
            HttpServerResponse response = ctx.response();
            response.setChunked(true);
            response.write(limitOrderService.createLimitOrder(currPair));
            ctx.response().end();
        });

        Route limitOrder = router.route("/orders/limit");
        limitOrder.handler(ctx -> {

            HttpServerResponse response = ctx.response();
            response.setChunked(true);
            response.write(orderBookService.getOrderBook());
            ctx.response().end();
        });
        server.requestHandler(router).listen(8080);
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        log.info("Shutting down vertx!!");
    }
}
