package com.hirisklab.evaluate.evaluator;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * Evaluate API, Map to router method
 */
public interface EvaluateApi {

    static final String EVALUATE_PREDICT = "evaluter.predict";
    static final String EVALUATE_STAT = "evaluater.stat";

    public void predict(JsonObject data, Handler<AsyncResult<EvaluateResponse<JsonObject>>> handler);

    public void predict(String symbol, String type, Handler<AsyncResult<EvaluateResponse<JsonObject>>> handler);

    public void stat(JsonObject json, Handler<AsyncResult<EvaluateResponse<JsonObject>>> handler);

}
