package org.example.controller;

import org.example.DTO.request.LocationRequestDto;
import org.example.DTO.response.LocationResponseDto;
import org.example.annotation.AuthRequired;
import org.example.annotation.CurrentUser;
import org.example.model.User;
import org.example.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/weather")
public class LocationController {

    private final LocationService locationService;

    @AuthRequired
    @PostMapping("/search")
    public String searchLocation(@RequestParam String location, Model model) {
        List<LocationResponseDto> foundLocations = locationService.findLocation(location);
        model.addAttribute("foundLocations", foundLocations);
        return "search";
    }

    @AuthRequired
    @PostMapping("/add")
    public String createLocation(@Valid @ModelAttribute LocationRequestDto dto, @CurrentUser User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }

        locationService.addLocation(user, dto);
        return "redirect:/";
    }

    @AuthRequired
    @PostMapping("/delete")
    public String deleteLocation(@RequestParam("id") Long id, @CurrentUser User user) {
        locationService.deleteLocation(id, user.getId());
        return "redirect:/";
    }
}
