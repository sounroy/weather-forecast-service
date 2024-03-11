package com.apple.weatherforecastservice.service;

import com.apple.weatherforecastservice.constants.CONSTANTS;
import com.apple.weatherforecastservice.model.response.WeatherForeCastResponse;
import com.apple.weatherforecastservice.service.cache.CacheService;
import com.apple.weatherforecastservice.utils.GeoCoderUtils;
import com.apple.weatherforecastservice.utils.ResponseWriterUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;


/**
 * Service layer for weather forecast business logic
 *
 * @author sroy
 */
@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    CacheService cacheService;

    /**
     * This method is to communicate  get weather forecast data from external API or cache, and do cache aside strategy based on cache hit miss.
     *
     * @param zipCode
     * @return WeatherForeCastResponse
     */
    public WeatherForeCastResponse getWeatherForecast(final String zipCode)  {
        GeoCoderUtils geoCoderUtils = new GeoCoderUtils();
        WeatherForeCastResponse response = null;
        ObjectMapper mapper = new ObjectMapper();
        logger.debug(" Fetching weather forecast data ");
        try {
            double result[] = geoCoderUtils.convertZipCodeToLatLong(zipCode);
            String dataFromCache = cacheService.getForecastDataByZipCode(zipCode);

            if (StringUtils.isBlank(dataFromCache)) {
                logger.debug(" Fetching weather forecast data. Data not in cache, hence cache aside ");
                response = fetchWeatherData(result[0], result[1]);
                cacheService.setForecastDataByZipCode(zipCode,
                        mapper.writeValueAsString(response), Duration.ofMinutes(30));
            } else {
                logger.debug(" Cache hit ");
                response = mapper.readValue(dataFromCache, WeatherForeCastResponse.class);
                response.isResponseCached = true;
            }
        } catch (Exception e) {
            logger.error("Error Fetching weather forecast data ", e);
        }
        return response;
    }

    /**
     * This method is to communicate  with external API to fetch the weather forecast data based on lat,long.
     * Since the OpenWeatherAPI was demanding a subscription with payment card, using dummy data.
     *
     * @param lat
     * @param lon
     * @return WeatherForeCastResponse
     */
    private WeatherForeCastResponse fetchWeatherData(final double lat, final double lon) {
        WeatherForeCastResponse response = ResponseWriterUtils.parseResponse(CONSTANTS.SAMPLE_RESPONSE);
        return response;
    }
}
