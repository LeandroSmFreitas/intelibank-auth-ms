package br.com.intelibank.service;


public interface KeycloakService {

    String createUser(String username, String password, String email);
    String login(String username, String password);
}
