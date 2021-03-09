package com.company;

import java.time.LocalDate;
import java.util.Date;

public class Task {

    private String title;
    private LocalDate dueDate;
    private boolean done;
    private String project;


    /**  Creating an object of Task class*/

    public Task (String title, LocalDate dueDate, boolean done, String project) {
        this.title = title;
        this.dueDate = dueDate;
        this.done = done;
        this.project=project;
    }

    /**
     * A method to get the task title
     * @return a String containing the title of a task
     */
    public String getTitle() {
        return title;
    }

    /**A method to set the title of a Task */
    public void setTitle(String title) {
        this.title = title;
    }

    /** A method to get the due date of a task*/

    public LocalDate getDueDate() {
        return dueDate;
    }
    /** A method to set the due date of a task*/

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    /** A method to get the status of task*/

    public boolean isDone() {
        return done;
    }
    /** A method to set the status of task*/

    public void setDone(boolean done) {
        this.done = done;
    }


    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", dueDate=" + dueDate +
                ", done=" + done +
                '}';
    }
}
