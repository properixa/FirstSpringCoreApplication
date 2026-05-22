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
                   completeTask();
                   break;
               case "4":
                   changeTask();
                   break;
               case "5":
                   deleteTask();
                   break;
               case "6":
                   printReport();
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
        try {
            taskService.deleteTask(toDeleteId);
            System.out.println("Заметка " + toDeleteId + " была удалена.");
        } catch (TaskNotFoundException ex) {
            System.out.println(ex.getMessage());
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
        System.out.println("3. Завершить задачу");
        System.out.println("4. Изменить задачу");
        System.out.println("5. Удалить задачу");
        System.out.println("6. Получить отчет по задачам");
        System.out.println("Наберите exit для выхода\n");
        System.out.print("Введите действие: ");
    }

    private void completeTask() {
        System.out.print("Введите ID задачи, которую хотите завершить: ");
        Integer idToComplete = scanner.nextInt();
        scanner.nextLine();

        try {
            taskService.update(idToComplete, true);
        } catch (TaskNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void printReport() {
        taskService.printReport();
        System.out.println();
    }
}
