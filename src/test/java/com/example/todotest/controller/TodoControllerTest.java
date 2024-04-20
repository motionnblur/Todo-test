package com.example.todotest.controller;

import com.example.todotest.dto.TodoDto;
import com.example.todotest.entity.TodoEntity;
import com.example.todotest.repository.TodoEntityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TodoEntityRepository todoEntityRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
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
}