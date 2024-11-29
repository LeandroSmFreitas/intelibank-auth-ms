package br.com.intelibank.Repository;

import br.com.intelibank.domain.client.Address;
import br.com.intelibank.domain.client.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
