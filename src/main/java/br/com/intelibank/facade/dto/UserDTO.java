package br.com.intelibank.facade.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {

    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
}
