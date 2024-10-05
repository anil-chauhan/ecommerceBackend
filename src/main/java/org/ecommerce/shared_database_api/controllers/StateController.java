package org.ecommerce.shared_database_api.controllers;

import lombok.Data;
import org.ecommerce.shared_database_api.dto.CategoryDto;
import org.ecommerce.shared_database_api.models.Country;
import org.ecommerce.shared_database_api.models.State;
import org.ecommerce.shared_database_api.repo.CountryRepository;
import org.ecommerce.shared_database_api.repo.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

@RestController
@CrossOrigin
public class StateController {


    private final StateRepository stateRepository;

    @Autowired
    public StateController(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }


    @Data
    public static class StateDto implements Serializable {
        Integer countryCode;
    }


    @PostMapping("/findByCountryCode")
    //@PostAuthorize("hasRole('ADMIN')")
    public List<State> getStateByCountryCode(@RequestBody StateDto stateDto) {
        Integer countryCode = stateDto.getCountryCode();
        List<State> byCountryCode = stateRepository.findByCountryCode1(countryCode);

        return byCountryCode;
    }


}
