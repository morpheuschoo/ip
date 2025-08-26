package seedu.bartholomew.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline (due date and time).
 * Extends the base Task class.
 */
public class Deadline extends Task {
    private LocalDateTime dueDate;
    
    /** Format for parsing date and time input from the user. */
    private static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /** Format for displaying date and time to the user. */
    private static final DateTimeFormatter DISPLAY_FORMATTER = 
            DateTimeFormatter.ofPattern("d MMMM yyyy, h:mma");

    /**
     * Creates a new deadline task with the given description and due date.
     *
     * @param desc The description of the deadline task
     * @param dueDate The due date and time in the format "d/M/yyyy HHmm" (e.g., "25/12/2023 1430")
     * @throws DateTimeParseException If the due date format is invalid
     */
    public Deadline(String desc, String dueDate) throws DateTimeParseException {
        super(desc);
        this.dueDate = LocalDateTime.parse(dueDate, INPUT_FORMAT);
    }

    /**
     * Returns a string representation of the deadline task.
     * Prefixes the base task representation with a [D] to indicate a deadline task,
     * and appends the due date in a readable format.
     *
     * @return The string representation of the deadline task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.dueDate.format(DISPLAY_FORMATTER) + ")"; 
    }

    /**
     * Gets the due date of the deadline task in the input format.
     *
     * @return The due date as a string in the format "d/M/yyyy HHmm"
     */
    public String getDueDate() {
        return this.dueDate.format(INPUT_FORMAT);
    }
}