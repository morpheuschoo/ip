package seedu.bartholomew.tasks;

/**
 * Represents a task in the Bartholomew task management system.
 * This is the base class for all task types.
 */
public class Task {
    private String desc;
    private boolean completed;

    /**
     * Creates a new task with the given description.
     * New tasks are initially marked as not completed.
     *
     * @param desc The description of the task
     */
    public Task(String desc) {
        this.desc = desc;
        this.completed = false;
    }

    /**
     * Returns a string representation of the task.
     * Includes the completion status ([X] for completed, [ ] for not completed)
     * followed by the task description.
     *
     * @return The string representation of the task
     */
    @Override
    public String toString() {
        return "[" + (this.completed ? "X" : " ") + "] " + this.desc;
    }

    /**
     * Marks the task as completed.
     */
    public void markTask() {
        this.completed = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmarkTask() {
        this.completed = false;
    }

    /**
     * Gets the description of the task.
     *
     * @return The task description
     */
    public String getDescription() {
        return this.desc;
    }

    /**
     * Checks if the task is marked as completed.
     *
     * @return true if the task is completed, false otherwise
     */
    public boolean isDone() {
        return this.completed;
    }
}
