package com.application.repository;

import com.application.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTaskRepository implements TaskRepository{

    private final ConcurrentHashMap<Integer, Task> tasks = new ConcurrentHashMap<>();

    @Override
    public boolean save(Task task) {
        if (tasks.containsKey(task.getId())) {
            return false;
        }
        tasks.put(task.getId(), task);
        return true;
    }

    @Override
    public Optional<Task> findById(int id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public List<Task> findAll() {
        return List.copyOf(tasks.values());
    }

    @Override
    public boolean delete(int taskId) {
        return tasks.remove(taskId) != null;
    }
}
