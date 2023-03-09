package com.example.springbootmongo.controller;

import com.example.springbootmongo.model.Task;
import com.example.springbootmongo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task){
        return taskService.addTask(task);
    }

    @GetMapping
    public List<Task> getTasks(){
        return taskService.findAllTasks();
    }

    @GetMapping("/{taskId}")
    public Task getTask(@PathVariable String taskId){
        return taskService.getTaskById(taskId);
    }

    @GetMapping("/importance/{imp}")
    public List<Task> findTaskByImp(@PathVariable int imp){
        return taskService.getTaskImp(imp);
    }

    @GetMapping("/assignee/{assignee}")
    public List<Task> getTaskByAssignee(@PathVariable String assignee){
        return taskService.getTaskByAssignee(assignee);
    }

    @PutMapping("/update/{tid}")
    public Task modifyTask(@PathVariable String tid, @RequestBody Task task){
        return taskService.updateTask(tid,task);
    }

    @DeleteMapping("/{taskId}")
    public String deleteTask(@PathVariable String taskId){
        return taskService.deleteTask(taskId);
    }


}
