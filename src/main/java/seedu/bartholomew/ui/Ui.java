package seedu.bartholomew.ui;

import java.util.List;

import seedu.bartholomew.tasks.Task;

public class Ui {
    
    public Ui() {
    }
    
    public String showWelcome() {
        String message = "Hello! I'm Bartholomew\n"
                + "What can I do for you?\n";
        return message;
    }
    
    public String showGoodbye() {
        String message = "Bye. Hope to see you again soon!\n";
        return message;
    }
    
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
    
    public String showTaskAdded(Task task, int taskCount) {
        String message = "Got it. I've added this task:\n"
                + "  " + task.toString() + "\n"
                + "Now you have " + taskCount + " task"
                + (taskCount == 1 ? "" : "s") + " in the list.\n";
        return message;
    }
    
    public String showTaskDeleted(Task task, int taskCount) {
        String message = "Noted. I've removed this task:\n"
                + "  " + task.toString() + "\n"
                + "Now you have " + taskCount + " task"
                + (taskCount == 1 ? "" : "s") + " in the list.\n";
        return message;
    }

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
    
    public String showTaskMarked(Task task) {
        String message = "Nice! I've marked this task as done:\n"
                + "  " + task.toString() + "\n";
        return message;
    }
    
    public String showTaskUnmarked(Task task) {
        String message = "OK, I've marked this task as not done yet:\n"
                + "  " + task.toString() + "\n";
        return message;
    }
    
    public String showTasksLoaded(int taskCount) {
        String message = "[I have loaded " + taskCount + " task"
                + (taskCount == 1 ? "" : "s") + "]\n";
        return message;
    }
    
    public String showError(String message) {
        return message + "\n";
    }
    
    public String showDateFormatError() {
        String message = "Your date and time is entered in the wrong format.\n" 
                + "Enter it in this format: 2/12/2019 1800.\n"
                + "This corresponds to 2 December 2019, 6pm.\n";
        return message;
    }

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