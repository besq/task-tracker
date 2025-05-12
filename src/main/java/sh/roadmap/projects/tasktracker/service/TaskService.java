package sh.roadmap.projects.tasktracker.service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import sh.roadmap.projects.tasktracker.exception.TaskNotFoundException;
import sh.roadmap.projects.tasktracker.model.Task;
import sh.roadmap.projects.tasktracker.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(String description) {
        Task task = new Task(idCounter.getAndIncrement(), description);
        return taskRepository.save(task);
    }

    public Task updateTask(Integer id, String newDescription) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            task.get().setDescription(newDescription);
            return taskRepository.save(task.get());
        } else {
            throw new TaskNotFoundException("Task with ID " + id + " not found.");
        }
    }

}
