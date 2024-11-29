package br.com.intelibank.facade.mapper.decorator;

import br.com.intelibank.domain.client.Address;
import br.com.intelibank.domain.client.Client;
import br.com.intelibank.domain.client.Financing;
import br.com.intelibank.domain.client.User;
import br.com.intelibank.domain.localization.City;
import br.com.intelibank.domain.localization.Country;
import br.com.intelibank.domain.localization.State;
import br.com.intelibank.facade.dto.ClientDTO;
import br.com.intelibank.facade.mapper.ClientMapper;
import org.springframework.stereotype.Component;

@Component
public abstract class ClientMapperDecorator implements ClientMapper {
    @Override
    public Client toEntity(ClientDTO dto) {
        return Client.builder()
                .name(dto.getUser().getFirstName() + " " + dto.getUser().getLastName())
                .cpf(dto.getCpf())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .financing(Financing.builder()
                        .companyName(dto.getFinancing().getCompanyName())
                        .salary(dto.getFinancing().getSalary())
                        .occupation(dto.getFinancing().getOccupation())
                        .build())
                .user(User.builder()
                        .email(dto.getUser().getEmail())
                        .name(dto.getUser().getFirstName() + " " + dto.getUser().getLastName())
                        .imageUrl(dto.getUser().getImageUrl())
                        .password(dto.getUser().getPassword())
                        .build())
                .address(Address.builder()
                        .number(dto.getAddress().getNumber())
                        .complement(dto.getAddress().getComplement())
                        .district(dto.getAddress().getDistrict())
                        .street(dto.getAddress().getStreet())
                        .zipcode(dto.getAddress().getZipcode())
                        .city(City.builder()
                                .id(dto.getAddress().getCity().getId())
                                .state(State.builder()
                                        .id(dto.getAddress().getState().getId())
                                        .country(Country.builder()
                                                .id(dto.getAddress().getCountry().getId())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();
    }
}
