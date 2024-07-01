package org.example.springprojecttodo.repository;

import org.example.springprojecttodo.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    Page<Task> findAllByClientIdAndCompleted(final UUID clientId, final boolean completed, final Pageable pageable);

    Optional<Task> findByIdAndClientId(final UUID id, final UUID clientId);

    @Query("UPDATE Task t SET t.completed = true WHERE t.id = :id AND t.client.id = :clientId")
    @Modifying
    int deleteTask(final UUID id, final UUID clientId);
}
