package com.valr.test.control.common;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.Data;

/**
 * User: raisandeepkmr
 * Date: 2021/08/22
 */
@Data
public class LimitOrderServiceException extends Exception {
    private HttpResponseStatus statusCode;
    public LimitOrderServiceException(Throwable t, HttpResponseStatus statusCode) {
        super(t);
        this.statusCode = statusCode;
    }
    public LimitOrderServiceException(String msg, HttpResponseStatus statusCode) {
        super(msg);
        this.statusCode = statusCode;
    }
}
