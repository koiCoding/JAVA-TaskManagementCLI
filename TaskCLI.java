import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TaskCLI {
    private static final String FILE_NAME = "tasks.json";
    private static final int LINE_DELIMITER_LENGTH = 20;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide a command.");
            return;
        }

        String command = args[0];

        switch (command.toLowerCase()) {
            case "add":
                if (args.length < 2) {
                    System.out.println("Please provide a task description.");
                } else {
                    addTask(args[1]);
                }
                break;
            case "update":
                if (args.length < 3) {
                    System.out.println("Please provide a task ID and new description.");
                } else {
                    updateTask(Integer.parseInt(args[1]), args[2]);
                }
                break;
            case "delete":
                if (args.length < 2) {
                    System.out.println("Please provide a task ID.");
                } else {
                    deleteTask(Integer.parseInt(args[1]));
                }
                break;
            case "mark-in-progress":
                if (args.length < 2) {
                    System.out.println("Please provide a task ID.");
                } else {
                    markTask(Integer.parseInt(args[1]), "in-progress");
                }
                break;
            case "mark-done":
                if (args.length < 2) {
                    System.out.println("Please provide a task ID.");
                } else {
                    markTask(Integer.parseInt(args[1]), "done");
                }
                break;
            case "list":
                if (args.length < 2) {
                    listTasks("all");
                } else {
                    listTasks(args[1]);
                }
                break;
            default:
                System.out.println("Unknown command.");
        }
    }

    private static void addTask(String description) {
        ArrayList<Task> tasks = getTasks();
        Task newTask = new Task(tasks.size() + 1, description, "to-do");
        tasks.add(newTask);
        saveTasks(tasks);
        System.out.println("Task added successfully (ID: " + (tasks.size()) + ")");
    }

    private static void updateTask(int id, String newDescription) {
        ArrayList<Task> tasks = getTasks();
        for(Task task : tasks){
            if(task.getId() == id){
                task.updateTask(newDescription);
                saveTasks(tasks);
                System.out.println("Task description updated successfully.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    private static void deleteTask(int id) {
        ArrayList<Task> tasks = getTasks();
        for(Task task : tasks){
            if(task.getId() == id){
                tasks.remove(task);
                saveTasks(tasks);
                System.out.println("Task deleted sucessfully.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    private static void markTask(int id, String status) {
        ArrayList<Task> tasks = getTasks();
        for(Task task : tasks){
            if(task.getId() == id){
                task.updateStatus(status);
                saveTasks(tasks);
                return;
            }
        }
        System.out.println("Task not found.");
    }

    private static void listTasks(String filter) {
        String delimiter = "-".repeat(LINE_DELIMITER_LENGTH);
        ArrayList<Task> tasks = getTasks();
        System.out.println("List of Task:");
        System.out.println(delimiter);
        for(Task task : tasks){
            if (filter.equals("all") || filter.equals(task.getStatus())) {
                System.out.println(task.toJSONString());
            }
        }
        System.out.println(delimiter);
    }

    private static ArrayList<Task> getTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                return taskList;
            }
            String content = new String(Files.readAllBytes(Paths.get(FILE_NAME)));
            content = content.substring(1,content.length() - 1);
            String[] tasksString = content.split("(?<=\\}),\\s*(?=\\{)");
            // task is JSON String here, we want to change it to put it into our taskList
            for(String task : tasksString){
                Task currentTask = Task.fromJSONString(task);
                taskList.add(currentTask);
            }
        } catch (IOException e) {
            System.out.println("Error reading tasks file.");
        }

        return taskList;
    }

    private static void saveTasks(ArrayList<Task> taskList) {
        try (FileWriter file = new FileWriter(FILE_NAME)) {
            file.write("[");
            for(int i = 0; i < taskList.size() - 1; i++){
                Task task = taskList.get(i);
                file.write(task.toJSONString() + ",");
            }
            file.write(taskList.get(taskList.size() - 1).toJSONString());
            file.write("]");
            file.flush();
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }
}
