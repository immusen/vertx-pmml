package com.hirisklab.evaluate;

import java.util.Optional;

import com.hirisklab.evaluate.evaluator.util.TransCodec;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Main router...
 * @author immusen
 */
public class MainRouter {

    public static String HEADER_STATUS_CODE = "x-status-code";
    public static String HEADER_STATUS_MESSAGE = "x-status-message";
    
    public static Router create(Router router, EventBus eventBus, JsonObject routeTable) {
        routeTable.forEach(rt -> {
            String path = rt.getKey();
            JsonObject rule = (JsonObject) rt.getValue();
            String access = Optional.ofNullable(rule.getString("access")).orElse("public");
            String actor = Optional.ofNullable(rule.getString("actor")).orElse("abort404");
            String method = Optional.ofNullable(rule.getString("method")).orElse("GET");
            JsonObject ext = Optional.ofNullable(rule.getJsonObject("ext")).orElse(new JsonObject());
            if (method.equals("POST"))
                router.route().handler(BodyHandler.create());
            if (!access.equals("public")) // TODO: access control
                router.route().handler(rc -> rc.response().setStatusCode(401).end());
            if (method.equals("GET")) {
                router.get(path)
                        .handler(rc -> eventBus.request(actor, TransCodec.toJsonObject(rc.queryParams()), reply(rc)));
            } else if (method.equals("POST")) {
                router.post(path)
                        .handler(rc -> eventBus.request(actor, rc.getBodyAsJson().put("_EXT", ext), reply(rc)));
            }
        });
        return router;
    }

    private final static <T> Handler<AsyncResult<Message<Object>>> reply(RoutingContext rtx) {
        return reply -> {
            System.out.println("reply result: " + reply.result());
            String statusCode = Optional.ofNullable(reply.result().headers().get(HEADER_STATUS_CODE)).orElse("200");
            String statusMsg = Optional.ofNullable(reply.result().headers().get(HEADER_STATUS_MESSAGE)).orElse("ok");
            reply.result()
                    .headers()
                    .remove(HEADER_STATUS_CODE)
                    .remove(HEADER_STATUS_MESSAGE)
                    .forEach(hdr -> rtx.response().putHeader(hdr.getKey(), hdr.getValue()));
            rtx.response()
                    .setStatusCode(Integer.parseInt(statusCode))
                    .setStatusMessage(statusMsg)
                    .end(reply.result().body().toString());
        };
    }
}
