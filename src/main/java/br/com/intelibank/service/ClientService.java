package br.com.intelibank.service;

import br.com.intelibank.domain.client.Client;
import br.com.intelibank.domain.client.User;
import br.com.intelibank.facade.dto.LoginDTO;

public interface ClientService {

    Client create(Client client);

    private void verifyClientAndUserExistence(Client client) {

    }

    String login(LoginDTO loginDTO);
}
