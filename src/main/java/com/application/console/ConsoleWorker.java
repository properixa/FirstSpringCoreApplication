package com.application.console;

import com.application.exceptions.TaskNotFoundException;
import com.application.exceptions.UncompletedTodoLimitReachedException;
import com.application.model.Task;
import com.application.service.TaskService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleWorker implements ConsoleInterface {
    private final Scanner scanner = new Scanner(System.in);
    private final TaskService taskService;

    public ConsoleWorker(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run() {
        boolean running = true;
        while (running) {
           printMainMenu();
           String input = scanner.nextLine();
           switch (input) {
               case "1":
                   showTasks();
                   break;
               case "2":
                   addTask();
                   break;
               case "3":
                   changeTask();
                   break;
               case "4":
                   deleteTask();
                   break;
               case "exit":
                   running = false;
                   break;
               default:
                   System.out.println("\nТакой команды не существует\n");
           }
       }
    }

    private void showTasks() {
        List<Task> tasks = taskService.findAllTask();
        if (tasks.isEmpty()) {
            System.out.println("В данный момент задач нету");
            return;
        }

        tasks.forEach((task) -> {
            System.out.println(task.getId() + ". " + task);
        });
        System.out.println();
    }

    private void addTask() {
        System.out.println();
        System.out.print("Введите заметку: ");
        Task task;
        try {
            task = taskService.createTask(scanner.nextLine());
        } catch (UncompletedTodoLimitReachedException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        System.out.println("Заметка была добавлена. ID заметки: " + task.getId());
        System.out.println();
    }

    private void deleteTask() {
        System.out.print("Введите ID заметки, которую вы хотите удалить: ");
        Integer toDeleteId = scanner.nextInt();
        scanner.nextLine();
        if (taskService.deleteTask(toDeleteId)) {
            System.out.println("Заметка удалена.");
        } else {
            System.out.println("Заметка не была удалена (скорее всего такой заметки не существует)");
        }
        System.out.println();
    }

    private void changeTask() {
        System.out.print("Введите ID задачи, которую вы хотите изменить: ");
        Integer idToChange = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите новую задачу: ");
        String title = scanner.nextLine();
        try {
            taskService.update(idToChange, title);
        } catch (TaskNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void printMainMenu() {
        System.out.println("Приветствую в списочнике задач. Что мы хотим сделать?");
        System.out.println("1. Посмотреть существующие задачи");
        System.out.println("2. Добавить новую задачу");
        System.out.println("3. Изменить задачу");
        System.out.println("4. Удалить задачу");
        System.out.println("Наберите exit для выхода\n");
        System.out.print("Введите действие: ");
    }
}
