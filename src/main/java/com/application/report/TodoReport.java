package com.application.report;

import com.application.annotation.LogExecution;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@LogExecution
public class TodoReport {

    private long total;
    private long uncompleted;
    private long completed;

    public void printReport() {
        System.out.printf("Всего задач: %d, Выполнено: %d, Не выполнено: %d%n", total, completed, uncompleted);
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setUncompleted(long uncompleted) {
        this.uncompleted = uncompleted;
    }

    public void setCompleted(long completed) {
        this.completed = completed;
    }
}
