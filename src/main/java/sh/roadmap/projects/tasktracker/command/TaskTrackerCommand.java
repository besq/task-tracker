package sh.roadmap.projects.tasktracker.command;

import org.springframework.shell.command.annotation.Command;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import sh.roadmap.projects.tasktracker.model.Task;
import sh.roadmap.projects.tasktracker.service.TaskService;

@Command(group = "Task Tracker Commands", description = "Task Tracker Commands", command = "task-cli")
public class TaskTrackerCommand {

    private final TaskService taskService;

    public TaskTrackerCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    @Command(command = "add", description = "Add a new task.")
    public String add(String taskDescription) {
        Task task = taskService.addTask(taskDescription);
        return "Task added successfully (ID: " + task.getId() + ")";
    }

    // command update
    @Command(command = "update", description = "Update an existing task.")
    public String update(@Positive @Min(1) Integer id, @NotBlank String newDescription) {
        Task task = taskService.updateTask(id, newDescription);
        return "Task updated successfully (ID: " + task.getId() + ")";
    }
}
