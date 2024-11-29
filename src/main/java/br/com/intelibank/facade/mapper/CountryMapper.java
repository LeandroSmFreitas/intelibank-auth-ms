package br.com.intelibank.facade.mapper;

import br.com.intelibank.domain.localization.Country;
import br.com.intelibank.facade.dto.CountryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountryMapper extends EntityMapper<CountryDTO, Country>{
}
