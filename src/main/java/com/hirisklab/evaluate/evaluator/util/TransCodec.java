package com.hirisklab.evaluate.evaluator.util;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

/**
 * Transfrome something ...
 * @author immusen
 */
public class TransCodec {
    
    public static String encode(String str) {
        return str.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&apos;");
    }

    public static String decode(String str) {
        return str.replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", "\"").replace("&apos;", "'");
    }

    public static String encode(String str, String charset) {
        try {
            return new String(str.getBytes(charset), "UTF-8");
        } catch (Exception e) {
            return str;
        }
    }

    public static String decode(String str, String charset) {
        try {
            return new String(str.getBytes("UTF-8"), charset);
        } catch (Exception e) {
            return str;
        }
    }

    //Mutilmap to Jsonobject
    public static JsonObject toJsonObject(MultiMap map) {
        JsonObject json = new JsonObject();
        map.forEach(entry -> {
            json.put(entry.getKey(), entry.getValue());
        });
        return json;
    }
}
