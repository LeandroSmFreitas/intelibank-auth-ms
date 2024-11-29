package br.com.intelibank.Repository;

import br.com.intelibank.domain.client.Financing;
import br.com.intelibank.domain.client.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FinancingRepository extends JpaRepository<Financing, Long> {

}
