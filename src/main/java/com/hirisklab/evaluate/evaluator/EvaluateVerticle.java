package com.hirisklab.evaluate.evaluator;

import com.hirisklab.evaluate.evaluator.actor.EvaluateImpl;
import com.hirisklab.evaluate.evaluator.util.EvaluateException;
import com.hirisklab.evaluate.evaluator.util.EvaluateResponseHanlder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

/**
 * Evaluate event handle with Eventbus
 * @author immusen
 */
public class EvaluateVerticle extends AbstractVerticle {

    private EvaluateApi evaluate = new EvaluateImpl();

    final Handler<Message<JsonObject>> predict = msg -> evaluate.predict(msg.body(), EvaluateResponseHanlder.apply(msg));
    final Handler<Message<JsonObject>> stat = msg -> msg.reply("stat: not implemented. #" + msg.body().getJsonObject("_PATH"), new DeliveryOptions().addHeader("content-type", "text/plain"));

    @Override
    public void start() throws EvaluateException {
        System.out.println("EvaluateVerticle: start");
        vertx.eventBus().<JsonObject>consumer(EvaluateApi.EVALUATE_PREDICT, predict);
        vertx.eventBus().<JsonObject>consumer(EvaluateApi.EVALUATE_STAT, stat);
    }
}
