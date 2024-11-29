package br.com.intelibank.facade.dto;


import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientDTO {

    private String email;
    private String phone;
    private String birthDate;
    private String cpf;
    private FinancingDTO financing;
    private AddressDTO address;
    private UserDTO user;

}
