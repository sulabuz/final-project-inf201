package com.example.weather;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Weather {
    private final Label temp;
    //private final Label location;
    private final Label state;
    private final ImageView iconWeather;

    public Weather(Label temp, Label state, ImageView icon1) {
        this.temp = temp;
        //this.location = location;
        this.state = state;
        this.iconWeather = icon1;
    }

    private static final String API_KEY = "410aae707de6697b640af6b0c9a582af";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=Almaty&appid=" + API_KEY + "&units=metric";

    public void getWeather() {
        try {
            String out = getUrl(API_URL);
            if (!out.isEmpty()) {
                JSONObject object = new JSONObject(out);
                String iconNum = object.getJSONArray("weather").getJSONObject(0).getString("icon");
                iconWeather.setImage(new Image("https://openweathermap.org/img/wn/" + iconNum + "@2x.png"));

                double temperature = object.getJSONObject("main").getDouble("temp");
                temp.setText((int) temperature + "\u2103");

                String weatherState = object.getJSONArray("weather").getJSONObject(0).getString("main");
                state.setText(weatherState);

                String cityName = object.getString("name");
                String country = object.getJSONObject("sys").getString("country");
                System.out.println(cityName + ", " + country);
                //location.setText(cityName + ", " + country);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error retrieving weather data.");
        }
    }

    private static String getUrl(String add) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(add);
            URLConnection urlCon = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error fetching data from URL.");
        }
        return content.toString();
    }
}
