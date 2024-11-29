package br.com.intelibank.web.rest;

import br.com.intelibank.facade.LocalizationFacade;
import br.com.intelibank.facade.dto.CityDTO;
import br.com.intelibank.facade.dto.CountryDTO;
import br.com.intelibank.facade.dto.StateDTO;
import br.com.intelibank.service.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class LocalizationController {

    @Autowired
    private LocalizationFacade localizationFacade;

    @GetMapping("/country")
    public ResponseEntity<List<CountryDTO>> getCountry() {
        return ResponseEntity.ok(localizationFacade.getCountry());
    }

    @GetMapping("/country/{id}/states")
    public ResponseEntity<List<StateDTO>> getCountryStates(@PathVariable("id") Long id) {
        return ResponseEntity.ok(localizationFacade.getStates(id));
    }

    @GetMapping("/state/{id}/cities")
    public ResponseEntity<List<CityDTO>> getStatesCities(@PathVariable("id") Long id) {
        return ResponseEntity.ok(localizationFacade.getCities(id));
    }


}
