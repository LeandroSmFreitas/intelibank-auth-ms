package br.com.intelibank.facade.mapper;

import br.com.intelibank.domain.client.Client;
import br.com.intelibank.facade.dto.ClientDTO;
import br.com.intelibank.facade.mapper.decorator.ClientMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(ClientMapperDecorator.class)
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
}
