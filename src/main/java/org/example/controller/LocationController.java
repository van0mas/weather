package org.example.controller;

import org.example.DTO.request.LocationRequestDto;
import org.example.DTO.response.LocationResponseDto;
import org.example.annotation.AuthRequired;
import org.example.config.props.AppConstants;
import org.example.model.User;
import org.example.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping(AppConstants.Paths.WEATHER)
public class LocationController {

    private final LocationService locationService;

    @AuthRequired
    @PostMapping(AppConstants.Paths.SEARCH)
    public String searchLocation(@RequestParam String location, HttpServletRequest request, Model model) {
        if (isInvalid(location)) {
            return AppConstants.Redirects.HOME;
        }
        List<LocationResponseDto> foundLocations = locationService.findLocation(location);
        User user = (User) request.getAttribute("user");

        model.addAttribute("foundLocations", foundLocations);
        model.addAttribute("username", user.getUsername());
        return AppConstants.Templates.SEARCH;
    }

    @AuthRequired
    @PostMapping(AppConstants.Paths.ADD)
    public String createLocation(@Valid @ModelAttribute LocationRequestDto dto,
                                 BindingResult bindingResult,
                                 HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return AppConstants.Redirects.HOME;
        }
        User user = (User) request.getAttribute("user");
        locationService.addLocation(user, dto);
        return AppConstants.Redirects.HOME;
    }

    @AuthRequired
    @PostMapping(AppConstants.Paths.DELETE)
    public String deleteLocation(@RequestParam String id, HttpServletRequest request) {
        if (isInvalid(id)) {
            return AppConstants.Redirects.HOME;
        }
        try {
            Long parsedId = Long.parseLong(id);
            User user = (User) request.getAttribute("user");
            locationService.deleteLocation(parsedId, user.getId());
        } catch (NumberFormatException e) {
            // игнорируем некорректный айди
        }
        return AppConstants.Redirects.HOME;
    }

    private boolean isInvalid(String param) {
        return param == null || param.isBlank();
    }
}
