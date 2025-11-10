package com.weather;

import java.util.List;
import java.util.Scanner;

/**
 * 天气预报应用程序的主应用程序类
 */
public class WeatherApp {
    private WeatherService weatherService;
    private Scanner scanner;

    public WeatherApp(String apiKey) {
        this.weatherService = new WeatherService(apiKey);
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("=================================");
        System.out.println("  Weather Forecast Application  ");
        System.out.println("=================================\n");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Get current weather");
            System.out.println("2. Get weather forecast");
            System.out.println("3. Exit");
            System.out.print("\nSelect an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    getCurrentWeather();
                    break;
                case "2":
                    getForecast();
                    break;
                case "3":
                    System.out.println("\nThank you for using Weather Forecast Application!");
                    return;
                default:
                    System.out.println("\nInvalid option. Please try again.");
            }
        }
    }

    private void getCurrentWeather() {
        System.out.print("\nEnter city name (e.g., London, Beijing, New York): ");
        String cityName = scanner.nextLine().trim();

        if (cityName.isEmpty()) {
            System.out.println("City name cannot be empty.");
            return;
        }

        try {
            System.out.println("\nFetching current weather...");
            Weather weather = weatherService.getCurrentWeather(cityName);
            System.out.println("\n" + weather);
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
            System.out.println("Please check the city name and try again.");
        }
    }

    private void getForecast() {
        System.out.print("\nEnter city name (e.g., London, Beijing, New York): ");
        String cityName = scanner.nextLine().trim();

        if (cityName.isEmpty()) {
            System.out.println("City name cannot be empty.");
            return;
        }

        System.out.print("Enter number of days (1-5): ");
        String daysStr = scanner.nextLine().trim();
        int days;
        try {
            days = Integer.parseInt(daysStr);
            if (days < 1 || days > 5) {
                System.out.println("Number of days must be between 1 and 5.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
            return;
        }

        try {
            System.out.println("\nFetching weather forecast...");
            List<WeatherForecast> forecasts = weatherService.getForecast(cityName, days);
            
            System.out.println("\nWeather Forecast for " + cityName + ":");
            System.out.println("=========================================");
            for (WeatherForecast forecast : forecasts) {
                System.out.println(forecast);
            }
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
            System.out.println("Please check the city name and try again.");
        }
    }

    public static void main(String[] args) {
        // 检查是否提供了 API 密钥
        String apiKey = System.getenv("OPENWEATHER_API_KEY");
        
        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("Error: OPENWEATHER_API_KEY environment variable is not set.");
            System.out.println("\nTo use this application:");
            System.out.println("1. Get an API key from https://openweathermap.org/api");
            System.out.println("2. Set the environment variable: export OPENWEATHER_API_KEY=your_api_key");
            System.out.println("3. Run the application again");
            System.exit(1);
        }

        WeatherApp app = new WeatherApp(apiKey);
        app.run();
    }
}
