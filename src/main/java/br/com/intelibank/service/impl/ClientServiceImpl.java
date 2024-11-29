package br.com.intelibank.service.impl;

import br.com.intelibank.Repository.AddressRepository;
import br.com.intelibank.Repository.ClientRepository;
import br.com.intelibank.Repository.FinancingRepository;
import br.com.intelibank.Repository.UserRepository;
import br.com.intelibank.domain.client.Client;
import br.com.intelibank.domain.client.User;
import br.com.intelibank.facade.dto.LoginDTO;
import br.com.intelibank.service.ClientService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final FinancingRepository financingRepository;
    private final AddressRepository addressRepository;
    private final KeycloakServiceImpl keycloakService;

    public ClientServiceImpl(ClientRepository clientRepository, UserRepository userRepository,
                             FinancingRepository financingRepository, AddressRepository addressRepository, KeycloakServiceImpl keycloakService) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.financingRepository = financingRepository;
        this.addressRepository = addressRepository;
        this.keycloakService = keycloakService;
    }

    @Override
    public Client create(Client client) {
        verifyClientAndUserExistence(client);

        client.setAddress(addressRepository.save(client.getAddress()));
        client.setFinancing(financingRepository.save(client.getFinancing()));

        User user = client.getUser();

        String keycloakUserId = keycloakService.createUser(user.getEmail(), user.getPassword(), user.getEmail());

        user.setKeycloakId(keycloakUserId);

        client.setUser(userRepository.save(user));

        Client clientSaved = clientRepository.save(client);


        return clientSaved;
    }

    private void verifyClientAndUserExistence(Client client) {
        if (clientRepository.findByEmail(client.getEmail()).isPresent() ||
                clientRepository.findByCpf(client.getCpf()).isPresent() ||
                userRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new RuntimeException("Client or User already exists");
        }
    }

    @Override
    public String login(LoginDTO loginDTO) {
        return keycloakService.login(loginDTO.getUsername(), loginDTO.getPassword());
    }

}
