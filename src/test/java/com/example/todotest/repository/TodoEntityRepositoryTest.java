package com.example.todotest.repository;

import com.example.todotest.entity.TodoEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
class TodoEntityRepositoryTest {
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