package br.com.intelibank.facade.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponse {
    private String accessToken;
}
