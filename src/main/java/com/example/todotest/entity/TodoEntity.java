package com.example.todotest.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String todoName;

    @JsonManagedReference
    @OneToMany(mappedBy = "todoEntity", cascade = CascadeType.ALL)
    private List<TodoItem> videosEntity;
}
