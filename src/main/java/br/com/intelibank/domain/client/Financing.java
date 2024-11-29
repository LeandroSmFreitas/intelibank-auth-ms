package br.com.intelibank.domain.client;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "financing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor()
@Builder(toBuilder = true)
@Getter
@Setter
public class Financing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salary")
    @NotNull
    @ToString.Include
    private Long salary;

    @Column(name = "occupation")
    @NotBlank
    @ToString.Include
    private String occupation;

    @Column(name = "company")
    @NotBlank
    @ToString.Include
    private String companyName;
}
