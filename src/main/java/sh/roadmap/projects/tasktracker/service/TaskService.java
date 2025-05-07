package sh.roadmap.projects.tasktracker.service;

import org.springframework.stereotype.Service;

import sh.roadmap.projects.tasktracker.model.Task;

@Service
public class TaskService {

    public Task createTask(String description) {
        Task task = new Task(description);
        System.out.println("Task created: " + task.toString());
        // TODO: Save the task to JsonRepository latter
        return task;
    }

}
