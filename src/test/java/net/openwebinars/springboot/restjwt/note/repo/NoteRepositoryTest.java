package net.openwebinars.springboot.restjwt.note.repo;

import net.openwebinars.springboot.restjwt.note.model.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import java.util.List;



@DataJpaTest
@ActiveProfiles({"postgres"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Sql(value = "classpath:import.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:import.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class NoteRepositoryTest {

    @Autowired
    NoteRepository repository;
    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
            .withUsername("postgres")
            .withPassword("12345678")
            .withDatabaseName("test");

    @Test
    void findByAuthor() {

        List<Note> result = repository.findByAuthor("12345678-1234-5678-1234-567812345678");

        Assertions.assertEquals(1,result.size());
    }
}