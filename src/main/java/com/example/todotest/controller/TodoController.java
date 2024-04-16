package com.example.todotest.controller;

import com.example.todotest.dto.TodoDto;
import com.example.todotest.entity.TodoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.todotest.repository.TodoEntityRepository;

import java.util.List;

@RestController
public class TodoController {
    @Autowired
    TodoEntityRepository todoEntityRepository;

    @PostMapping("/api/create_todo")
    private ResponseEntity<String> createTodo(@RequestBody TodoDto todoDto){
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTodoName(todoDto.getTodoName());
        todoEntity.setTodoStrings(todoDto.getItems());

        todoEntityRepository.save(todoEntity);
        return new ResponseEntity<>("Todo has been created", HttpStatus.CREATED);
    }
    @GetMapping("/api/get_todo")
    private ResponseEntity<List<String>> getTodo(@RequestParam String todoName){
        TodoEntity todoEntity = todoEntityRepository.findByTodoName(todoName);

        return new ResponseEntity<>(todoEntity.getTodoStrings(), HttpStatus.CREATED);
    }
}
