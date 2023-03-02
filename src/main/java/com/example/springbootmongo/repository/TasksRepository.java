package com.example.springbootmongo.repository;

import com.example.springbootmongo.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TasksRepository extends MongoRepository<Task,String> {

    List<Task> findByImportance(int imp);

    @Query("{assignee : ?0 }")
    List<Task> getTaskByAssignee(String assignee);
}
