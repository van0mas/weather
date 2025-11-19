package org.example.controller;

import org.example.DTO.response.WeatherResponseDto;
import org.example.annotation.AuthRequired;
import org.example.config.props.AppConstants;
import org.example.model.User;
import org.example.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class MainController {

    private final LocationService locationService;

    @AuthRequired
    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        User user = (User) request.getAttribute("user");
        List<WeatherResponseDto> locationsWeather = locationService.getLocationsWeather(user);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("weatherList", locationsWeather);

        return AppConstants.Templates.INDEX;
    }
}
