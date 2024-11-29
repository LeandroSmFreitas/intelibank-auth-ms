package br.com.intelibank.facade;

import br.com.intelibank.facade.dto.CityDTO;
import br.com.intelibank.facade.dto.CountryDTO;
import br.com.intelibank.facade.dto.StateDTO;
import br.com.intelibank.facade.mapper.CityMapper;
import br.com.intelibank.facade.mapper.CountryMapper;
import br.com.intelibank.facade.mapper.StateMapper;
import br.com.intelibank.service.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class LocalizationFacade {

    @Autowired
    private LocalizationService localizationService;

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private StateMapper stateMapper;

    @Autowired
    private CityMapper cityMapper;

    @Transactional
    public List<CountryDTO> getCountry () {
        return countryMapper.toDto(localizationService.getCountry());
    }

    @Transactional
    public List<StateDTO> getStates (Long id) {
        return stateMapper.toDto(localizationService.getState(id));
    }

    @Transactional
    public List<CityDTO> getCities (Long id) {
        return cityMapper.toDto(localizationService.getCities(id));
    }
}
