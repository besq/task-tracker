package sh.roadmap.projects.tasktracker.command;

import org.springframework.shell.command.annotation.Command;

import jakarta.validation.constraints.NotBlank;
import sh.roadmap.projects.tasktracker.model.Task;
import sh.roadmap.projects.tasktracker.service.TaskService;

@Command(group = "Task Tracker Commands", description = "Task Tracker Commands", command = "task-cli")
public class TaskTrackerCommand {

    private final TaskService taskService;

    public TaskTrackerCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    @Command(command = "add", description = "Add a new task.")
    public String add(@NotBlank String taskDescription) {
        Task task = taskService.addTask(taskDescription);
        if (task == null) {
            return "Failed to add task.";
        }
        return "Task added successfully (ID: " + task.getId() + ")";
    }

    // @Command(command = "update", description = "Update an existing task")
    // public String update(String taskId, String newDescription) {

    //     if (taskId == null || taskId.isEmpty()) {
    //         return "Task ID cannot be null or empty.";
    //     }
    //     if (newDescription == null || newDescription.isEmpty()) {
    //         return "New description cannot be null or empty.";
    //     }

    //     Task updatedTask = taskService.updateTask(taskId, newDescription);
    //     if (updatedTask == null) {
    //         return "Task not found (ID: " + taskId + ")";
    //     }
    //     System.out.println("Task updated successfully: " + updatedTask.toString());
    //     return "Task updated successfully (ID: " + updatedTask.getId() + ")";
    // }
}
