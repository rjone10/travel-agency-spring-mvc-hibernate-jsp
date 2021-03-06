package com.travel.agency.controller;

import com.travel.agency.converter.DtoConverter;
import com.travel.agency.dto.HotelDto;
import com.travel.agency.model.AccommodationType;
import com.travel.agency.model.Hotel;
import com.travel.agency.service.CountryService;
import com.travel.agency.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hotels")
public class HotelController {
    private final HotelService hotelService;
    private final CountryService countryService;
    private final DtoConverter dtoConverter;

    @Autowired
    public HotelController(HotelService hotelService, CountryService countryService, DtoConverter dtoConverter) {
        this.hotelService = hotelService;
        this.countryService = countryService;
        this.dtoConverter = dtoConverter;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add")
    public String addHotel(Model model) {
        model.addAttribute("hotel", new HotelDto());
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("types", AccommodationType.values());
        return "add-hotel";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public String addHotel(@ModelAttribute HotelDto hotelDto, @RequestParam("countryId") Long countryId) {
        hotelDto.setCountry(countryService.findById(countryId));
        Hotel hotel = dtoConverter.convertToEntity(hotelDto, new Hotel());
        hotelService.add(hotel);
        return "redirect:/hotels/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteHotel(@PathVariable Long id) {
        hotelService.delete(id);
        return "redirect:/hotels/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public String getAllHotels(Model model) {
        model.addAttribute("hotels", hotelService.getAllHotels());
        return "hotels";
    }

    @GetMapping("/allHotelsInCountry/{id}")
    public String getHotelsInCountry(@PathVariable("id") Long countryId, Model model) {
        List<Hotel> hotels = hotelService.findByCountryId(countryId);
        if (hotels.isEmpty()) {
            model.addAttribute("errorMessage", "There is no hotels in this country");
            model.addAttribute("countries", countryService.getAllCountries());
            return "hotels";
        }
        model.addAttribute("hotels", hotels);
        return "hotels-in-country";
    }
}
