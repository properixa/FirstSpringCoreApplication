package com.application.service;

import com.application.exceptions.UncompletedTodoLimitReachedException;
import com.application.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(String title) throws UncompletedTodoLimitReachedException;
    Optional<Task> findTaskById(Integer id);
    List<Task> findAllTask();
    Task update(Integer id, String title, boolean completed);
    Task update(Integer id, String title);
    Task update(Integer id, boolean completed);
    boolean deleteTask(Integer id);
    void printReport();
    void internalCallDemo();
}
