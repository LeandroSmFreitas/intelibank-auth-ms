package br.com.intelibank.facade.mapper;

import br.com.intelibank.domain.localization.State;
import br.com.intelibank.facade.dto.StateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StateMapper extends EntityMapper<StateDTO, State>{
    Set<StateDTO> toDto(Set<State> state);
}
