package br.com.intelibank.domain.client;

import br.com.intelibank.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "user")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Setter
public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @NotNull
    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254)
    @NotBlank
    private String email;

    @Builder.Default
    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Builder.Default
    @Column(nullable = false, name = "email_verified")
    private Boolean emailVerified = false;

    @Size(max = 20)
    @Column(name = "email_verification_key", length = 20)
    @JsonIgnore
    private String emailVerificationKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    @NotBlank
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "keycloak_id")
    @JsonIgnore
    private String keycloakId;

}
