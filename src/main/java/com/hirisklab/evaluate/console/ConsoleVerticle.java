package com.hirisklab.evaluate.console;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

/**
 * Console event handle with Eventbus
 * @author immusen
 */
public class ConsoleVerticle extends AbstractVerticle {

    final static String WEB_LOGIN = "web.login";

    final static Handler<Message<JsonObject>> login = msg -> 
        msg.reply("<h1>login</h1><input name='who'></input><button>login</button>", new DeliveryOptions().addHeader("content-type", "text/html"));

    @Override
    public void start() throws Exception {
        System.out.println("ConsoleVerticle: start");
        vertx.eventBus().<JsonObject>consumer(WEB_LOGIN, login);
    }
}
