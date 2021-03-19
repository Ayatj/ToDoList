package toDoList;

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
        ToDo.printIntro();
        ToDo.getTasks(project2Task);
        int option = in.nextInt();
        while (true) {
            switch (option) {
                case 1:
                    ToDo.displayTasks(project2Task);
                    break;
                case 2:
                    ToDo.addTask(project2Task);
                    break;
                case 3:
                    ToDo.editTask(project2Task);
                    break;
                case 4:
                    ToDo.saveTasks(project2Task);
                    break;
                default:
                    System.out.println("The option chosen is not valid");
            }
            ToDo.printIntro();
            option = in.nextInt();
        }
    }

}

