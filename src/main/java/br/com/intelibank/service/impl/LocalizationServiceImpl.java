package br.com.intelibank.service.impl;

import br.com.intelibank.Repository.CountryRepository;
import br.com.intelibank.Repository.StateRepository;
import br.com.intelibank.domain.localization.City;
import br.com.intelibank.domain.localization.Country;
import br.com.intelibank.domain.localization.State;
import br.com.intelibank.service.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalizationServiceImpl implements LocalizationService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private StateRepository stateRepository;

    @Override
    public List<Country> getCountry() {
        return countryRepository.findAll();
    }

    @Override
    public Country getCountryById(Long id) {
        return countryRepository.findById(id).orElseThrow();
    }

    @Override
    public List<State> getState(Long countryId) {
        Country country = this.getCountryById(countryId);
        return country.getStates();

    }

    @Override
    public State getStateById(Long id) {
        return stateRepository.findById(id).orElseThrow();
    }

    @Override
    public List<City> getCities(Long stateId) {
        State state = this.getStateById(stateId);
        return state.getCities();
    }

}
