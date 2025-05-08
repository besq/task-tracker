package sh.roadmap.projects.tasktracker.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

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
        taskRepository.save(task);
        return task;
    }

}
