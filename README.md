> ⚠️ **Important:** This project does **<span style="color:red;">NOT</span>** meet the requirement: **<span style="color:cyan;">"_Do not use any external libraries or frameworks to build this project_"</span>**.

I do use Spring Boot framework! \
I am working on this project to learn how to use Spring Shell and to share it with others who might also be interested. Please take note!

# Getting Started

### Working Space
Make sure that you're standing in the project code base folder, which is `task-tracker`.
```bash
cd /path/to/your/space/task-tracker
```

### Build
```bash
./mvnw clean install -DskipTests 
```

### Run
```bash
java -jar ./target/task-tracker-0.0.1-SNAPSHOT.jar
```

# Usage
```bash
# Adding a new task
task-cli add "Buy groceries"
# Output: Task added successfully (ID: 1)

# Updating and deleting tasks
task-cli update 1 "Buy groceries and cook dinner"
task-cli delete 1

# Marking a task as in progress or done
task-cli mark-in-progress 1
task-cli mark-done 1

# Listing all tasks
task-cli list
# Listing tasks by status
task-cli list done
task-cli list todo
task-cli list in-progress
```