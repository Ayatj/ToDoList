package com.company;

import javax.sound.sampled.Line;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        Map<String, List<Task>> project2Task = new HashMap<>();
        Scanner in = new Scanner(System.in);
        printIntro();
        getTasks(project2Task);
        int option = in.nextInt();
        while (true) {
            switch (option) {
                case 1:
                    displayTasks(project2Task);
                    break;
                case 2:
                    addTask(project2Task);
                    break;
                case 3:
                    editTask(project2Task);
                    break;
                case 4:
                    saveTasks(project2Task);
                    break;
                default:
                    System.out.println("The option chosen is not valid");
            }
            printIntro();
            option = in.nextInt();
        }
    }
    // A method to display all the task  that contents of Map sorting by project or by date.
    private static void displayTasks(Map<String, List<Task>> project2Task) {
        Scanner in = new Scanner(System.in);
        System.out.println("Would you like to sort tasks by project or date?");
        String sortOption = in.nextLine();
        switch (sortOption) {
            case "project":
                displayByProject(project2Task);
                break;
            case "date":
                displayByDate(project2Task);
                break;
            default:
                System.out.println("Not an option");
        }
    }
    // A method to display the contents of Map sorting by date.
    private static void displayByDate(Map<String, List<Task>> project2Task) {
        List<Task> tasks = project2Task.values().stream().flatMap(List::stream).collect(Collectors.toList());
        Collections.sort(tasks, (a, b) -> a.getDueDate().compareTo(b.getDueDate()));
        displayHeader();
        for (Task task : tasks) {
            String isDone = "X";
            if (task.isDone())
                isDone = "✓";
            System.out.format("%10s %30s %20s %5s",
                    task.getProject(), task.getTitle(), task.getDueDate(), isDone);
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------------------------");
    }
    // A method to display the contents of Map sorting by project.
    private static void displayByProject(Map<String, List<Task>> project2Task) {
        displayHeader();
        for (Map.Entry<String, List<Task>> entry : project2Task.entrySet()) {
            // Print the list objects in tabular format.
            for (Task task : entry.getValue()) {
                String isDone = "X";
                if (task.isDone())
                    isDone = "✓";
                System.out.format("%10s %30s %20s %5s",
                        task.getProject(), task.getTitle(), task.getDueDate(), isDone);
                System.out.println();
            }
        }
        System.out.println("-----------------------------------------------------------------------------");
    }

    private static void displayHeader() {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%10s %30s %20s %5s", "Project", "Task", "Due Date", "isDone");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
    }
    // saving the task details in data file
    private static void saveTasks(Map<String, List<Task>> project2Task) throws IOException {
        new FileWriter("./resources/data.txt", false).close();
        for (Map.Entry<String, List<Task>> entry : project2Task.entrySet()) {
            List<Task> value = entry.getValue();
            for (Task tmp : value) {
                String toWrite = tmp.getDueDate().toString() + ";" + tmp.getTitle() + ";" + tmp.isDone() + ";" + tmp.getProject() + System.lineSeparator();
                Files.write(Paths.get("./resources/data.txt"), toWrite.getBytes(), StandardOpenOption.APPEND);
            }
        }
        System.exit(0);
    }

    // A method to select a particular Task for project from Map and perform editing operations
    private static void editTask(Map<String, List<Task>> project2Task) {
        Scanner in = new Scanner(System.in);
        System.out.println("Select the project: " + project2Task.keySet());
        String tmpProject = in.nextLine();
        // edit the task details by editing  or delete.
        System.out.println("Select the task to be edited: " + project2Task.get(tmpProject));
        String tmpValue = in.nextLine();
        Task taskToBeEdited = null;
        for (Task tmpTask : project2Task.get(tmpProject)) {
            if (tmpTask.getTitle().equals(tmpValue)) {
                taskToBeEdited = tmpTask;
                break;
            }

        }
        if (taskToBeEdited == null) {
            System.out.println(" The task doesn't exist");
        } else {
            System.out.println("Do you want to edit, title, date, status or delete task?");
            String editOption = in.nextLine();
            switch (editOption) {
                // Delete task.
                case "delete":
                    project2Task.get(tmpProject).remove(taskToBeEdited);
                    break;
                    // change the date.
                case "date":
                    System.out.println("Select a new date");
                    taskToBeEdited.setDueDate(LocalDate.parse(in.nextLine()));
                    break;
                    // change the title.
                case "title":
                    System.out.println("Select a new title");
                    taskToBeEdited.setTitle(in.nextLine());

                    break;
                    // change the status.
                case "status":
                    System.out.println("Select a new status");
                    taskToBeEdited.setDone(Boolean.parseBoolean(in.nextLine()));
                    break;
                default:
                    System.out.println("Not an option");
            }
        }
    }
    // A method to add new task to the  Map.
    private static void addTask(Map<String, List<Task>> project2Task) {
        // Add the task details in data file
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Title:");
        String title = in.nextLine();
        System.out.println("Enter Date with format yyyy-mm-dd:");
        String enteredDate = in.nextLine();
        System.out.println("Enter project name:");
        String project = in.nextLine();
        addToMap(project2Task, LocalDate.parse(enteredDate), title, false, project);
    }

    private static void getTasks(Map<String, List<Task>> taskMap) throws FileNotFoundException {
        File myFile = new File("./resources/data.txt");
        Scanner readFile = new Scanner(myFile);
        while (readFile.hasNext()) {
            String line[] = readFile.nextLine().split(";");
            LocalDate localDate = LocalDate.parse(line[0]);
            String title = line[1];
            boolean isDone = Boolean.parseBoolean(line[2]);
            String project = line[3];
            addToMap(taskMap, localDate, title, isDone, project);

        }
    }

    private static void addToMap(Map<String, List<Task>> taskMap, LocalDate localDate, String title, boolean isDone, String project) {
        if (taskMap.containsKey(project)) {
            taskMap.get(project).add(new Task(title, localDate, isDone, project));
        } else {
            List<Task> tmpList = new ArrayList<>();
            tmpList.add(new Task(title, localDate, isDone, project));
            taskMap.put(project, tmpList);
        }
    }

    private static void printIntro() {
        System.out.println("Welcome to toDoList");
        System.out.println("You now have the following options:");
        System.out.println("1- Show tasks");
        System.out.println("2- Add new task");
        System.out.println("3- Edit task");
        System.out.println("4- Save and quit");
        System.out.println("Please enter your choice[1-4]:");
    }
}