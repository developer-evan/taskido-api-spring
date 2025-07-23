package com.mogeni.taskido.repository;


import com.mogeni.taskido.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    // Additional query methods can be defined here if needed
    List<Task> findByUserId(String userId);
    List<Task> findByUserIdOrderByCreatedAtDesc(String userId);
    List<Task> findByUserIdAndCompleted(String userId, boolean completed);
    List<Task> findByUserIdAndNameContainingIgnoreCase(String userId, String name);
    List<Task> findByUserIdAndCompletedOrderByCreatedAtDesc(String userId, boolean completed);
    List<Task> findByUserIdAndNameContainingIgnoreCaseOrderByCreatedAtDesc(String userId, String name);
    List<Task> findByNameContainingIgnoreCase(String name);
    List <Task> findByCompletedOrderByCreatedAtDesc(boolean completed);
    List <Task> findAllByOrderByCreatedAtDesc();
    List <Task> findByCompleted(boolean completed);
    List<Task> findByUserIdAndCompletedAndNameContainingIgnoreCase(String userId, boolean completed, String name);
    Optional<Task> findByIdAndUserId(String id, String userId);
    void deleteByIdAndUserId(String id, String userId);
}
