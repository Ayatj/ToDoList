package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Map<String, Task> taskMap = new HashMap<>();
        Scanner in = new Scanner(System.in);
        printIntro();
        getTasks(taskMap);
        int option = in.nextInt();
        while (true) {
            switch (option) {
                case 1:
                    // show the tasks title
                    System.out.println(taskMap.keySet());
                    break;
                case 2:
                    // Add the task details in data file
                    System.out.println("Enter Title:");
                    in.nextLine();
                    String title = in.nextLine();
                    System.out.println("Enter Date with format yyyy-mm-dd:");
                    String enteredDate = in.nextLine();
                    taskMap.put(title, new Task(title, LocalDate.parse(enteredDate), false));
                    break;
                case 3:
                    // edit the task details by editing  or delete.

                    System.out.println("Select the task to be edited: " + taskMap.keySet());
                    in.nextLine();
                    String tmpValue = in.nextLine();
                    if (!taskMap.containsKey(tmpValue)) {
                        System.out.println(" The task doesn't exist");
                    } else {
                        Task taskToBeEdited = taskMap.get(tmpValue);
                        System.out.println("Do you want to edit, title, date, status or delete task?");
                        String editOption = in.nextLine();
                        switch (editOption) {
                            case "delete":
                                taskMap.remove(tmpValue);
                                break;
                            case "date":
                                System.out.println("Select a new date");
                                taskToBeEdited.setDueDate(LocalDate.parse(in.nextLine()));
                                break;
                            case "title":
                                System.out.println("Select a new title");
                                taskToBeEdited.setTitle(in.nextLine());
                                taskMap.remove(tmpValue);
                                taskMap.put(taskToBeEdited.getTitle(), taskToBeEdited);
                                break;
                            case "status":
                                System.out.println("Select a new status");
                                taskToBeEdited.setDone(Boolean.parseBoolean(in.nextLine()));
                                break;
                            default:
                                System.out.println("Not an option");
                        }
                    }
                    break;
                case 4:
                    // saving the task details in data file

                    new FileWriter("./resources/data.txt", false).close();
                    for (Map.Entry<String, Task> entry : taskMap.entrySet()) {
                        Task tmp = entry.getValue();
                        String toWrite = tmp.getDueDate().toString() + ";" + tmp.getTitle() + ";" + tmp.isDone() + System.lineSeparator();
                        Files.write(Paths.get("./resources/data.txt"), toWrite.getBytes(), StandardOpenOption.APPEND);
                    }
                    System.exit(0);
                    break;
                default:
                    System.out.println("The option chosen is not valid");
            }
            printIntro();
            option = in.nextInt();
        }
    }

    private static void getTasks(Map<String, Task> taskMap) throws FileNotFoundException {
        File myFile = new File("./resources/data.txt");
        Scanner readFile = new Scanner(myFile);
        while (readFile.hasNext()) {
            String line[] = readFile.nextLine().split(";");
            LocalDate localDate = LocalDate.parse(line[0]);
            String title = line[1];
            boolean isDone = Boolean.parseBoolean(line[2]);
            taskMap.put(title, new Task(title, localDate, isDone));
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
