package br.com.intelibank.web.rest;

import br.com.intelibank.facade.ClientFacade;
import br.com.intelibank.facade.dto.ClientDTO;
import br.com.intelibank.facade.dto.LoginDTO;
import br.com.intelibank.facade.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientFacade clientFacade;

    @PostMapping("/client/register")
    public ResponseEntity createUser(@Valid @RequestBody ClientDTO clientDTO) {
        clientFacade.createUser(clientDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/client/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginResponse token = clientFacade.login(loginDTO);
        return ResponseEntity.ok().body(token);
    }
}
