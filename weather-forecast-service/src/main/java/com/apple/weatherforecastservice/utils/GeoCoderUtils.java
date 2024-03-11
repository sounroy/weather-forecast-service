package com.apple.weatherforecastservice.utils;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import org.apache.tomcat.util.json.JSONParser;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;


/**
 * Utility class pertaining to zipcode to lat long conversions
 *
 * @author sroy
 */
public class GeoCoderUtils {

    // Assumption that we are making external call to Gmap API to get lat, long from zipcode.
    // Needs subscription, APIkey so removing.
    public static double[] convertZipCodeToLatLong(String zipCode) throws IOException, ParseException {
        String apiKey = "YOUR_GOOGLE_MAPS_API_KEY"; // Replace with your API key
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="
                + URLEncoder.encode(zipCode, "UTF-8") + "&key=" + apiKey;

        Scanner scanner = new Scanner(new URL(url).openStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNextLine()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        // Parse JSON response
       /* JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.toString());
        JSONArray results = (JSONArray) jsonObject.get("results");
        JSONObject location = (JSONObject) ((JSONObject) results.get(0)).get("geometry");
        JSONObject latLngObj = (JSONObject) location.get("location");
        double latitude = (double) latLngObj.get("lat");
        double longitude = (double) latLngObj.get("lng"); */

        double latituide = 33.44;
        double longitude = -94.04;

        return new double[] { latituide, longitude };
    }
}
