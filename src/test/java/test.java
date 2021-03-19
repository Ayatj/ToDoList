import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import toDoList.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {
    Map<String, List<Task>> project2Task = new HashMap<>();
    Task task1 = new Task("swimming", LocalDate.parse("2020-03-06"), false, "sport");
    Task task2 = new Task("running", LocalDate.parse("2020-03-07"), false, "sport");
    Task task3 = new Task("milk", LocalDate.parse("2020-03-08"), false, "shopping");
    Task task4 = new Task("banana", LocalDate.parse("2020-03-09"), false, "shopping");
    @Test
    @DisplayName("Add Task Test")
    public void addTaskTest()  {
        assertEquals(project2Task.size(), 0);
        ToDo.addToMap(project2Task, task1);
        assertEquals(project2Task.size(),1);
        ToDo.addToMap(project2Task, task2);
        ToDo.addToMap(project2Task, task3);
        ToDo.addToMap(project2Task, task4);
        assertEquals(project2Task.size(),2);
    }

    @Test
    @DisplayName("Get Task Test")
    public void getaskTest()  {
        addTaskTest();
        assertEquals(ToDo.getTask(project2Task,"sport", "swimming",null),task1);
        assertEquals(ToDo.getTask(project2Task,"sport", "running",null),task2);
        assertEquals(ToDo.getTask(project2Task,"home", "cleaning",null),null);
    }

    @Test
    @DisplayName("Display Task Test")
    public void displayTaskTest()  {
        addTaskTest();
        ToDo.displayByDate(project2Task);
    }

    @Test
    @DisplayName("Edi Task Test")
    public void editTaskTest()  {
        addTaskTest();
        ToDo.EditTaskTitle(task1, "gym");
        ToDo.deleteTask(project2Task,"shopping",task4);
        ToDo.displayByDate(project2Task);
    }

}
