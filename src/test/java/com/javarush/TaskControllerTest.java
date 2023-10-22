package com.javarush;

import com.javarush.controller.TaskController;
import com.javarush.domain.Status;
import com.javarush.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@TestPropertySource("/h2.properties")
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    public TaskController taskController;


    @Test
    public void titlePageTest() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Description")))
                .andExpect(content().string(containsString("Status")))
                .andExpect(content().string(containsString("Id")));
    }

    @Test
    public void testSaveWithRedirect() throws Exception {
        Task task = new Task();
        task.setId(5L);
        task.setDescription("test task");
        task.setStatus(Status.PAUSED);
        mockMvc.perform(post("/saveTask")
                        .flashAttr("task", task))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testDeleteByIdWithRedirect() throws Exception {
        mockMvc.perform(get("/deleteById/{id}", 5L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }


}
