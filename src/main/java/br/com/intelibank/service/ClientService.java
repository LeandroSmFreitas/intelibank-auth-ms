package br.com.intelibank.service;

import br.com.intelibank.domain.client.Client;
import br.com.intelibank.domain.client.User;
import br.com.intelibank.facade.dto.ActivateClient;
import br.com.intelibank.facade.dto.LoginDTO;
import br.com.intelibank.web.rest.VM.ActivateClientVM;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ClientService {

    Client create(Client client) throws JsonProcessingException;

    private void verifyClientAndUserExistence(Client client) {

    }

    String login(LoginDTO loginDTO);

    ActivateClientVM activate(ActivateClient activateClient);
}
