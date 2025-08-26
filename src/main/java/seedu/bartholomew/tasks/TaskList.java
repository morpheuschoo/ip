package seedu.bartholomew.tasks;

import java.util.ArrayList;
import java.util.List;

import seedu.bartholomew.exceptions.BartholomewExceptions;

/**
 * Manages a collection of tasks with operations to add, delete, and modify tasks.
 * Provides methods for task management and retrieval.
 */
public class TaskList {
    private ArrayList<Task> tasks;
    
    /**
     * Creates a new empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }
    
    /**
     * Creates a new task list with the provided initial tasks.
     *
     * @param tasks The initial list of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }
    
    /**
     * Adds a task to the task list.
     *
     * @param task The task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    /**
     * Deletes a task from the task list by index.
     *
     * @param index The one-based index of the task to delete
     * @return The deleted task
     * @throws BartholomewExceptions.InvalidTaskNumberException If the index is invalid
     */
    public Task deleteTask(int index) throws BartholomewExceptions.InvalidTaskNumberException {
        if (index < 1 || index > tasks.size()) {
            throw new BartholomewExceptions.InvalidTaskNumberException(index);
        }
        return tasks.remove(index - 1);
    }
    
    /**
     * Marks a task as completed.
     *
     * @param index The one-based index of the task to mark as completed
     * @return The marked task
     * @throws BartholomewExceptions.InvalidTaskNumberException If the index is invalid
     */
    public Task markTaskAsDone(int index) throws BartholomewExceptions.InvalidTaskNumberException {
        if (index < 1 || index > tasks.size()) {
            throw new BartholomewExceptions.InvalidTaskNumberException(index);
        }
        Task task = tasks.get(index - 1);
        task.markTask();
        return task;
    }
    
    /**
     * Marks a task as not completed.
     *
     * @param index The one-based index of the task to mark as not completed
     * @return The unmarked task
     * @throws BartholomewExceptions.InvalidTaskNumberException If the index is invalid
     */
    public Task markTaskAsNotDone(int index) throws BartholomewExceptions.InvalidTaskNumberException {
        if (index < 1 || index > tasks.size()) {
            throw new BartholomewExceptions.InvalidTaskNumberException(index);
        }
        Task task = tasks.get(index - 1);
        task.unmarkTask();
        return task;
    }
    
    /**
     * Gets a task by its index.
     *
     * @param index The one-based index of the task to retrieve
     * @return The requested task
     * @throws BartholomewExceptions.InvalidTaskNumberException If the index is invalid
     */
    public Task getTask(int index) throws BartholomewExceptions.InvalidTaskNumberException {
        if (index < 1 || index > tasks.size()) {
            throw new BartholomewExceptions.InvalidTaskNumberException(index);
        }
        return tasks.get(index - 1);
    }
    
    /**
     * Gets all tasks in the task list.
     * Returns a defensive copy to prevent external modification of the internal list.
     *
     * @return A list containing all tasks
     */
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
    
    /**
     * Gets the number of tasks in the task list.
     *
     * @return The number of tasks
     */
    public int size() {
        return tasks.size();
    }
    
    /**
     * Checks if the task list is empty.
     *
     * @return true if the task list contains no tasks, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}