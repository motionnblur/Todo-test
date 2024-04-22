package com.example.todotest.entity;

import com.example.todotest.dto.TodoDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
class TodoEntityUnitTest {
    @Test
    public void shouldTodoDtoMappedToTodoEntity(){
        List<String> todoItems = new ArrayList<>();
        todoItems.add("item 1");
        todoItems.add("item 2");

        TodoDto todoDto = new TodoDto();
        todoDto.setTodoName("todo");
        todoDto.setItems(todoItems);

        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTodoName(todoDto.getTodoName());
        todoEntity.setTodoStrings(todoDto.getItems());

        assertEquals(todoEntity.getTodoName(), todoDto.getTodoName());
        assertArrayEquals(todoEntity.getTodoStrings().toArray(new String[0]), todoDto.getItems().toArray(new String[0]));
    }
}