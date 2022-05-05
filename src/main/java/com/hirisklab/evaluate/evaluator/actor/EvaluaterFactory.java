package com.hirisklab.evaluate.evaluator.actor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.hirisklab.evaluate.evaluator.util.EvaluateException;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

/**
 * Evaluator holder...
 * @author immusen
 */
public class EvaluaterFactory {

    private static Map<JsonObject, Evaluater> evaluaters = new ConcurrentHashMap<JsonObject, Evaluater>();

    public static Future<Evaluater> getEvaluater(JsonObject profile) {
        Promise<Evaluater> promise = Promise.promise();
        Evaluater evaluater = EvaluaterFactory.evaluaters.get(profile);
        if (evaluater == null) {
            synchronized (EvaluaterFactory.class) {
                evaluater = EvaluaterFactory.evaluaters.get(profile);
                if (evaluater == null) {
                    try {
                        evaluater = new Evaluater(profile);
                        EvaluaterFactory.evaluaters.put(profile, evaluater);
                    } catch (EvaluateException e) {
                        promise.fail(e);
                    }
                }
            }
        }
        promise.complete(evaluater);
        return promise.future();
    }
}
