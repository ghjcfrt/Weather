package com.weather;

import org.junit.Test;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

/**
 * Unit tests for the WeatherForecast class
 */
public class WeatherForecastTest {

    @Test
    public void testWeatherForecastConstructor() {
        LocalDateTime dateTime = LocalDateTime.now();
        WeatherForecast forecast = new WeatherForecast(dateTime);
        
        assertNotNull(forecast);
        assertEquals(dateTime, forecast.getDateTime());
    }

    @Test
    public void testWeatherForecastSettersAndGetters() {
        LocalDateTime dateTime = LocalDateTime.now();
        WeatherForecast forecast = new WeatherForecast(dateTime);
        
        forecast.setTemperature(18.5);
        forecast.setTempMin(16.0);
        forecast.setTempMax(20.0);
        forecast.setHumidity(65);
        forecast.setWindSpeed(3.5);
        forecast.setDescription("partly cloudy");
        forecast.setMain("Clouds");
        forecast.setPressure(1013);
        
        assertEquals(18.5, forecast.getTemperature(), 0.01);
        assertEquals(16.0, forecast.getTempMin(), 0.01);
        assertEquals(20.0, forecast.getTempMax(), 0.01);
        assertEquals(65, forecast.getHumidity());
        assertEquals(3.5, forecast.getWindSpeed(), 0.01);
        assertEquals("partly cloudy", forecast.getDescription());
        assertEquals("Clouds", forecast.getMain());
        assertEquals(1013, forecast.getPressure());
    }

    @Test
    public void testWeatherForecastToString() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 11, 10, 12, 0);
        WeatherForecast forecast = new WeatherForecast(dateTime);
        
        forecast.setTemperature(18.5);
        forecast.setTempMin(16.0);
        forecast.setTempMax(20.0);
        forecast.setHumidity(65);
        forecast.setWindSpeed(3.5);
        forecast.setDescription("partly cloudy");
        forecast.setMain("Clouds");
        
        String result = forecast.toString();
        assertTrue(result.contains("2025-11-10"));
        assertTrue(result.contains("Clouds"));
        assertTrue(result.contains("18.5"));
    }
}
