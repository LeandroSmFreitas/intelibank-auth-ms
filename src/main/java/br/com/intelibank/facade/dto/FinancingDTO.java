package br.com.intelibank.facade.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FinancingDTO {

    private Long salary;
    private String occupation;
    private String companyName;

}
