package br.com.intelibank.facade.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CityDTO {
    private Long id;
    private String name;
}
