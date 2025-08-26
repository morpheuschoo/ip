package seedu.bartholomew.tasks;

import java.util.ArrayList;
import java.util.List;

import seedu.bartholomew.exceptions.BartholomewExceptions;

public class TaskList {
    private ArrayList<Task> tasks;
    
    public TaskList() {
        this.tasks = new ArrayList<>();
    }
    
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }
    
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    public Task deleteTask(int index) throws BartholomewExceptions.InvalidTaskNumberException {
        if (index < 1 || index > tasks.size()) {
            throw new BartholomewExceptions.InvalidTaskNumberException(index);
        }
        return tasks.remove(index - 1);
    }
    
    public Task markTaskAsDone(int index) throws BartholomewExceptions.InvalidTaskNumberException {
        if (index < 1 || index > tasks.size()) {
            throw new BartholomewExceptions.InvalidTaskNumberException(index);
        }
        Task task = tasks.get(index - 1);
        task.markTask();
        return task;
    }
    
    public Task markTaskAsNotDone(int index) throws BartholomewExceptions.InvalidTaskNumberException {
        if (index < 1 || index > tasks.size()) {
            throw new BartholomewExceptions.InvalidTaskNumberException(index);
        }
        Task task = tasks.get(index - 1);
        task.unmarkTask();
        return task;
    }
    
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
        List<Task> matchingTasks = new ArrayList<>();
        String searchTermLower = searchTerm.toLowerCase();
        
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(searchTermLower)) {
                matchingTasks.add(task);
            }
        }
        
        return matchingTasks;
    }
    
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
    
    public int size() {
        return tasks.size();
    }
    
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}