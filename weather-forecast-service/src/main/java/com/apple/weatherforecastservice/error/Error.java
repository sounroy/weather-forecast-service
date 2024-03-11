package com.apple.weatherforecastservice.error;


import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Enum class for error codes pertaining to error
 *
 * @author sroy
 */

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Error {

    GENERIC_ERROR("ERR_01", "Some issue happpened");

    private final String code;
    private final String description;

    private Error(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}
