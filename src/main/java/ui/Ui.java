package ui;

import java.util.List;
import java.util.Scanner;

import tasks.Task;

public class Ui {
    private final String MESSAGE_DIVIDER = "____________________________________________________________\n";
    private final Scanner scanner;
    
    public Ui() {
        scanner = new Scanner(System.in);
    }
    
    public void showWelcome() {
        String message = MESSAGE_DIVIDER
                + "Hello! I'm Bartholomew\n"
                + "What can I do for you?\n"
                + MESSAGE_DIVIDER;
        System.out.println(message);
    }
    
    public void showGoodbye() {
        String message = MESSAGE_DIVIDER
                + "Bye. Hope to see you again soon!\n"
                + MESSAGE_DIVIDER;
        System.out.println(message);
        this.scanner.close();
    }
    
    public void showTaskList(List<Task> tasks) {
        StringBuilder result = new StringBuilder(MESSAGE_DIVIDER);
        
        if (tasks.isEmpty()) {
            result.append("You have no tasks in your list.\n");
        } else {
            result.append("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                result.append(i + 1).append(". ").append(tasks.get(i).toString()).append("\n");
            }
        }
        
        result.append(MESSAGE_DIVIDER);
        System.out.println(result.toString());
    }
    
    public void showTaskAdded(Task task, int taskCount) {
        String message = MESSAGE_DIVIDER
                + "Got it. I've added this task:\n"
                + "  " + task.toString() + "\n"
                + "Now you have " + taskCount + " task"
                + (taskCount == 1 ? "" : "s") + " in the list.\n"
                + MESSAGE_DIVIDER;
        System.out.println(message);
    }
    
    public void showTaskDeleted(Task task, int taskCount) {
        String message = MESSAGE_DIVIDER
                + "Noted. I've removed this task:\n"
                + "  " + task.toString() + "\n"
                + "Now you have " + taskCount + " task"
                + (taskCount == 1 ? "" : "s") + " in the list.\n"
                + MESSAGE_DIVIDER;
        System.out.println(message);
    }
    
    public void showTaskMarked(Task task) {
        String message = MESSAGE_DIVIDER
                + "Nice! I've marked this task as done:\n"
                + "  " + task.toString() + "\n"
                + MESSAGE_DIVIDER;
        System.out.println(message);
    }
    
    public void showTaskUnmarked(Task task) {
        String message = MESSAGE_DIVIDER
                + "OK, I've marked this task as not done yet:\n"
                + "  " + task.toString() + "\n"
                + MESSAGE_DIVIDER;
        System.out.println(message);
    }
    
    public void showTasksLoaded(int taskCount) {
        String message = MESSAGE_DIVIDER
                + "Loaded " + taskCount + " task"
                + (taskCount == 1 ? "" : "s") + ".\n"
                + MESSAGE_DIVIDER;
        System.out.println(message);
    }
    
    public void showError(String message) {
        System.out.println(MESSAGE_DIVIDER + message + MESSAGE_DIVIDER);
    }
    
    public void showDateFormatError() {
        String message = MESSAGE_DIVIDER
                + "Your date and time is entered in the wrong format.\n" 
                + "Enter it in this format: 2/12/2019 1800.\n"
                + "This corresponds to 2 December 2019, 6pm.\n"
                + MESSAGE_DIVIDER;
        System.out.println(message);
    }
    
    public String readCommand() {
        return this.scanner.nextLine();
    }
}