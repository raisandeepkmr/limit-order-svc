package com.valr.test.boundry;

import com.valr.test.control.LimitOrderService;
import com.valr.test.control.common.LimitOrderServiceException;
import com.valr.test.model.orderlimit.LimitOrder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.healthchecks.HealthCheckHandler;
import io.vertx.ext.healthchecks.Status;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * User: raisandeepkmr
 * Date: 2021/08/22
 */
@Slf4j
public class LimitOrderResource extends AbstractVerticle {
    LimitOrderService limitOrderService = new LimitOrderService();

    /**
     *
     * @param startPromise
     * @throws Exception
     */
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        log.info("Starting vertx server!!");
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        //***************************************HEALTH CHECK
        HealthCheckHandler healthCheckHandler = HealthCheckHandler.create(vertx);
        healthCheckHandler.register("health-check-handler", future -> future.complete(Status.OK()));
        router.get("/health").handler(healthCheckHandler);
        //***************************************ORDER BOOK ROUTE
        router.get("/:currPair/orderbook")
        .respond(ctx -> {
            String currPair = ctx.pathParam("currPair");
            try {
                return Future.succeededFuture(limitOrderService.getOrderBook(currPair));
            } catch (LimitOrderServiceException e) {
                return Future.failedFuture(e);
            }
        });
        //***************************************LIMIT ORDER ROUTE
        router.post("/orders/limit")
        .handler(BodyHandler.create())
        .respond(ctx -> {
            String userId = ctx.request().getHeader("x-user-id");
            LimitOrder limitOrder = ctx.getBodyAsJson().mapTo(LimitOrder.class);
            try {
                ctx.response().setStatusCode(HttpResponseStatus.CREATED.code());
                return Future.succeededFuture(limitOrderService.createLimitOrder(userId, limitOrder));
            } catch (LimitOrderServiceException e) {
                return Future.failedFuture(e);
            }
        });
        //***************************************TRADE HISTORY ROUTE
        router.post("/:currPair/tradehistory")
        .handler(BodyHandler.create())
        .respond(ctx -> {
            String userId = ctx.request().getHeader("x-user-id");
            String currPair = ctx.pathParam("currPair");
            try {
                return Future.succeededFuture(limitOrderService.getTradeHistory(userId, currPair));
            } catch (LimitOrderServiceException e) {
                ctx.response().setStatusCode(e.getStatusCode().code());
                return Future.succeededFuture(e.getMessage());
            }
        });
        server.requestHandler(router).listen(8080);
    }

    /**
     *
     * @param stopPromise
     * @throws Exception
     */
    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        log.info("Shutting down vertx!!");
    }
}
