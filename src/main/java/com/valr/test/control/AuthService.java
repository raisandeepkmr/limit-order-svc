package com.valr.test.control;

import com.valr.test.control.common.LimitOrderServiceException;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;

/**
 * User: raisandeepkmr
 * Date: 2021/08/24
 */
public class AuthService extends AbstractVerticle {
    private static AuthService instance;
    private static JWTAuth provider;

    private AuthService() {
        provider = JWTAuth.create(vertx, new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                    .setAlgorithm("HS256")
                        .setBuffer("limit test")));
    }

    public static synchronized AuthService getInstance() {
        if(instance == null) instance = new AuthService();
        return instance;
    }

    public static synchronized JWTAuth getProvider() {
        if(instance == null) instance = new AuthService();
        return provider;
    }

    public synchronized String getToken(String userName) {
        return provider.generateToken(new JsonObject().put("user", userName));
    }
}
