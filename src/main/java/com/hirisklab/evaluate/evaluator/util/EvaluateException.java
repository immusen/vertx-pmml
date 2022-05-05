package com.hirisklab.evaluate.evaluator.util;

import com.hirisklab.evaluate.MainException;

/**
 * Evaluator exception...
 * @author immusen
 */
public final class EvaluateException extends MainException{

    public EvaluateException(int code, String message) {
        super(code, message);
    }

    public EvaluateException(int code, int status, String message) {
        super(code, status, message);
    }

    public static final EvaluateException FailedException = new EvaluateException(4000, "Evaluate Failed");
    public static final EvaluateException FailedToPredict = new EvaluateException(4001, "Evaluate Failed to predict");
    public static final EvaluateException FailedToFeaturelize = new EvaluateException(4002, "Failed to featurelize");
    public static final EvaluateException FailedToLoadEvaluator = new EvaluateException(4003, "Failed to load evaluator");
    public static final EvaluateException InvalidConfigException = new EvaluateException(4001, "Invalid Parameter");
    public static final EvaluateException ServiceUnavailableException = new EvaluateException(5000, "Evaluate Service Unavailable");
    public static final EvaluateException InvalidConfigWithProfile= new EvaluateException(5001,  "Invalid Configruation, Missing profile");

    public static EvaluateException ClientException(String message) {
        return new EvaluateException(2000, 200, "Client Exception, " + message);
    }

    public static EvaluateException FailedException(String message) {
        return new EvaluateException(4000, 400, "Evaluate Failed, " + message);
    }

    public static EvaluateException InvalidConfigException(String message) {
        return new EvaluateException(4001, 400, "Invalid Parameter, " + message);
    }

    public static final EvaluateException ServiceUnavailableException(String message) {
        return new EvaluateException(5000, 503,  "Evaluate Service Unavailable, " + message);
    }

    public static final EvaluateException NotImplemented(String message) {
        return new EvaluateException(5001, 501, "Not Implemented, " + message);
    }

}