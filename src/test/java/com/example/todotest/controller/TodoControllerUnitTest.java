package com.example.todotest.controller;

import com.example.todotest.dto.TodoDto;
import com.example.todotest.entity.TodoEntity;
import com.example.todotest.repository.TodoEntityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TodoControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TodoEntityRepository todoEntityRepository;
    @Test
    public void shouldPostMappingReturn_BadRequest() throws Exception {
        List<String> todoItems = new ArrayList<>();
        todoItems.add("item 1");
        todoItems.add("item 2");

        TodoDto todoDto = new TodoDto();
        todoDto.setTodoName("todo");
        todoDto.setItems(todoItems);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(todoDto);

        doThrow(new RuntimeException("Simulated data access exception")).when(todoEntityRepository).save(any(TodoEntity.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/create_todo")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void shouldGetMappingReturn_BadRequest() throws Exception {
        doThrow(new RuntimeException("Simulated data access exception")).when(todoEntityRepository).findByTodoName(any(String.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/get_todo")
                        .param("todoName", "todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}