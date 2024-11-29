package br.com.intelibank.facade.mapper;

import br.com.intelibank.domain.localization.City;
import br.com.intelibank.facade.dto.CityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CityMapper extends EntityMapper<CityDTO, City>{
}
