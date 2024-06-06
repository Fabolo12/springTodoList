package org.example.springprojecttodo.repository;

import org.example.springprojecttodo.model.Client;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Profile("spring-data")
@Repository
public interface ClientRepositorySpring extends JpaRepository<Client, UUID> {

    Optional<Client> findByEmail(String email);

    @Query("SELECT COUNT(c) FROM Client c")
    int countClients();

    @Modifying
    @Query("UPDATE Client c SET c.status = 'INACTIVE' WHERE c.id = :id")
    void delete(UUID id);
}
