package org.example.springprojecttodo.repository.mapper;

import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.model.ClientStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ClientRowMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
        return Client.builder()
                .id(UUID.fromString(resultSet.getString("id")))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .status(ClientStatus.valueOf(resultSet.getString("status")))
                .build();
    }
}
