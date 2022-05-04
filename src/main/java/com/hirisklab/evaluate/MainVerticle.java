package com.hirisklab.evaluate;

import com.hirisklab.evaluate.evaluator.EvaluateVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;


/**
 * MainVerticle
 * @author immusen
 */
public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println(config().getJsonObject("route"));
    Router router = MainRouter.create(
        Router.router(vertx),
        vertx.eventBus(),
        config().getJsonObject("route"));
    vertx.deployVerticle(EvaluateVerticle.class.getName(), res -> {
      System.out.println(res.result());
      if (res.failed())
        startPromise.fail(res.cause());
    });
    vertx
        .createHttpServer()
        .requestHandler(router)
        .listen(
            config().getInteger("http.port", 8080),
            http -> {
              if (http.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port: " + http.result().actualPort());
              } else {
                startPromise.fail(http.cause());
                System.out.println("HTTP server start failed " + http.cause().getMessage());
              }
            });
  }
}
