# 天气预报应用

一个使用 OpenWeatherMap API 查询当前天气和未来天气预报的 Java 应用程序。

## 功能特性

- 查询任何城市的当前天气状况
- 获取最多 5 天的天气预报
- 显示详细的天气信息，包括：
  - 温度（当前、体感、最低、最高）
  - 天气状况和描述
  - 湿度百分比
  - 风速
  - 大气压力

## 架构设计

应用程序采用面向对象设计，包含以下类：

- **City**：表示包含名称、国家和坐标的城市
- **Weather**：表示当前天气信息
- **WeatherForecast**：表示特定时间的预报数据
- **WeatherService**：处理与 OpenWeatherMap 的 API 通信
- **WeatherApp**：带有交互式命令行界面的主应用程序

## 前置要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- OpenWeatherMap API 密钥（在 https://openweathermap.org/api 免费注册）

## 安装配置

1. 克隆仓库：
```bash
git clone https://github.com/ghjcfrt/Weather.git
cd Weather
```

2. 获取 API 密钥：
   - 在 https://openweathermap.org/api 注册
   - 创建一个免费的 API 密钥
   - 注意：API 密钥可能需要几分钟才能激活

3. 将 API 密钥设置为环境变量：
```bash
export OPENWEATHER_API_KEY=your_api_key_here
```

## 构建

使用 Maven 构建项目：

```bash
mvn clean package
```

这将创建一个可执行的 JAR 文件：`target/weather-app.jar`

## 运行

运行应用程序：

```bash
java -jar target/weather-app.jar
```

或直接使用 Maven 运行：

```bash
mvn exec:java -Dexec.mainClass="com.weather.WeatherApp"
```

## 使用说明

运行应用程序后，您将看到一个交互式菜单：

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

### 获取当前天气

1. 选择选项 1
2. 输入城市名称（例如："London"、"Beijing"、"New York"）
3. 查看当前天气信息

示例输出：
```
Weather in London, GB:
  Temperature: 15.2°C (Feels like: 14.1°C)
  Condition: Clouds (overcast clouds)
  Humidity: 72%
  Wind Speed: 4.5 m/s
  Pressure: 1015 hPa
```

### 获取天气预报

1. 选择选项 2
2. 输入城市名称
3. 输入天数（1-5）
4. 查看 3 小时间隔的天气预报

示例输出：
```
Weather Forecast for London:
=========================================
2025-11-10 12:00 - Clouds: 14.8°C (13.5°C - 14.8°C), broken clouds, Humidity: 75%, Wind: 4.2 m/s
2025-11-10 15:00 - Clouds: 15.5°C (14.2°C - 15.5°C), scattered clouds, Humidity: 68%, Wind: 3.8 m/s
...
```

## API 文档

应用程序使用 OpenWeatherMap API：

- 当前天气：`https://api.openweathermap.org/data/2.5/weather`
- 5 天预报：`https://api.openweathermap.org/data/2.5/forecast`

所有温度均为摄氏度（公制单位）。

## 项目结构

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

## 依赖项

- [org.json](https://github.com/stleary/JSON-java) - JSON 解析库

## 许可证

本项目是开源的，可用于教育目的。

## 作者

创建此天气预报应用程序以演示 Java 面向对象编程原理和 API 集成。