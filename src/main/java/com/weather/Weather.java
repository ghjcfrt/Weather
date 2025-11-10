package com.weather;

import java.time.LocalDateTime;

/**
 * 表示某个地点的当前天气信息
 */
public class Weather {
    private City city;
    private double temperature;
    private double feelsLike;
    private int humidity;
    private double windSpeed;
    private String description;
    private String main;
    private int pressure;
    private LocalDateTime timestamp;

    public Weather(City city) {
        this.city = city;
        this.timestamp = LocalDateTime.now();
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("Weather in %s:\n" +
                        "  Temperature: %.1f°C (Feels like: %.1f°C)\n" +
                        "  Condition: %s (%s)\n" +
                        "  Humidity: %d%%\n" +
                        "  Wind Speed: %.1f m/s\n" +
                        "  Pressure: %d hPa",
                city, temperature, feelsLike, main, description, 
                humidity, windSpeed, pressure);
    }
}
