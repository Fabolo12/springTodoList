package org.example.springprojecttodo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.springprojecttodo.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Profile("hibernate")
@Repository
public class ClientRepositoryHibernate implements ClientRepository {

    //    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public ClientRepositoryHibernate(final EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public Client getClientById(final UUID id) {
        return entityManager.find(Client.class, id);
    }

    @Override
    public Optional<Client> findByEmail(final String email) {
        return Optional.ofNullable(entityManager.createQuery(
                        "FROM Client c WHERE c.email = :email", Client.class
                )
                .setParameter("email", email)
                .getSingleResult());
    }

    @Override
    public Stream<Client> getAll() {
        return entityManager.createQuery("FROM Client", Client.class)
                .getResultStream();
    }

    @Override
    public void save(final Client client) {
        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(final Client client) {
        entityManager.getTransaction().begin();
        entityManager.merge(client);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(final UUID id) {
        entityManager.getTransaction().begin();

        entityManager.createQuery("UPDATE Client c SET c.status = 'INACTIVE' WHERE c.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.clear(); // Not USE in real code, only for demonstration
    }

    @Override
    public int countClients() {
        return Math.toIntExact(entityManager.createQuery("SELECT COUNT(*) FROM Client", Long.class)
                .getSingleResult());
    }
}
