package com.apple.weatherforecastservice.model;


import javax.validation.constraints.NotNull;


/**
 * Payload for request to fetch weather forecast data
 *
 * @author sroy
 */
public class WeatherForecastRequest {

    @NotNull
    private String zipCode;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
