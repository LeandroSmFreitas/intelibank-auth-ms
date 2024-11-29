package br.com.intelibank.domain.client;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "account_bank")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Setter
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(name = "account_number")
    @EqualsAndHashCode.Include
    @ToString.Include
    @NotBlank
    private String accountNumber;
    @Column(name = "card_number")
    @EqualsAndHashCode.Include
    @ToString.Include
    @NotBlank
    private String cardNumber;
    @Column(name = "validation_date")
    @ToString.Include
    @NotBlank
    private String validationDate;
    @Column(name = "cvv")
    @ToString.Include
    @NotBlank
    private String cvv;
    @Column(name = "account_value")
    @ToString.Include
    @NotBlank
    private Integer accountValue;
    @Column(name = "credit_value")
    @ToString.Include
    @NotBlank
    private Integer creditValue;
}
