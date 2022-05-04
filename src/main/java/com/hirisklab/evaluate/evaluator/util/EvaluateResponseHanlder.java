package com.hirisklab.evaluate.evaluator.util;

import java.util.Optional;
import com.hirisklab.evaluate.MainException;
import com.hirisklab.evaluate.MainRouter;
import com.hirisklab.evaluate.evaluator.EvaluateResponse;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

/**
 * =API response async handler...
 * @author immusen
 */
public class EvaluateResponseHanlder {

    public static <T> Handler<AsyncResult<EvaluateResponse<T>>> apply(Message<JsonObject> msg) {
        return rsp -> {
            EvaluateResponse<?> resp = Optional.ofNullable(rsp.result()).orElseGet(()-> (new EvaluateResponse<>()));
            DeliveryOptions options = new DeliveryOptions();
            if (rsp.succeeded()) {
                options.setHeaders(resp.getHeaders());
                msg.reply(resp.buildBody(msg.body()).getBody(), options);
            } else {
                Throwable cause = rsp.cause();
                resp.setException((MainException) cause);
                options.setHeaders(resp.getHeaders())
                        .addHeader(MainRouter.HEADER_STATUS_CODE, String.valueOf(((MainException) cause).getStatus()))
                        .addHeader(MainRouter.HEADER_STATUS_MESSAGE, ((MainException) cause).getMessage());
                msg.reply(resp.buildBody(msg.body()).getBody(), options);
                System.out.println(resp);
            }
        };
    }
}
