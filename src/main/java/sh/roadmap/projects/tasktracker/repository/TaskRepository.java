package sh.roadmap.projects.tasktracker.repository;

import org.springframework.stereotype.Component;

import sh.roadmap.projects.tasktracker.config.JsonProperties;
import sh.roadmap.projects.tasktracker.model.Task;

@Component
public class TaskRepository extends JsonRepository<Task, Integer> {

    public TaskRepository(JsonProperties jsonProperties) {
        super(jsonProperties.getDataFile(), Task.class, Task::getId);
    }

}
