package com.example.todotest.controller;

import com.example.todotest.dto.TodoDto;
import com.example.todotest.entity.TodoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.todotest.repository.TodoEntityRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {
    @Autowired
    TodoEntityRepository todoEntityRepository;

    @PostMapping("/api/create_todo")
    private ResponseEntity<String> createTodo(@RequestBody TodoDto todoDto){
        try{
            Optional<TodoEntity> todoEntityOptional = Optional.ofNullable(todoEntityRepository.findByTodoName(todoDto.getTodoName()));
            if(todoEntityOptional.isPresent())
                return new ResponseEntity<>("Todo already exists", HttpStatus.NOT_ACCEPTABLE);

            TodoEntity todoEntity = new TodoEntity();
            todoEntity.setTodoName(todoDto.getTodoName());
            todoEntity.setTodoStrings(todoDto.getItems());

            todoEntityRepository.save(todoEntity);

            return new ResponseEntity<>("Todo has been created", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/api/get_todo")
    private ResponseEntity<?> getTodo(@RequestParam String todoName){
        try{
            TodoEntity todoEntity = todoEntityRepository.findByTodoName(todoName);
            return new ResponseEntity<>(todoEntity, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(todoName+ " Not found", HttpStatus.NOT_FOUND);
        }
    }
}
