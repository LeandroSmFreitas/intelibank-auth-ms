package br.com.intelibank.service;

import br.com.intelibank.domain.localization.City;
import br.com.intelibank.domain.localization.Country;
import br.com.intelibank.domain.localization.State;

import java.util.List;

public interface LocalizationService {

    List<Country> getCountry();

    Country getCountryById(Long id);

    List<State> getState(Long countryId);

    State getStateById(Long id);

    List<City> getCities(Long stateId);
}
