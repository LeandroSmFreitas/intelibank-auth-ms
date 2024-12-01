package br.com.intelibank.facade;

import br.com.intelibank.domain.client.Client;
import br.com.intelibank.facade.dto.ActivateClient;
import br.com.intelibank.facade.dto.ClientDTO;
import br.com.intelibank.facade.dto.LoginDTO;
import br.com.intelibank.facade.dto.LoginResponse;
import br.com.intelibank.facade.mapper.ClientMapper;
import br.com.intelibank.service.ClientService;
import br.com.intelibank.web.rest.VM.ActivateClientVM;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientFacade {

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ClientService clientService;

    @Transactional
    public void createUser(ClientDTO clientDTO) throws JsonProcessingException {
        clientService.create(clientMapper.toEntity(clientDTO));
    }

    @Transactional
    public LoginResponse login(LoginDTO loginDTO) {
        String token = clientService.login(loginDTO);
        return LoginResponse.builder().accessToken(token).build();
    }

    @Transactional
    public ActivateClientVM activate(ActivateClient activateClient) {
        ActivateClientVM activateClientVM = clientService.activate(activateClient);
        return activateClientVM;
    }
}
