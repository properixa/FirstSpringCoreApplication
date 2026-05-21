package com.application.repository;

import com.application.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(int id);
    List<Task> findAll();
    boolean delete(int taskId);
}
