package org.example.restexam2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.restexam2.domain.Todo;
import org.example.restexam2.domain.User;
import org.example.restexam2.repository.TodoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional(readOnly = true)
    public List<Todo> getTodos() {
        return todoRepository.findAll(Sort.by("id").descending());
    }

    @Transactional(readOnly = true)
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new RuntimeException("id에 해당하는 사용자를 찾을 수 없어요. id::" + id));
    }


    @Transactional
    public Todo createTodo(Todo todo) {

        try {
            Todo crtTodo = todoRepository.save(todo);
            return crtTodo;

        } catch (Exception e) {
            throw new RuntimeException("사용자 추가 실패 : " + e.getMessage());
        }


    }


    @Transactional
    public Todo updateTodo(Long id) {
        System.out.println("업데이트 2");

        Todo findTodo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("사용자 없다"));


        findTodo.setDone(!findTodo.isDone());
        /*
        // 현재 상태가 false(미완료)인 경우
        boolean currentState = false;
        boolean newState = !currentState; // 결과: true(완료)

        // 현재 상태가 true(완료)인 경우
        currentState = true;
        newState = !currentState; // 결과: false(미완료)

         */

        return findTodo;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Boolean deleteTodo(Long id) {
        try {
            if (todoRepository.existsById(id)) {
                todoRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(id + " 사용자 삭제 실패 : " + e.getMessage());
        }

    }


}
