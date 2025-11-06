package io.github.aplaraujo.library_api_estudo.repositories;

import io.github.aplaraujo.library_api_estudo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Client findByClientId(String clientId);
}
