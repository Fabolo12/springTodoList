package org.example.springprojecttodo.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.springprojecttodo.model.ClientStatus;

@Getter
@Setter
@ToString
public class ClientsFilter {

    private String name;

    private String email;

    private String password;

    private ClientStatus status;
}
