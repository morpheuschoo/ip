package seedu.bartholomew.tasks;

/**
 * Represents a simple to-do task without date or time constraints.
 * Extends the base Task class.
 */
public class ToDo extends Task {
    
    /**
     * Creates a new to-do task with the given description.
     *
     * @param desc The description of the to-do task
     */
    public ToDo(String desc) {
        super(desc);
    }

    /**
     * Returns a string representation of the to-do task.
     * Prefixes the base task representation with a [T] to indicate a to-do task.
     *
     * @return The string representation of the to-do task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}