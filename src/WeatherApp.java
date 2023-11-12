import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class WeatherApp {
    private static final String API_KEY = "f735ad8cc57317e6c9aec8e514ed3a58";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=city_name&appid=" + API_KEY;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String city = sc.nextLine();

        // Получение данных о текущей погоде
        String currentWeatherData = getWeatherData(city);
        JSONObject currentWeather = new JSONObject(currentWeatherData);

        // Получение данных о погодном прогнозе на следующие 5 дней
        String forecastWeatherData = getForecastData(city);
        JSONObject forecastWeather = new JSONObject(forecastWeatherData);

        // Вывод информации о текущей погоде
        System.out.println("Текущая погода в городе " + city);
        double d = currentWeather.getJSONObject("main").getDouble("temp");
        d = d-273.15;
        double e = currentWeather.getJSONObject("main").getDouble("temp_max");
        e = e- 273.15;
        double f= currentWeather.getJSONObject("main").getDouble("temp_min");
        f = f-273.15;
        System.out.println("Температура: " + d);
        System.out.println("Максимальная температура: " + e);
        System.out.println("Минимальная температура: " + f);
        System.out.println("Скорость ветра: " + currentWeather.getJSONObject("wind").getDouble("speed"));
        System.out.println("Направление ветра: " + currentWeather.getJSONObject("wind").getDouble("deg"));
        System.out.println("Состояние погоды: " + currentWeather.getJSONArray("weather").getJSONObject(0).getString("main"));

        // Вывод информации о погодном прогнозе на следующие 5 дней
        System.out.println();
        System.out.println("Прогноз погоды на следующие 5 дней в городе " + city);
        JSONArray forecastList = forecastWeather.getJSONArray("list");
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        for (int i = 0; i < forecastList.length(); i++) {
            long forecastDate = forecastList.getJSONObject(i).getLong("dt");
            Date date = new Date(forecastDate * 1000);
            String formattedDate = dateFormat.format(date);
            JSONObject forecastData = forecastList.getJSONObject(i).getJSONArray("weather").getJSONObject(0);

            System.out.println("Дата: " + formattedDate);
            System.out.println("Состояние погоды: " + forecastData.getString("main"));
            double a = forecastList.getJSONObject(i).getJSONObject("main").getDouble("temp");
            a=a-273.15;
            System.out.println("Температура: " + a);
            double b = forecastList.getJSONObject(i).getJSONObject("main").getDouble("temp_max");
            b = b-273.15;
            System.out.println("Максимальная температура: " + b);
            double c = forecastList.getJSONObject(i).getJSONObject("main").getDouble("temp_min");
            c = c-273.15;
            System.out.println("Минимальная температура: " + c);
            System.out.println("Скорость ветра: " + forecastList.getJSONObject(i).getJSONObject("wind").getDouble("speed"));
            System.out.println("Направление ветра: " + forecastList.getJSONObject(i).getJSONObject("wind").getDouble("deg"));
            System.out.println();
        }
    }

    private static String getWeatherData(String city) {
        String apiUrl = API_URL.replace("city_name", city);

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            return response.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getForecastData(String city) {
        String apiUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + API_KEY;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            return response.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}