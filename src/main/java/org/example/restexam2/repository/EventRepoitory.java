package org.example.restexam2.repository;

import org.example.restexam2.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepoitory extends JpaRepository<Event, Long> {
}
