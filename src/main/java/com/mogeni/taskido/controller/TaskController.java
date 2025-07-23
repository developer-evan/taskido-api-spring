package com.mogeni.taskido.controller;

//import org.springframework.web.bind.annotation.CrossOrigin;
import com.mogeni.taskido.model.Task;
import com.mogeni.taskido.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*") // Allow all origins for CORS
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Ensure only authenticated users can access these endpoints
@Tag(name = "Tasks", description = "Task management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    @Autowired
    private TaskService taskService; // Assuming you have a TaskService to handle business logic

    // This class will handle HTTP requests related to tasks
    // You can define methods here to handle CRUD operations for tasks
    // For example:
    // - @GetMapping to retrieve tasks
    @Operation(summary = "Get all tasks", description = "Retrieve all tasks for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tasks",
                    content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        List<Task> tasks = taskService.getAllTasksByUserId(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // - @GetMapping("/{id}") to retrieve a specific task by ID
    @Operation(summary = "Get task by ID", description = "Retrieve a specific task by its ID for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(
            @Parameter(description = "Task ID") @PathVariable String id,
            HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        Optional<Task> task = taskService.getTaskByIdAndUserId(id, userId);
        return task.map(t -> new ResponseEntity<>(t, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // - @PostMapping to create a new task
    @Operation(summary = "Create a new task", description = "Create a new task for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "400", description = "Invalid task data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        task.setUserId(userId);
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }
    // - @PutMapping to update an existing task
    @Operation(summary = "Update a task", description = "Update an existing task for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully",
                    content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "400", description = "Invalid task data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @Parameter(description = "Task ID") @PathVariable String id,
            @Valid @RequestBody Task taskDetails,
            HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        Task updatedTask = taskService.updateTaskForUser(id, taskDetails, userId);
        if (updatedTask != null) {
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // - @DeleteMapping to delete a task
    @Operation(summary = "Delete a task", description = "Delete a specific task for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "Task ID") @PathVariable String id,
            HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        boolean deleted = taskService.deleteTaskForUser(id, userId);
        return deleted
            ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // - @GetMapping("/completed") to get tasks by completion status
    @Operation(summary = "Get tasks by completion status", description = "Retrieve tasks filtered by completion status for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tasks",
                    content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/status/{completed}")
    public ResponseEntity<List<Task>> getTasksByCompletionStatus(
            @Parameter(description = "Completion status (true for completed, false for pending)") @PathVariable boolean completed,
            HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        List<Task> tasks = taskService.getTasksByCompletionStatusForUser(userId, completed);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // - @GetMapping("/search") to search tasks by name
    @Operation(summary = "Search tasks by name", description = "Search tasks by name for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved matching tasks",
                    content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasksByName(
            @Parameter(description = "Task name to search for") @RequestParam String name,
            HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        List<Task> tasks = taskService.searchTasksByNameForUser(userId, name);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // - @PatchMapping("/{id}/toggle") to toggle task completion status
    @Operation(summary = "Toggle task completion", description = "Toggle the completion status of a specific task for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task completion status toggled successfully",
                    content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Task> toggleTaskCompletion(
            @Parameter(description = "Task ID") @PathVariable String id,
            HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        Task updatedTask = taskService.toggleTaskCompletionForUser(id, userId);
        return updatedTask != null
            ? new ResponseEntity<>(updatedTask, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
