package com.apple.weatherforecastservice.utils;

import com.apple.weatherforecastservice.model.response.WeatherForeCastResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Helper class to write response
 *
 * @author sroy
 */
public class ResponseWriterUtils {

    public static WeatherForeCastResponse parseResponse(String response) {
        ObjectMapper mapper = new ObjectMapper();
        WeatherForeCastResponse responseData = null;
        try {
            responseData = mapper.readValue(response, WeatherForeCastResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return responseData;
    }
}
