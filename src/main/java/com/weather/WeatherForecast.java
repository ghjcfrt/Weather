package com.weather;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a weather forecast entry for a specific time
 */
public class WeatherForecast {
    private LocalDateTime dateTime;
    private double temperature;
    private double tempMin;
    private double tempMax;
    private String description;
    private String main;
    private int humidity;
    private double windSpeed;
    private int pressure;

    public WeatherForecast(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
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

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("%s - %s: %.1f°C (%.1f°C - %.1f°C), %s, Humidity: %d%%, Wind: %.1f m/s",
                dateTime.format(formatter), main, temperature, tempMin, tempMax, 
                description, humidity, windSpeed);
    }
}
