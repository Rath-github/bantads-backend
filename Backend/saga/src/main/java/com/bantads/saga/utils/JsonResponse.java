package com.bantads.saga.utils;

public class JsonResponse {
    private final Boolean success;
    private final String message;
    private final Object data;

    public JsonResponse(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }
}
