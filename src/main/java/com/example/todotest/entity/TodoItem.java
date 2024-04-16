package com.example.todotest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String todoString;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="todoEntity_id", nullable=false)
    TodoEntity todoEntity;
}