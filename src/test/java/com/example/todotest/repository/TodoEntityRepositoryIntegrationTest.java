package com.example.todotest.repository;

import com.example.todotest.entity.TodoEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ActiveProfiles("integration")
class TodoEntityRepositoryIntegrationTest {
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
    @Transactional(rollbackFor = Exception.class)
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
    @Test
    @Transactional(rollbackFor = Exception.class)
    void shouldGetTodoFromDatabase() {
        List<String> todoItems = new ArrayList<>();
        todoItems.add("item 1");
        todoItems.add("item 2");

        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTodoName("Todo");
        todoEntity.setTodoStrings(todoItems);

        todoEntityRepository.save(todoEntity);

        String todoNameToFind = "Todo";
        TodoEntity todoShouldBeFound = todoEntityRepository.findByTodoName(todoNameToFind);

        assertNotNull(todoShouldBeFound);
    }
    @Test
    @Transactional(rollbackFor = Exception.class)
    void shouldDeleteTodoFromDatabase() {
        List<String> todoItems = new ArrayList<>();
        todoItems.add("item 1");
        todoItems.add("item 2");

        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTodoName("Todo");
        todoEntity.setTodoStrings(todoItems);

        todoEntityRepository.save(todoEntity);

        // Retrieve the actual TodoEntity object
        TodoEntity entityToDelete = todoEntityRepository.findByTodoName(todoEntity.getTodoName());
        assertNotNull(entityToDelete);

        String todoNameToDelete = entityToDelete.getTodoName(); // Get the name from the entity

        todoEntityRepository.delete(entityToDelete);

        TodoEntity entityThatShouldBeNull = todoEntityRepository.findByTodoName(todoNameToDelete);

        assertNull(entityThatShouldBeNull);
    }
}