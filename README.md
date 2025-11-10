# Weather Forecast Application

A Java application for querying current weather and future weather forecasts using the OpenWeatherMap API.

## Features

- Query current weather conditions for any city
- Get weather forecasts for up to 5 days
- Display detailed weather information including:
  - Temperature (current, feels like, min, max)
  - Weather conditions and descriptions
  - Humidity percentage
  - Wind speed
  - Atmospheric pressure

## Architecture

The application uses object-oriented design with the following classes:

- **City**: Represents a city with name, country, and coordinates
- **Weather**: Represents current weather information
- **WeatherForecast**: Represents forecast data for a specific time
- **WeatherService**: Handles API communication with OpenWeatherMap
- **WeatherApp**: Main application with interactive CLI

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- OpenWeatherMap API key (free registration at https://openweathermap.org/api)

## Setup

1. Clone the repository:
```bash
git clone https://github.com/ghjcfrt/Weather.git
cd Weather
```

2. Get an API key:
   - Register at https://openweathermap.org/api
   - Create a free API key
   - Note: It may take a few minutes for the API key to activate

3. Set the API key as an environment variable:
```bash
export OPENWEATHER_API_KEY=your_api_key_here
```

## Building

Build the project using Maven:

```bash
mvn clean package
```

This will create an executable JAR file: `target/weather-app.jar`

## Running

Run the application:

```bash
java -jar target/weather-app.jar
```

Or run directly with Maven:

```bash
mvn exec:java -Dexec.mainClass="com.weather.WeatherApp"
```

## Usage

When you run the application, you'll see an interactive menu:

```
=================================
  Weather Forecast Application  
=================================

Options:
1. Get current weather
2. Get weather forecast
3. Exit

Select an option:
```

### Get Current Weather

1. Select option 1
2. Enter a city name (e.g., "London", "Beijing", "New York")
3. View the current weather information

Example output:
```
Weather in London, GB:
  Temperature: 15.2°C (Feels like: 14.1°C)
  Condition: Clouds (overcast clouds)
  Humidity: 72%
  Wind Speed: 4.5 m/s
  Pressure: 1015 hPa
```

### Get Weather Forecast

1. Select option 2
2. Enter a city name
3. Enter the number of days (1-5)
4. View the weather forecast with 3-hour intervals

Example output:
```
Weather Forecast for London:
=========================================
2025-11-10 12:00 - Clouds: 14.8°C (13.5°C - 14.8°C), broken clouds, Humidity: 75%, Wind: 4.2 m/s
2025-11-10 15:00 - Clouds: 15.5°C (14.2°C - 15.5°C), scattered clouds, Humidity: 68%, Wind: 3.8 m/s
...
```

## API Documentation

The application uses the OpenWeatherMap API:

- Current Weather: `https://api.openweathermap.org/data/2.5/weather`
- 5 Day Forecast: `https://api.openweathermap.org/data/2.5/forecast`

All temperatures are in Celsius (metric units).

## Project Structure

```
Weather/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── weather/
│   │               ├── City.java
│   │               ├── Weather.java
│   │               ├── WeatherForecast.java
│   │               ├── WeatherService.java
│   │               └── WeatherApp.java
│   └── test/
│       └── java/
│           └── com/
│               └── weather/
├── pom.xml
├── .gitignore
└── README.md
```

## Dependencies

- [org.json](https://github.com/stleary/JSON-java) - JSON parsing library

## License

This project is open source and available for educational purposes.

## Author

Created as a weather forecast application demonstrating Java OOP principles and API integration.