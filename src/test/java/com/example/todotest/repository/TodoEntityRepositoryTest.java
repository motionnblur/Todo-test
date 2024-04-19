package com.example.todotest.repository;

import com.example.todotest.entity.TodoEntity;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.MountableFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class TodoEntityRepositoryTest {
    static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:15")
            .withDatabaseName("test")
            .withUsername("root")
            .withPassword("root");
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }
    @BeforeAll
    static void beforeAll() {
        postgresqlContainer.start();
    }
    @AfterAll
    static void afterAll() {
        postgresqlContainer.stop();
    }

    @Autowired
    TodoEntityRepository todoEntityRepository;
    @Test
    void shouldSaveTodoToDatabase() {
        List<String> todoItems = new ArrayList<>();
        todoItems.add("item 1");
        todoItems.add("item 2");

        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTodoName("Todo");
        todoEntity.setTodoStrings(todoItems);

        TodoEntity savedTodoEntity = todoEntityRepository.save(todoEntity);
        assertNotNull(savedTodoEntity);
    }
}