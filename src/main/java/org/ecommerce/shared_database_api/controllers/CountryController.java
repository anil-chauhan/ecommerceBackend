package org.ecommerce.shared_database_api.controllers;

import lombok.Data;
import org.ecommerce.shared_database_api.dto.CategoryDto;
import org.ecommerce.shared_database_api.dto.CategoryTreeDto;
import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.models.Country;
import org.ecommerce.shared_database_api.repo.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CountryController {


    private final CountryRepository countryRepository;

    @Autowired
    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    @PostMapping("/countries")
    //@PostAuthorize("hasRole('ADMIN')")
    public List<Country> getCountries() {


        List<Country> all = countryRepository.findAll();

        return all;
    }


}
