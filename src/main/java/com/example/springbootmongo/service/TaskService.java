package com.example.springbootmongo.service;

import com.example.springbootmongo.model.Task;
import com.example.springbootmongo.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TasksRepository repository;

    public Task addTask(Task task){
        return repository.save(task);
    }

    public List<Task> findAllTasks(){
       return repository.findAll();
    }

    public Task getTaskById(String taskId){
        return repository.findById(taskId).get();
    }

    public List<Task> getTaskImp(int imp){
        return repository.findByImportance(imp);
    }

    public List<Task> getTaskByAssignee(String assignee){
        return repository.getTaskByAssignee(assignee);
    }
    public Task updateTask(String tid,Task task){
        Task exisitingTask = getTaskById(tid);
        exisitingTask.setDescription(task.getDescription());
        exisitingTask.setAssignee(task.getAssignee());
        exisitingTask.setImportance(task.getImportance());
        return repository.save(exisitingTask);
    }

    public String deleteTask(String taskId){
        repository.deleteById(taskId);
        return taskId+" task deleted successfully";
    }


}
