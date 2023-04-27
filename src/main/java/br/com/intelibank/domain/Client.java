package br.com.intelibank.domain;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Setter
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @NotBlank
    private String name;

    @Column(name = "email")
    @NotBlank
    @EqualsAndHashCode.Include
    @ToString.Include
    private String email;

    @Column(name= "cpf")
    @NotBlank
    @EqualsAndHashCode.Include
    @ToString.Include
    private String cpf;

    @Column(name = "phone")
    @NotBlank
    private String phone;

    @Column(name = "registration_key")
    @NotBlank
    private String registrationKey;

    @Column(name = "is_active")
    @Builder.Default
    @EqualsAndHashCode.Include
    @ToString.Include
    @NotBlank
    private Boolean isActive = true;
    @Builder.Default
    @Column(name = "is_verified")
    @NotBlank
    private Boolean isVerified = true;
    @OneToOne
    private User user;
    @OneToOne
    private Account account;
    @OneToOne
    private Address address;
    @Builder.Default
    @OneToMany(mappedBy = "client")
    private Set<Attachment> attachments = new HashSet<>();


}
