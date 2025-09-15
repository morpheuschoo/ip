package seedu.bartholomew.ui;

import java.util.List;

import seedu.bartholomew.tasks.Task;

/**
 * Handles user interface messages for Bartholomew.
 * <p>
 * Documentation for this class and its methods was generated using GitHub Copilot.
 */
public class Ui {
    
    /**
     * Constructs a Ui object.
     * Documentation generated using GitHub Copilot.
     */
    public Ui() {
    }
    
    /**
     * Returns the welcome message.
     * @return Welcome message string.
     * Documentation generated using GitHub Copilot.
     */
    public String showWelcome() {
        String message = "Hello! I'm Bartholomew\n"
                + "What can I do for you?\n";
        return message;
    }
    
    /**
     * Returns the goodbye message.
     * @return Goodbye message string.
     * Documentation generated using GitHub Copilot.
     */
    public String showGoodbye() {
        String message = "Bye. Hope to see you again soon!\n";
        return message;
    }
    
    /**
     * Returns a formatted list of tasks.
     * @param tasks List of tasks to display.
     * @return Formatted task list string.
     * Documentation generated using GitHub Copilot.
     */
    public String showTaskList(List<Task> tasks) {
        StringBuilder result = new StringBuilder();
        
        if (tasks.isEmpty()) {
            result.append("You have no tasks in your list.\n");
        } else {
            result.append("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                result.append(i + 1).append(". ").append(tasks.get(i).toString()).append("\n");
            }
        }
        
        return result.toString();
    }
    
    /**
     * Returns a message indicating a task was added.
     * @param task The task added.
     * @param taskCount The current number of tasks.
     * @return Task added message string.
     * Documentation generated using GitHub Copilot.
     */
    public String showTaskAdded(Task task, int taskCount) {
        String message = "Got it. I've added this task:\n"
                + "  " + task.toString() + "\n"
                + "Now you have " + taskCount + " task"
                + (taskCount == 1 ? "" : "s") + " in the list.\n";
        return message;
    }
    
    /**
     * Returns a message indicating a task was deleted.
     * @param task The task deleted.
     * @param taskCount The current number of tasks.
     * @return Task deleted message string.
     * Documentation generated using GitHub Copilot.
     */
    public String showTaskDeleted(Task task, int taskCount) {
        String message = "Noted. I've removed this task:\n"
                + "  " + task.toString() + "\n"
                + "Now you have " + taskCount + " task"
                + (taskCount == 1 ? "" : "s") + " in the list.\n";
        return message;
    }

    /**
     * Returns a message indicating multiple tasks were deleted.
     * @param deletedTasks List of deleted tasks.
     * @param remainingTaskCount Number of tasks remaining.
     * @return Multiple tasks deleted message string.
     * Documentation generated using GitHub Copilot.
     */
    public String showMultipleTasksDeleted(List<Task> deletedTasks, int remainingTaskCount) {
        StringBuilder message = new StringBuilder("Noted. I've removed these tasks:\n");
        
        for (int i = 0; i < deletedTasks.size(); i++) {
            message.append("  ").append(i + 1).append(". ")
                .append(deletedTasks.get(i)).append("\n");
        }
        
        message.append("Now you have ").append(remainingTaskCount)
            .append(remainingTaskCount == 1 ? " task" : " tasks")
            .append(" in the list.\n");
        
        return message.toString();
    }
    
    /**
     * Returns a message indicating a task was marked as done.
     * @param task The task marked as done.
     * @return Task marked message string.
     * Documentation generated using GitHub Copilot.
     */
    public String showTaskMarked(Task task) {
        String message = "Nice! I've marked this task as done:\n"
                + "  " + task.toString() + "\n";
        return message;
    }
    
    /**
     * Returns a message indicating a task was unmarked.
     * @param task The task unmarked.
     * @return Task unmarked message string.
     * Documentation generated using GitHub Copilot.
     */
    public String showTaskUnmarked(Task task) {
        String message = "OK, I've marked this task as not done yet:\n"
                + "  " + task.toString() + "\n";
        return message;
    }
    
    /**
     * Returns a message indicating tasks were loaded.
     * @param taskCount Number of tasks loaded.
     * @return Tasks loaded message string.
     * Documentation generated using GitHub Copilot.
     */
    public String showTasksLoaded(int taskCount) {
        String message = "[I have loaded " + taskCount + " task"
                + (taskCount == 1 ? "" : "s") + "]\n";
        return message;
    }
    
    /**
     * Returns an error message.
     * @param message The error message.
     * @return Error message string.
     * Documentation generated using GitHub Copilot.
     */
    public String showError(String message) {
        return message + "\n";
    }
    
    /**
     * Returns a message indicating the date format is incorrect.
     * @return Date format error message string.
     * Documentation generated using GitHub Copilot.
     */
    public String showDateFormatError() {
        String message = "Your date and time is entered in the wrong format.\n" 
                + "Enter it in this format: 2/12/2019 1800.\n"
                + "This corresponds to 2 December 2019, 6pm.\n";
        return message;
    }

    /**
     * Returns a formatted list of search results.
     * @param tasks List of matching tasks.
     * @param searchTerm The search term used.
     * @return Search results message string.
     * Documentation generated using GitHub Copilot.
     */
    public String showSearchResults(List<Task> tasks, String searchTerm) {
        StringBuilder result = new StringBuilder();
        
        if (tasks.isEmpty()) {
            result.append("No matching tasks found for: \"").append(searchTerm).append("\"\n");
        } else {
            result.append("Here are the matching tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                result.append(i + 1).append(". ").append(tasks.get(i).toString()).append("\n");
            }
        }
        
        return result.toString();
    }
}