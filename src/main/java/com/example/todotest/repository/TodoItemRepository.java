package com.example.todotest.repository;

import com.example.todotest.entity.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    TodoItem findByTodoString(String todoString);
}
