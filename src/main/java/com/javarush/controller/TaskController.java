package com.javarush.controller;

import com.javarush.domain.Status;
import com.javarush.domain.Task;
import com.javarush.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String getHomePage(Model model){
        return findPaginated(1,"description","asc",model);
    }

    @GetMapping("/showNewTaskForm")
    public String showNewTaskForm(Model model){
        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("options", Status.values());
        return "new_task";
    }

    @PostMapping("/saveTask")
    public String saveNewTask(@ModelAttribute("task") Task task){
        taskService.saveTask(task);
        return "redirect:/";
    }

    @GetMapping("/deleteById/{id}")
    public String deleteById(@PathVariable(value = "id") long id){
        taskService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model){
        Task task = taskService.getTaskById(id);
        model.addAttribute("task",task);
        model.addAttribute("options", Status.values());
        return "update_task";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model){
        int pageSize = 5;
        Page<Task> page = taskService.findPaginated(pageNo,pageSize,sortField,sortDir);
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir.equals("asc")?"desc":"asc");
        model.addAttribute("listTasks",page.getContent());
        return "index";
    }
}
