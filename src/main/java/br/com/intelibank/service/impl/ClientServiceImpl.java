package br.com.intelibank.service.impl;

import br.com.intelibank.Repository.AddressRepository;
import br.com.intelibank.Repository.ClientRepository;
import br.com.intelibank.Repository.FinancingRepository;
import br.com.intelibank.Repository.UserRepository;
import br.com.intelibank.domain.client.Client;
import br.com.intelibank.domain.client.User;
import br.com.intelibank.exceptions.*;
import br.com.intelibank.facade.dto.ActivateClient;
import br.com.intelibank.facade.dto.LoginDTO;
import br.com.intelibank.service.ClientService;
import br.com.intelibank.web.rest.VM.ActivateClientVM;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final FinancingRepository financingRepository;
    private final AddressRepository addressRepository;
    private final KeycloakServiceImpl keycloakService;
    private final KafkaTemplate kafkaTemplate;

    @Value("${kafka.topic.messageHub}")
    private String kafkaTopicMessageHub;

    public ClientServiceImpl(ClientRepository clientRepository, UserRepository userRepository,
                             FinancingRepository financingRepository, AddressRepository addressRepository, KeycloakServiceImpl keycloakService, KafkaTemplate kafkaTemplate) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.financingRepository = financingRepository;
        this.addressRepository = addressRepository;
        this.keycloakService = keycloakService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Client create(Client client) throws JsonProcessingException {
        verifyClientAndUserExistence(client);

        try{
            client.setAddress(addressRepository.save(client.getAddress()));

        }catch (Exception e){
            throw new RestNotFound(e.getMessage());
        }
        client.setFinancing(financingRepository.save(client.getFinancing()));

        User user = client.getUser();

        user.setEmailVerificationKey(generateVerificationKey());

        String keycloakUserId = keycloakService.createUser(user.getEmail(), user.getPassword(), user.getEmail());

        user.setKeycloakId(keycloakUserId);

        client.setUser(userRepository.save(user));

        Client clientSaved = clientRepository.save(client);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> messagePayload = new HashMap<>();
        messagePayload.put("type", "verification_email");
        messagePayload.put("email", clientSaved.getEmail());
        messagePayload.put("key", clientSaved.getUser().getEmailVerificationKey());

        String message = objectMapper.writeValueAsString(messagePayload);

        kafkaTemplate.send(kafkaTopicMessageHub, message);

        return clientSaved;
    }

    private void verifyClientAndUserExistence(Client client) {
        if (clientRepository.findByEmail(client.getEmail()).isPresent() ||
                clientRepository.findByCpf(client.getCpf()).isPresent() ||
                userRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new RestAlreadyExist("Client already exist");
        }
    }

    private static String generateVerificationKey() {
        SecureRandom random = new SecureRandom();
        int number = random.nextInt(1000000);
        return String.format("%06d", number);
    }

    @Override
    public String login(LoginDTO loginDTO) {
        try{
            return keycloakService.login(loginDTO.getUsername(), loginDTO.getPassword());
        }catch (Exception e) {
            throw new RestAccessDeniedError(e.getMessage());
        }
    }

    @Override
    public ActivateClientVM activate(ActivateClient activateClient) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String clientEmail = jwt.getClaim("email");
        Client client = clientRepository.findByEmail(clientEmail).orElseThrow(() -> new RestNotFound("Client not found"));

        String userKey = client.getUser().getEmailVerificationKey();

        if(userKey.equals(activateClient.getKey())){
            client.getUser().setEmailVerificationKey(null);
            client.getUser().setEmailVerified(Boolean.TRUE);
            client.getUser().setActivated(Boolean.TRUE);
            clientRepository.save(client);
            return ActivateClientVM.builder().status("Client active").build();
        }else{
            throw new RestErrorActivate("Error activate client");
        }

    }

}
