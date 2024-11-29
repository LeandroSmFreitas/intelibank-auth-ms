package br.com.intelibank.facade.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CountryDTO {
    private Long id;
    private String name;
    private String acronym;
    private String nationality;
}
