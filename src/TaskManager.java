package advancedstudytimer;

import java.util.ArrayList;

public class TaskManager {
    // Task class (representing a task with name and duration)
    public static class Task {
        private String taskName;
        private int durationSeconds;  // Duration in seconds

        // Constructor
        public Task(String taskName, int durationSeconds) {
            this.taskName = taskName;
            this.durationSeconds = durationSeconds;
        }

        // Getter methods
        public String getTaskName() {
            return taskName;
        }

        public int getDurationSeconds() {
            return durationSeconds;
        }

        // Override toString() method to display task name
        @Override
        public String toString() {
            return taskName;  // This will return the task name when displayed in the list
        }
    }

    // List of tasks
    private ArrayList<Task> tasks = new ArrayList<>();

    // Method to add a task
    public void addTask(String taskName, int durationSeconds) {
        tasks.add(new Task(taskName, durationSeconds));
    }

    // Method to remove tasks
    public void removeTasks(ArrayList<Task> tasksToRemove) {
        tasks.removeAll(tasksToRemove);
    }

    // Method to get all tasks
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
