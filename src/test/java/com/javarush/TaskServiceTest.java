package com.javarush;

import com.javarush.domain.Status;
import com.javarush.domain.Task;
import com.javarush.service.TaskService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(classes = SpringProjectApplication.class)
@TestPropertySource("/h2.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskServiceTest {

    @Autowired
    public TaskService taskService;

    private static long id;

    @Test
    @Order(1)
    public void whenTaskSavedThenCanGetTaskById(){
        Task task = new Task();
        task.setDescription("Testing the app");
        task.setStatus(Status.IN_PROGRESS);
        taskService.saveTask(task);
        assertNotEquals(task.getId(), 0);
        id = task.getId();

        Task taskFromDB = taskService.getTaskById(id);
        assertEquals(taskFromDB.getDescription(), "Testing the app");
        assertEquals(taskFromDB.getStatus(), Status.IN_PROGRESS);
    }

    @Test
    @Order(2)
    public void whenTaskSavedThenCanDeleteIt(){
        taskService.deleteById(id);
        RuntimeException re = assertThrows(RuntimeException.class, () -> taskService.getTaskById(id));
        assertEquals(re.getMessage(), "Task not found!");
    }
}
