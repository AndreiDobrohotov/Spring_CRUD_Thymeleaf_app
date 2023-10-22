package com.javarush.service;

import com.javarush.domain.Task;
import com.javarush.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public void saveTask(Task task){
        taskRepository.save(task);
    }

    public void deleteById(Long id){
        taskRepository.deleteById(id);
    }

    public Task getTaskById(Long id){
        Optional<Task> optional = taskRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new RuntimeException("Task not found!");
    }


    public Page<Task> findPaginated(int pageNumber, int pageSize, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize, sort);
        return taskRepository.findAll(pageable);
    }

}
