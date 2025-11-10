package com.weather;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * City 类的单元测试
 */
public class CityTest {

    @Test
    public void testCityConstructor() {
        City city = new City("London", "GB");
        assertEquals("London", city.getName());
        assertEquals("GB", city.getCountry());
    }

    @Test
    public void testCityConstructorWithCoordinates() {
        City city = new City("London", "GB", 51.5074, -0.1278);
        assertEquals("London", city.getName());
        assertEquals("GB", city.getCountry());
        assertEquals(51.5074, city.getLatitude(), 0.0001);
        assertEquals(-0.1278, city.getLongitude(), 0.0001);
    }

    @Test
    public void testCitySetters() {
        City city = new City("London", "GB");
        city.setName("Paris");
        city.setCountry("FR");
        city.setLatitude(48.8566);
        city.setLongitude(2.3522);
        
        assertEquals("Paris", city.getName());
        assertEquals("FR", city.getCountry());
        assertEquals(48.8566, city.getLatitude(), 0.0001);
        assertEquals(2.3522, city.getLongitude(), 0.0001);
    }

    @Test
    public void testCityToString() {
        City city = new City("London", "GB");
        assertEquals("London, GB", city.toString());
    }
}
