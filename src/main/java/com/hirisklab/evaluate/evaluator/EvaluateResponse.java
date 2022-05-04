package com.hirisklab.evaluate.evaluator;

import com.hirisklab.evaluate.MainException;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;


/**
 * Evaluate reponse...
 * @author immusen
 */
public class EvaluateResponse<T> {

    private T result;
    private MultiMap headers = MultiMap.caseInsensitiveMultiMap().add("content-type", "application/json");
    private JsonObject body = new JsonObject();
    private MainException exception;

    public EvaluateResponse() {
    }

    public EvaluateResponse(T result) {
        this.result = result;
    } 

    public EvaluateResponse(MainException exception) {
        this.exception = exception;
    }

    public EvaluateResponse<T> setResult(T result) {
        this.result = result;
        return this;
    } 

    public T getResult() {
        return result;
    } 

    public EvaluateResponse<T> setHeaders(MultiMap headers) {
        this.headers = headers;
        return this;
    } 

    public EvaluateResponse<T> addHeaders(String key, String value) {
        if (this.headers == null) {
            this.headers = MultiMap.caseInsensitiveMultiMap();
        }
        this.headers.add(key, value);
        return this;
    } 

    public MultiMap getHeaders() {
        return headers;
    } 

    public EvaluateResponse<T> setException(MainException exception) {
        this.exception = exception;
        return this;
    }

    public EvaluateResponse<T> buildBody(JsonObject original) {
        if (exception == null)
            this.fineBody(original);
        else
            this.failBody(original);
        return this;
    }

    private EvaluateResponse<T> fineBody(JsonObject original) {
        this.body.put("code", 0)
                .put("msg", "success")
                .put("reqSn", original.getString("reqSn"))
                .put("data", this.result instanceof JsonObject ? (JsonObject) this.result : new JsonObject().put("result", this.result));
        return this;
    }
    
    private EvaluateResponse<T> failBody(JsonObject original) {
        this.body.put("code", this.exception.getCode())
                .put("msg", this.exception.getMessage())
                .put("reqSn", original.getString("reqSn"));
        return this;
    }

    public EvaluateResponse<T> putBody(String key, Object value) {
        this.body.put(key, value);
        return this;
    }

    public JsonObject getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "EvaluateResponse [result=" + result + ", headers=" + headers + ", body=" + body + ", exception=" + exception + "]";
    }
    
}
