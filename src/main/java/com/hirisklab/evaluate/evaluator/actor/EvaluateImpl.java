package com.hirisklab.evaluate.evaluator.actor;

import java.util.Map;
import java.util.Optional;
import com.hirisklab.evaluate.evaluator.EvaluateApi;
import com.hirisklab.evaluate.evaluator.util.EvaluateException;
import com.hirisklab.evaluate.evaluator.EvaluateResponse;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

/**
 * Evaluator api implementation...
 * @author immusen
 */
public class EvaluateImpl implements EvaluateApi {

    public void predict(JsonObject data, Handler<AsyncResult<EvaluateResponse<JsonObject>>> handler) {
        Promise<EvaluateResponse<JsonObject>> promise = Promise.promise();
        try {
            JsonObject profile = Optional.ofNullable(data.getJsonObject("_EXT"))
                    .orElseThrow(() -> EvaluateException.InvalidConfigException);
            EvaluaterFactory.getEvaluater(profile).onSuccess(evaluater -> {
                Featurelize(data.getJsonObject("data")).onSuccess(feature -> {
                    evaluater.predict(feature).onSuccess(result -> {
                        promise.complete(new EvaluateResponse<JsonObject>(new JsonObject().put("raw", result)));
                    }).onFailure(f -> promise.fail(EvaluateException.FailedToPredict));
                }).onFailure(f -> promise.fail(EvaluateException.FailedToFeaturelize));
            }).onFailure(f -> promise.fail(EvaluateException.FailedToLoadEvaluator));
        } catch (Exception e) {
            e.printStackTrace();
            promise.fail(e);
        }
        handler.handle(promise.future());
    }

    public void predict(String symbol, String type, Handler<AsyncResult<EvaluateResponse<JsonObject>>> handler) {
    }

    public void stat(JsonObject data, Handler<AsyncResult<EvaluateResponse<JsonObject>>> handler) {
    }

    private Future<Map<String, Object>> Featurelize(JsonObject data) {
        Promise<Map<String, Object>> promise = Promise.promise();
        try {
            Map<String, Object> feature = data.getMap();
            // TODO: featurelize with pmml input fields...
            promise.complete(feature);
        } catch (Exception e) {
            promise.fail(e);
        }
        return promise.future();
    }

}
