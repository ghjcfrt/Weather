package com.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Service class to interact with OpenWeatherMap API
 */
public class WeatherService {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5";
    private String apiKey;

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Get current weather for a city
     */
    public Weather getCurrentWeather(String cityName) throws Exception {
        String encodedCity = URLEncoder.encode(cityName, StandardCharsets.UTF_8.toString());
        String urlString = String.format("%s/weather?q=%s&appid=%s&units=metric", 
                BASE_URL, encodedCity, apiKey);
        
        String response = makeApiRequest(urlString);
        return parseCurrentWeather(response);
    }

    /**
     * Get current weather for a city by coordinates
     */
    public Weather getCurrentWeather(double lat, double lon) throws Exception {
        String urlString = String.format("%s/weather?lat=%f&lon=%f&appid=%s&units=metric", 
                BASE_URL, lat, lon, apiKey);
        
        String response = makeApiRequest(urlString);
        return parseCurrentWeather(response);
    }

    /**
     * Get weather forecast for a city (5 day forecast with 3-hour intervals)
     */
    public List<WeatherForecast> getForecast(String cityName, int days) throws Exception {
        String encodedCity = URLEncoder.encode(cityName, StandardCharsets.UTF_8.toString());
        String urlString = String.format("%s/forecast?q=%s&appid=%s&units=metric&cnt=%d", 
                BASE_URL, encodedCity, apiKey, days * 8); // 8 forecasts per day (3-hour intervals)
        
        String response = makeApiRequest(urlString);
        return parseForecast(response);
    }

    /**
     * Get weather forecast by coordinates
     */
    public List<WeatherForecast> getForecast(double lat, double lon, int days) throws Exception {
        String urlString = String.format("%s/forecast?lat=%f&lon=%f&appid=%s&units=metric&cnt=%d", 
                BASE_URL, lat, lon, apiKey, days * 8);
        
        String response = makeApiRequest(urlString);
        return parseForecast(response);
    }

    private String makeApiRequest(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder();
            String line;
            while ((line = errorReader.readLine()) != null) {
                errorResponse.append(line);
            }
            errorReader.close();
            throw new Exception("API request failed with code " + responseCode + ": " + errorResponse.toString());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        conn.disconnect();

        return response.toString();
    }

    private Weather parseCurrentWeather(String jsonResponse) {
        JSONObject json = new JSONObject(jsonResponse);
        
        // Parse city information
        String cityName = json.getString("name");
        JSONObject sys = json.getJSONObject("sys");
        String country = sys.optString("country", "");
        JSONObject coord = json.getJSONObject("coord");
        double lat = coord.getDouble("lat");
        double lon = coord.getDouble("lon");
        
        City city = new City(cityName, country, lat, lon);
        Weather weather = new Weather(city);

        // Parse weather data
        JSONObject main = json.getJSONObject("main");
        weather.setTemperature(main.getDouble("temp"));
        weather.setFeelsLike(main.getDouble("feels_like"));
        weather.setHumidity(main.getInt("humidity"));
        weather.setPressure(main.getInt("pressure"));

        JSONObject wind = json.getJSONObject("wind");
        weather.setWindSpeed(wind.getDouble("speed"));

        JSONArray weatherArray = json.getJSONArray("weather");
        if (weatherArray.length() > 0) {
            JSONObject weatherObj = weatherArray.getJSONObject(0);
            weather.setMain(weatherObj.getString("main"));
            weather.setDescription(weatherObj.getString("description"));
        }

        long timestamp = json.getLong("dt");
        weather.setTimestamp(LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()));

        return weather;
    }

    private List<WeatherForecast> parseForecast(String jsonResponse) {
        List<WeatherForecast> forecasts = new ArrayList<>();
        JSONObject json = new JSONObject(jsonResponse);
        JSONArray list = json.getJSONArray("list");

        for (int i = 0; i < list.length(); i++) {
            JSONObject item = list.getJSONObject(i);
            
            long timestamp = item.getLong("dt");
            LocalDateTime dateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
            
            WeatherForecast forecast = new WeatherForecast(dateTime);

            JSONObject main = item.getJSONObject("main");
            forecast.setTemperature(main.getDouble("temp"));
            forecast.setTempMin(main.getDouble("temp_min"));
            forecast.setTempMax(main.getDouble("temp_max"));
            forecast.setHumidity(main.getInt("humidity"));
            forecast.setPressure(main.getInt("pressure"));

            JSONObject wind = item.getJSONObject("wind");
            forecast.setWindSpeed(wind.getDouble("speed"));

            JSONArray weatherArray = item.getJSONArray("weather");
            if (weatherArray.length() > 0) {
                JSONObject weatherObj = weatherArray.getJSONObject(0);
                forecast.setMain(weatherObj.getString("main"));
                forecast.setDescription(weatherObj.getString("description"));
            }

            forecasts.add(forecast);
        }

        return forecasts;
    }
}
