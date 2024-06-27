package org.example.springprojecttodo.context;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

@Component
@RequestScope
@Setter
@Getter
public class TaskContext {
    private UUID clientId;
    private boolean completed;
    private Pageable pageable;
}
