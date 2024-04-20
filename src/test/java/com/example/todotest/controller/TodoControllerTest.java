package com.example.todotest.controller;

import com.example.todotest.dto.TodoDto;
import com.example.todotest.entity.TodoEntity;
import com.example.todotest.repository.TodoEntityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class TodoControllerTest {
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
    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(TodoController.class).build();
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
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void shouldReturnStatusCreated() throws Exception {
        List<String> todoItems = new ArrayList<>();
        todoItems.add("item 1");
        todoItems.add("item 2");

        TodoDto todoDto = new TodoDto();
        todoDto.setTodoName("todo");
        todoDto.setItems(todoItems);

        String requestBody = objectMapper.writeValueAsString(todoDto);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/create_todo")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    @Transactional(rollbackFor = Exception.class)
    public void shouldGetTodo() throws Exception {
        List<String> todoItems = new ArrayList<>();
        todoItems.add("item 1");
        todoItems.add("item 2");

        TodoDto todoDto = new TodoDto();
        todoDto.setTodoName("todo");
        todoDto.setItems(todoItems);

        String requestBody = objectMapper.writeValueAsString(todoDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/create_todo")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/get_todo")
                        .param("todoName", "todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());
    }
}