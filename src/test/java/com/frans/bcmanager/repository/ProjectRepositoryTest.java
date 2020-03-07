package com.frans.bcmanager.repository;

import com.frans.bcmanager.model.Client;
import com.frans.bcmanager.model.Project;
import com.frans.bcmanager.testbuilder.ClientTestBuilder;
import com.frans.bcmanager.testbuilder.ProjectTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProjectRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProjectRepository repository;

    private Project project;

    @BeforeEach
    public void setUp() {
        project = ProjectTestBuilder.project().build();
        entityManager.persist(project);
    }
    @Test
    public void findOne() {
        assertThat(repository.findById(project.getId()).get()).isEqualTo(project);
    }
}
