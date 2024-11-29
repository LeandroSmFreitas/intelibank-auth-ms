package br.com.intelibank.facade.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDTO {

    private String street;
    private String number;
    private String zipcode;
    private String district;
    private String complement;
    private CityDTO city;
    private StateDTO state;
    private CountryDTO country;
}
