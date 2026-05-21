package com.application.service;

import com.application.exceptions.TaskNotFoundException;
import com.application.exceptions.UncompletedTodoLimitReachedException;
import com.application.model.Task;
import com.application.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultTaskService implements TaskService {
    private final TaskRepository repository;
    @Value("${task.uncompleted.limit}")
    private int UNCOMPLETED_TASK_LIMIT;

    public DefaultTaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task createTask(String title) {
        if (findAllTask().size() + 1 > UNCOMPLETED_TASK_LIMIT) {
            throw new UncompletedTodoLimitReachedException("Достигнут лимит незавершенных задач");
        }
        Task task = new Task();
        task.setTitle(title);
        task.setCreatedAt(LocalDateTime.now());
        return repository.save(task);
    }

    @Override
    public Optional<Task> findTaskById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Task> findAllTask() {
        return repository.findAll();
    }

    @Override
    public Task update(Integer id, String title, boolean completed) {
        Task task = repository.findById(id).orElse(null);
        if (task == null) {
            throw new TaskNotFoundException("Задача под указанным ID не найдена");
        }

        task.setTitle(title);
        task.setCompleted(completed);
        return repository.save(task);
    }

    @Override
    public Task update(Integer id, String title) {
        Task task = repository.findById(id).orElse(null);
        if (task == null) {
            throw new TaskNotFoundException("Задача под указанным ID не найдена");
        }

        task.setTitle(title);
        return repository.save(task);
    }

    @Override
    public Task update(Integer id, boolean completed) {
        Task task = repository.findById(id).orElse(null);
        if (task == null) {
            throw new TaskNotFoundException("Задача под указанным ID не найдена");
        }

        task.setCompleted(completed);
        return repository.save(task);
    }

    @Override
    public boolean deleteTask(Integer id) {
        return repository.delete(id);
    }
}
