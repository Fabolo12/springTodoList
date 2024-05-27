package org.example.springprojecttodo.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.repository.mapper.ClientRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Profile("jdbc")
@Repository
@Slf4j
public class ClientRepositoryJdbc implements ClientRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    ClientRepositoryJdbc(
            final JdbcTemplate jdbcTemplate,
            final NamedParameterJdbcTemplate namedTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedTemplate = namedTemplate;
        log.info("Use Jdbc repository implementation");
    }

    @Override
    public Client getClientById(final UUID id) {
        final Client client = jdbcTemplate.queryForObject(
                "SELECT * FROM client WHERE id = ?",
                new ClientRowMapper(),
                id
        );
        return Objects.requireNonNull(client, "Client not found with id: " + id);
    }

    @Override
    public Optional<Client> findByEmail(final String email) {
        final Client client = namedTemplate.queryForObject(
                """
                        SELECT id, name, email, password, status
                        FROM client
                        WHERE email = :email
                        """,
                new MapSqlParameterSource("email", email),
                new ClientRowMapper()
        );
        return Optional.ofNullable(client);
    }

    @Override
    public Stream<Client> getAll() {
        return jdbcTemplate.queryForStream("SELECT * FROM client", new ClientRowMapper());
    }

    @Override
    public void save(final Client client) {
        final MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("id", client.getId())
                .addValue("name", client.getName())
                .addValue("email", client.getEmail())
                .addValue("password", client.getPassword())
                .addValue("status", client.getStatus().name());
        namedTemplate.update(
                """
                        INSERT INTO client (id, name, email, password, status)
                        VALUES (:id, :name, :email, :password, :status)
                        """, source
        );
    }

    @Override
    public void update(final Client client) {
        final BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(client) {
            @Override
            public Object getValue(String paramName) throws IllegalArgumentException {
                Object value = super.getValue(paramName);
                if (value instanceof Enum) {
                    return value.toString();
                }

                return value;
            }
        };
        namedTemplate.update(
                """
                        UPDATE client
                        SET name = :name, email = :email, password = :password, status = :status
                        WHERE id = :id
                        """, source
        );
    }

    @Override
    public void delete(final UUID id) {
        jdbcTemplate.update("UPDATE client SET status = 'INACTIVE' WHERE id = ?", id);
    }

    @Override
    public int countClients() {
        return Objects.requireNonNullElse(
                jdbcTemplate.queryForObject("SELECT COUNT(*) FROM client", Integer.class),
                0
        );
    }
}
