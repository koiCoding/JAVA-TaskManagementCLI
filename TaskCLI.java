import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TaskCLI {
    private static final String FILE_NAME = "tasks.json";

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
        JSONArray tasks = getTasks();
        JSONObject newTask = new JSONObject();
        newTask.put("id", tasks.length() + 1);
        newTask.put("description", description);
        newTask.put("status", "todo");
        tasks.put(newTask);
        saveTasks(tasks);
        System.out.println("Task added successfully (ID: " + (tasks.length()) + ")");
    }

    private static void updateTask(int id, String newDescription) {
        JSONArray tasks = getTasks();
        for (int i = 0; i < tasks.length(); i++) {
            JSONObject task = tasks.getJSONObject(i);
            if (task.getInt("id") == id) {
                task.put("description", newDescription);
                saveTasks(tasks);
                System.out.println("Task updated successfully.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    private static void deleteTask(int id) {
        JSONArray tasks = getTasks();
        for (int i = 0; i < tasks.length(); i++) {
            if (tasks.getJSONObject(i).getInt("id") == id) {
                tasks.remove(i);
                saveTasks(tasks);
                System.out.println("Task deleted successfully.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    private static void markTask(int id, String status) {
        JSONArray tasks = getTasks();
        for (int i = 0; i < tasks.length(); i++) {
            JSONObject task = tasks.getJSONObject(i);
            if (task.getInt("id") == id) {
                task.put("status", status);
                saveTasks(tasks);
                System.out.println("Task marked as " + status + ".");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    private static void listTasks(String filter) {
        JSONArray tasks = getTasks();
        for (int i = 0; i < tasks.length(); i++) {
            JSONObject task = tasks.getJSONObject(i);
            String status = task.getString("status");

            if (filter.equals("all") ||
                (filter.equals("done") && status.equals("done")) ||
                (filter.equals("todo") && status.equals("todo")) ||
                (filter.equals("in-progress") && status.equals("in-progress"))) {
                System.out.println("ID: " + task.getInt("id") + ", Description: " + task.getString("description") + ", Status: " + status);
            }
        }
    }

    private static JSONArray getTasks() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                return new JSONArray();
            }
            String content = new String(Files.readAllBytes(Paths.get(FILE_NAME)));
            return new JSONArray(content);
        } catch (IOException e) {
            System.out.println("Error reading tasks file.");
            return new JSONArray();
        }
    }

    private static void saveTasks(JSONArray tasks) {
        try (FileWriter file = new FileWriter(FILE_NAME)) {
            file.write(tasks.toString());
            file.flush();
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }
}
