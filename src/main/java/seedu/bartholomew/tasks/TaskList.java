package seedu.bartholomew.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        assert tasks != null : "Tasks list cannot be null";
        this.tasks = new ArrayList<>(tasks);
    }
    
    /**
     * Adds a task to the task list.
     *
     * @param task The task to add
     */
    public void addTask(Task task) {
        assert task != null : "Cannot add null task";
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
    
    public List<Task> deleteMultipleTasks(List<Integer> indices) throws BartholomewExceptions.InvalidTaskNumberException {
        for (int index : indices) {
            if (index < 1 || index > tasks.size()) {
                throw new BartholomewExceptions.InvalidTaskNumberException(index);
            }
        }
        
        List<Task> deletedTasks = new ArrayList<>();
        for (int index : indices) {
            deletedTasks.add(tasks.get(index - 1));
        }
        
        List<Integer> sortedIndices = new ArrayList<>(indices);
        Collections.sort(sortedIndices, Collections.reverseOrder());
        
        for (int index : sortedIndices) {
            tasks.remove(index - 1);
        }
        
        return deletedTasks;
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
     * Searches for tasks containing the given search term in their description.
     * Case-insensitive search.
     * 
     * @param searchTerm The term to search for
     * @return A list of tasks matching the search term
     */
    public List<Task> findTasks(String searchTerm) {
        String searchTermLower = searchTerm.toLowerCase();
        
        return tasks.stream()
            .filter(task -> task.getDescription().toLowerCase().contains(searchTermLower))
            .collect(Collectors.toList());
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
