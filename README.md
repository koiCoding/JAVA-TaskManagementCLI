# JAVA-Project1-TaskManagementCLI

# Task CLI

**Task CLI** is a command-line tool for task management feature. The tool provides different actions including add, update, delete, and track tasks based on their status.

## Features

- Add new tasks
- Update existing tasks
- Delete tasks
- Mark tasks as "In Progress" or "Done."
- List tasks by status

## Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/task-cli.git
   ```
2. Navigate to local repository:
   ```bash
   cd local_path
   ```
3. Run the following command in the terminal:
  ```bash
  java -cp "lib/json.jar;." TaskCLI command arguments
  ```

## Usage

### Adding a New Task

To add a new task, use the following command:
```bash
add "Buy groceries"
```
**Output:**
```
Task added successfully (ID: 1)
```

### Updating a Task

To update an existing task, use the `update` command:
```bash
update 1 "Buy groceries and cook dinner"
```

### Deleting a Task

To delete a task, use the `delete` command:
```bash
delete 1
```

### Marking a Task

- To mark a task as "In Progress":
  ```bash
  mark-in-progress 1
  ```

- To mark a task as "Done":
  ```bash
  mark-done 1
  ```

### Listing Tasks

- To list all tasks:
  ```bash
  list
  ```

- To list tasks by status:
  ```bash
  list done
  list todo
  list in-progress
  ```


