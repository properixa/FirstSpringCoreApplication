package com.application.repository;

import com.application.id.IdGenerator;
import com.application.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Primary
public class InFileTaskRepository implements TaskRepository {

    private final ObjectMapper objectMapper;
    private final String dataFilePath;
    private final IdGenerator idGenerator;
    private final ConcurrentHashMap<Integer, Task> tasks = new ConcurrentHashMap<>();

    public InFileTaskRepository(@Value("${repository.json.filename:temp.json}") String dataFilePath, IdGenerator idGenerator) {
        this.objectMapper = new ObjectMapper();
        this.dataFilePath = dataFilePath;
        this.idGenerator = idGenerator;
        loadData();
    }

    private void loadData() {
        File file = new File(this.dataFilePath);
        if (!file.exists()) {
            return;
        }
        Task[] loadedTasks = objectMapper.readValue(file, Task[].class);
        for (Task task : loadedTasks) {
            task.setId(idGenerator.getNewId());
            tasks.put(task.getId(), task);
        }
    }

    private void saveData() {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(dataFilePath), findAll());
        System.out.println("Данные сохранены в файл: " + dataFilePath);
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
        saveData();
        return task;
    }

    @Override
    public Optional<Task> findById(int id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public boolean delete(int taskId) {
        Task removed = tasks.remove(taskId);
        if (removed != null) {
            saveData();
        }
        return removed != null;
    }

    @Override
    public List<Task> findUncompletedTasks() {
        return tasks.values().stream().filter(task -> !task.isCompleted()).toList();
    }
}
