package com.example.todotest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TodoDto {
    String todoName;
    List<String> items;
}
