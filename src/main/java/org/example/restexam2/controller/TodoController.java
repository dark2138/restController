package org.example.restexam2.controller;

import lombok.RequiredArgsConstructor;
import org.example.restexam2.domain.Todo;
import org.example.restexam2.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = todoService.getTodos();
        return ResponseEntity.ok(todos);
    }

    @PostMapping
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
        Todo createdTodo = todoService.createTodo(todo);
        return ResponseEntity.status(201).body(createdTodo);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Todo> updateTodd(@PathVariable("id")Long id){

        Todo updateTodo = todoService.updateTodo(id);
        return ResponseEntity.ok(updateTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable(name = "id") Long id) {
        System.out.println("삭제 1");

        boolean deleted = todoService.deleteTodo(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping // 일로 들어옴
    public ResponseEntity<Void> deleteTodo2(@RequestBody Todo todo){
        System.out.println("삭제 2");

        todoService.deleteTodo(todo.getId());
        return ResponseEntity.noContent().build();
    }
}






