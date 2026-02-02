package org.example.repository;

import org.example.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    java.util.List<Todo> findByTitleContainingIgnoreCase(String keyword);
    java.util.List<Todo> findByCompleted(boolean completed);
    java.util.List<Todo> findByTitleContainingIgnoreCaseAndCompleted(String keyword, boolean completed);
}
