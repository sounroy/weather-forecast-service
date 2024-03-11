package com.apple.weatherforecastservice.error;


/**
 * Class for input field error
 *
 * @author sroy
 */
public class InputFieldError {

    private String field;
    private String defaultMessage;


    public InputFieldError(String field, String defaultMessage) {
        this.field = field;
        this.defaultMessage = defaultMessage;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}
