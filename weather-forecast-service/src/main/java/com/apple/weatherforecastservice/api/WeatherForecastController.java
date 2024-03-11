package com.apple.weatherforecastservice.api;

import com.apple.weatherforecastservice.error.InputFieldError;
import com.apple.weatherforecastservice.model.WeatherForecastRequest;
import com.apple.weatherforecastservice.model.response.ResponseStatus;
import com.apple.weatherforecastservice.model.response.RestResponseContainer;
import com.apple.weatherforecastservice.model.response.WeatherForeCastResponse;
import com.apple.weatherforecastservice.service.WeatherService;
import com.apple.weatherforecastservice.utils.metrics.MetricsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WeatherForecastController {
    private static final Logger logger = LoggerFactory.getLogger(WeatherForecastController.class);
    private static final String SERVICE_NAME = "weather-forecast-service";
    @Autowired
    private WeatherService weatherService;

   // @Autowired
   // private MeterRegistry metrics;

    /**
     * This API is used to forward user sub to Game server to validate social login
     * @param weatherForecastRequest
     * @return RestResponseContainer
     */
    @PostMapping("/v1/weather/")
    public @ResponseBody RestResponseContainer getWeatherForecast(@RequestBody
                                                                      @Valid WeatherForecastRequest weatherForecastRequest,
                                                                  BindingResult bindingResult) {
        RestResponseContainer response = new RestResponseContainer();
        ResponseStatus status = new ResponseStatus() ;
        final long timeStart = System.currentTimeMillis();
        try {

            if (bindingResult.hasFieldErrors()) {
                List<InputFieldError> fieldErrorList = bindingResult.getFieldErrors().stream()
                        .map(error -> new InputFieldError(error.getField(), error.getDefaultMessage()))
                        .collect(Collectors.toList());
                response.setData(fieldErrorList);
                status = new ResponseStatus(400,"Bad request - ");
                response.setStatus(status);
                return response;
            }
            WeatherForeCastResponse forecastResponse = null;
            forecastResponse = weatherService.getWeatherForecast(weatherForecastRequest.getZipCode());
            response.setData( forecastResponse);
        }catch(Exception e) {
            logger.error(String.format("Exception while getting weather forecast data for input : %s ", weatherForecastRequest.getZipCode()),e);
            status = new ResponseStatus(4000,"Generic exception - "+e.getMessage());
            //metrics.counter(MetricsUtils.getMetricsExpectedErrorKey(env, SERVICE_NAME, REQ_TYPE_TOKEN)).increment();
        }finally {
           /* metrics.timer(MetricsUtils.getMetricsKey(env, SERVICE_NAME))
                    .record(System.currentTimeMillis() - timeStart, TimeUnit.MILLISECONDS);
            metrics.counter(MetricsUtils.getMetricsExecutionCountKey(env, SERVICE_NAME)).increment();*/
        }
        response.setStatus(status);
        return response;
    }
}