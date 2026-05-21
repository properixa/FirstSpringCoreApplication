package com.application.repository;

import com.application.id.IdGenerator;
import com.application.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTaskRepository implements TaskRepository{

    private final IdGenerator idGenerator;
    private final ConcurrentHashMap<Integer, Task> tasks = new ConcurrentHashMap<>();

    public InMemoryTaskRepository(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            int newId = idGenerator.getNewId();
            task.setId(newId);
            tasks.put(newId, task);
        } else {
            tasks.put(task.getId(), task);
        }
        return task;
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

    @Override
    public List<Task> findUncompletedTasks() {
        return tasks.values().stream().filter(Task::isCompleted).toList();
    }
}
