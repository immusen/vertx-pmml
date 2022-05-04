package com.hirisklab.evaluate;


/**
 * Evaluate exception
 * @author immusen
 */
public class MainException extends RuntimeException {

    private int code = 5000;
    private int status = -1;
    private String message = "Internal Server Error";

    public MainException() {
    }

    public MainException(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public MainException(int code, int status, String message) {
        super();
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        if (this.status == -1) {
            if (this.code >= 2000)
                this.status = 200;
            if (this.code >= 4000)
                this.status = 400;
            if (this.code >= 5000)
                this.status = 500;
        }
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

}