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
 * 与 OpenWeatherMap API 交互的服务类
 */
public class WeatherService {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5";
    private static final int FORECASTS_PER_DAY = 8; // 3小时间隔：24小时 / 3小时 = 每天8次预报
    private static final int CONNECTION_TIMEOUT_MS = 5000;
    private static final int READ_TIMEOUT_MS = 5000;
    private String apiKey;

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * 获取城市的当前天气
     */
    public Weather getCurrentWeather(String cityName) throws Exception {
        String encodedCity = URLEncoder.encode(cityName, StandardCharsets.UTF_8.toString());
        String urlString = String.format("%s/weather?q=%s&appid=%s&units=metric", 
                BASE_URL, encodedCity, apiKey);
        
        String response = makeApiRequest(urlString);
        return parseCurrentWeather(response);
    }

    /**
     * 通过坐标获取当前天气
     */
    public Weather getCurrentWeather(double lat, double lon) throws Exception {
        String urlString = String.format("%s/weather?lat=%f&lon=%f&appid=%s&units=metric", 
                BASE_URL, lat, lon, apiKey);
        
        String response = makeApiRequest(urlString);
        return parseCurrentWeather(response);
    }

    /**
     * 获取城市的天气预报（5天预报，3小时间隔）
     */
    public List<WeatherForecast> getForecast(String cityName, int days) throws Exception {
        String encodedCity = URLEncoder.encode(cityName, StandardCharsets.UTF_8.toString());
        String urlString = String.format("%s/forecast?q=%s&appid=%s&units=metric&cnt=%d", 
                BASE_URL, encodedCity, apiKey, days * FORECASTS_PER_DAY);
        
        String response = makeApiRequest(urlString);
        return parseForecast(response);
    }

    /**
     * 通过坐标获取天气预报
     */
    public List<WeatherForecast> getForecast(double lat, double lon, int days) throws Exception {
        String urlString = String.format("%s/forecast?lat=%f&lon=%f&appid=%s&units=metric&cnt=%d", 
                BASE_URL, lat, lon, apiKey, days * FORECASTS_PER_DAY);
        
        String response = makeApiRequest(urlString);
        return parseForecast(response);
    }

    private String makeApiRequest(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(CONNECTION_TIMEOUT_MS);
        conn.setReadTimeout(READ_TIMEOUT_MS);

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
        
        // 解析城市信息
        String cityName = json.getString("name");
        JSONObject sys = json.getJSONObject("sys");
        String country = sys.optString("country", "");
        JSONObject coord = json.getJSONObject("coord");
        double lat = coord.getDouble("lat");
        double lon = coord.getDouble("lon");
        
        City city = new City(cityName, country, lat, lon);
        Weather weather = new Weather(city);

        // 解析天气数据
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
