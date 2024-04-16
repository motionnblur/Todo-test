package com.example.todotest.controller;

import com.example.todotest.dto.TodoDto;
import com.example.todotest.entity.TodoEntity;
import com.example.todotest.entity.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.todotest.repository.TodoEntityRepository;

@RestController
public class TodoController {
    @Autowired
    TodoEntityRepository todoEntityRepository;

    @PostMapping("/api/create_todo")
    private ResponseEntity<String> createTodo(@RequestBody TodoDto todoDto){
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTodoName(todoDto.getTodoName());

        todoEntityRepository.save(todoEntity);
        return new ResponseEntity<>("Todo has been created", HttpStatus.CREATED);
    }
}
