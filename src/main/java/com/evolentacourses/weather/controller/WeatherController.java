package com.evolentacourses.weather.controller;

import com.evolentacourses.weather.model.Main;
import com.evolentacourses.weather.model.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
@EnableCaching
public class WeatherController {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${appId}")
    private String appId;
    @Value("${url.weather}")
    private String urlWeather;


    @GetMapping("/")
    @Cacheable(cacheNames = {"weather"})
    public Main getWeather(@RequestParam String lat, @RequestParam String lon) {
        String request = String.format("%s?lat=%s&lon=%s&units=metric&appid=%s", urlWeather, lat, lon, appId);
        return Objects.requireNonNull(restTemplate.getForObject(request, Root.class)).getMain();
    }
}
