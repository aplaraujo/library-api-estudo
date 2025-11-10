package io.github.aplaraujo.library_api_estudo.controllers;

import io.github.aplaraujo.library_api_estudo.model.Client;
import io.github.aplaraujo.library_api_estudo.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public void salvar(@RequestBody Client client) {
        log.info("Registrando novo cliente: {} com escopo: {} ", client.getClientId(), client.getScope());
        clientService.salvar(client);
    }
}
