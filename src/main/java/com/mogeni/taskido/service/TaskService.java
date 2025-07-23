package com.mogeni.taskido.service;

import com.mogeni.taskido.model.Task;
import com.mogeni.taskido.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasksByUserId(String userId) {
        return taskRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Optional<Task> getTaskByIdAndUserId(String id, String userId) {
        return taskRepository.findByIdAndUserId(id, userId);
    }

    public Task createTask(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task updateTaskForUser(String id, Task taskDetails, String userId) {
        Optional<Task> optionalTask = taskRepository.findByIdAndUserId(id, userId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setName(taskDetails.getName());
            task.setDescription(taskDetails.getDescription());
            task.setCompleted(taskDetails.isCompleted());
            task.setUpdatedAt(LocalDateTime.now());
            return taskRepository.save(task);
        }
        return null;
    }

    public boolean deleteTaskForUser(String id, String userId) {
        Optional<Task> optionalTask = taskRepository.findByIdAndUserId(id, userId);
        if (optionalTask.isPresent()) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Task> getTasksByCompletionStatusForUser(String userId, boolean completed) {
        return taskRepository.findByUserIdAndCompletedOrderByCreatedAtDesc(userId, completed);
    }

    public List<Task> searchTasksByNameForUser(String userId, String name) {
        return taskRepository.findByUserIdAndNameContainingIgnoreCase(userId, name);
    }

    public Task toggleTaskCompletionForUser(String id, String userId) {
        Optional<Task> optionalTask = taskRepository.findByIdAndUserId(id, userId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setCompleted(!task.isCompleted());
            task.setUpdatedAt(LocalDateTime.now());
            return taskRepository.save(task);
        }
        return null;
    }


}
