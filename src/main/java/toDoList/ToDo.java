package toDoList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class ToDo {
    // A method to display all the task  that contents of Map sorting by project or by date.
    public static void displayTasks(Map<String, List<Task>> project2Task) {
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
    public static void displayByDate(Map<String, List<Task>> project2Task) {
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
    public static void displayByProject(Map<String, List<Task>> project2Task) {
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
    // print Header
    public  static void displayHeader() {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%10s %30s %20s %5s", "Project", "Task", "Due Date", "isDone");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
    }
    // saving the task details in data file
    public static void saveTasks(Map<String, List<Task>> project2Task) throws IOException {
        new FileWriter("/Users/ayatjabali/Downloads/ToDoList1/src/main/java/toDoList/data.txt", false).close();
        for (Map.Entry<String, List<Task>> entry : project2Task.entrySet()) {
            List<Task> value = entry.getValue();
            for (Task tmp : value) {
                String toWrite = tmp.getDueDate().toString() + ";" + tmp.getTitle() + ";" + tmp.isDone() + ";" + tmp.getProject() + System.lineSeparator();
                Files.write(Paths.get("/Users/ayatjabali/Downloads/ToDoList1/src/main/java/toDoList/data.txt"), toWrite.getBytes(), StandardOpenOption.APPEND);
            }
        }
        System.exit(0);
    }

    // A method to select a particular Task for project from Map and perform editing operations
    public static void editTask(Map<String, List<Task>> project2Task) {
        Scanner in = new Scanner(System.in);
        System.out.println("Select the project: " + project2Task.keySet());
        String tmpProject = in.nextLine();
        // edit the task details by editing  or delete.
        System.out.println("Select the task to be edited: " + project2Task.get(tmpProject));
        String tmpValue = in.nextLine();
        Task taskToBeEdited = null;
        taskToBeEdited = getTask(project2Task, tmpProject, tmpValue, taskToBeEdited);
        if (taskToBeEdited == null) {
            System.out.println(" The task doesn't exist");
        } else {
            System.out.println("Do you want to edit, title, date, status or delete task?");
            String editOption = in.nextLine();
            switch (editOption) {
                // Delete task.
                case "delete":
                    deleteTask(project2Task, tmpProject, taskToBeEdited);
                    break;
                // change the date.
                case "date":
                    System.out.println("Select a new date");
                    String newDate = in.nextLine();
                    EditTaskDate(newDate, taskToBeEdited);
                    break;
                // change the title.
                case "title":
                    System.out.println("Select a new title");
                    String newTitle = in.nextLine();
                    EditTaskTitle(taskToBeEdited, newTitle);

                    break;
                // change the status.
                case "status":
                    System.out.println("Select a new status");
                    boolean isDone = Boolean.parseBoolean(in.nextLine());
                    EditTaskDone(taskToBeEdited, isDone);
                    break;
                default:
                    System.out.println("Not an option");
            }
        }
    }
      // Edit the task status
    public static void EditTaskDone(Task taskToBeEdited, boolean isDone) {
        taskToBeEdited.setDone(isDone);
    }
      // Edit task title
    public static void EditTaskTitle(Task taskToBeEdited, String newTitle) {
        taskToBeEdited.setTitle(newTitle);
    }
      // Edit Task Date
    public static void EditTaskDate(String in, Task taskToBeEdited) {
        taskToBeEdited.setDueDate(LocalDate.parse(in));
    }
    // method to delete task
    public static void deleteTask(Map<String, List<Task>> project2Task, String tmpProject, Task taskToBeEdited) {
        project2Task.get(tmpProject).remove(taskToBeEdited);
    }
    // search throw the tasks of the project to find the target task
    public static Task getTask(Map<String, List<Task>> project2Task, String tmpProject, String tmpValue, Task taskToBeEdited) {
        if (!project2Task.containsKey(tmpProject))
            return  taskToBeEdited;
        for (Task tmpTask : project2Task.get(tmpProject)) {
            if (tmpTask.getTitle().equals(tmpValue)) {
                taskToBeEdited = tmpTask;
                break;
            }

        }
        return taskToBeEdited;
    }

    // A method to add new task to the  Map.
    public static void addTask(Map<String, List<Task>> project2Task) {
        // Add the task details in data file
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Title:");
        String title = in.nextLine();
        System.out.println("Enter Date with format yyyy-mm-dd:");
        String enteredDate = in.nextLine();
        System.out.println("Enter project name:");
        String project = in.nextLine();
        try {        LocalDate date = LocalDate.parse(enteredDate);
            Task result = new Task(title,date,false, project);
            addToMap(project2Task, result);
        }catch (DateTimeParseException e){
            System.out.println(e);
        }
    }
    // method to get task
    public static void getTasks(Map<String, List<Task>> taskMap) throws FileNotFoundException {
        File myFile = new File("/Users/ayatjabali/Downloads/ToDoList1/src/main/java/toDoList/data.txt");
        Scanner readFile = new Scanner(myFile);
        while (readFile.hasNext()) {
            String line[] = readFile.nextLine().split(";");
            LocalDate localDate = null;
            try {
                localDate = LocalDate.parse(line[0]);
            }catch (DateTimeParseException e){
                System.out.println(e);
            }
            String title = line[1];
            boolean isDone = Boolean.parseBoolean(line[2]);
            String project = line[3];
            Task result = new Task(title,localDate,false, project);
            addToMap(taskMap, result);

        }
    }
       // add task to the map
    public static void addToMap(Map<String, List<Task>> taskMap, Task task) {
        if (taskMap.containsKey(task.getProject())) {
            taskMap.get(task.getProject()).add(task);
        } else {
            List<Task> tmpList = new ArrayList<>();
            tmpList.add(task);
            taskMap.put(task.getProject(), tmpList);
        }
    }
    public static void printIntro() {
        System.out.println("Welcome to toDoList");
        System.out.println("You now have the following options:");
        System.out.println("1- Show tasks");
        System.out.println("2- Add new task");
        System.out.println("3- Edit task");
        System.out.println("4- Save and quit");
        System.out.println("Please enter your choice[1-4]:");
    }

}


