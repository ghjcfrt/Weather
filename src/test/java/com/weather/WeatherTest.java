package com.weather;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Weather 类的单元测试
 */
public class WeatherTest {

    @Test
    public void testWeatherConstructor() {
        City city = new City("London", "GB");
        Weather weather = new Weather(city);
        
        assertNotNull(weather);
        assertEquals(city, weather.getCity());
        assertNotNull(weather.getTimestamp());
    }

    @Test
    public void testWeatherSettersAndGetters() {
        City city = new City("London", "GB");
        Weather weather = new Weather(city);
        
        weather.setTemperature(15.5);
        weather.setFeelsLike(14.2);
        weather.setHumidity(72);
        weather.setWindSpeed(4.5);
        weather.setDescription("clear sky");
        weather.setMain("Clear");
        weather.setPressure(1015);
        
        assertEquals(15.5, weather.getTemperature(), 0.01);
        assertEquals(14.2, weather.getFeelsLike(), 0.01);
        assertEquals(72, weather.getHumidity());
        assertEquals(4.5, weather.getWindSpeed(), 0.01);
        assertEquals("clear sky", weather.getDescription());
        assertEquals("Clear", weather.getMain());
        assertEquals(1015, weather.getPressure());
    }

    @Test
    public void testWeatherToString() {
        City city = new City("London", "GB");
        Weather weather = new Weather(city);
        
        weather.setTemperature(15.5);
        weather.setFeelsLike(14.2);
        weather.setHumidity(72);
        weather.setWindSpeed(4.5);
        weather.setDescription("clear sky");
        weather.setMain("Clear");
        weather.setPressure(1015);
        
        String result = weather.toString();
        assertTrue(result.contains("London"));
        assertTrue(result.contains("15.5"));
        assertTrue(result.contains("Clear"));
    }
}
