package com.frans.bcmanager.repository;

import com.frans.bcmanager.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.frans.bcmanager.testbuilder.ClientTestBuilder.client;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository repository;

    private Client client, otherClient;

    @BeforeEach
    public void setup() {
        client = client()
                .build();

        otherClient = client()
                .build();

        this.entityManager.persist(client);
        this.entityManager.persist(otherClient);
    }
    @Test
    public void findAll() {
        assertThat(repository.findAll()).containsExactly(client, otherClient);
    }
    @Test
    public void findOne() {
        assertThat(repository.findById(client.getId()).get()).isEqualTo(client);
    }
    @Test
    public void delete() {
        repository.deleteById(client.getId());

        assertThat(repository.findAll()).containsOnly(otherClient);
    }
}