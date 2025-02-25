package org.example.restexam2.repository;

import org.example.restexam2.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo , Long> {
}
