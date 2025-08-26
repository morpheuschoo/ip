package seedu.bartholomew.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event with start and end times.
 * Extends the base Task class.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /** Format for parsing date and time input from the user. */
    private static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /** Format for displaying date and time to the user. */
    private static final DateTimeFormatter DISPLAY_FORMATTER = 
            DateTimeFormatter.ofPattern("d MMMM yyyy, h:mma");
    
    /**
     * Creates a new event task with the given description, start time, and end time.
     *
     * @param desc The description of the event
     * @param from The start date and time in the format "d/M/yyyy HHmm"
     * @param to The end date and time in the format "d/M/yyyy HHmm"
     * @throws DateTimeParseException If either time format is invalid
     */
    public Event(String desc, String from, String to) throws DateTimeParseException {
        super(desc);
        this.from = LocalDateTime.parse(from, INPUT_FORMAT);
        this.to = LocalDateTime.parse(to, INPUT_FORMAT);
    }

    /**
     * Returns a string representation of the event task.
     * Prefixes the base task representation with an [E] to indicate an event task,
     * and appends the start and end times in a readable format.
     *
     * @return The string representation of the event task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from.format(DISPLAY_FORMATTER) 
                + " to: " + this.to.format(DISPLAY_FORMATTER) + ")";
    }

    /**
     * Gets the start time of the event in the input format.
     *
     * @return The start time as a string in the format "d/M/yyyy HHmm"
     */
    public String getFrom() {
        return this.from.format(INPUT_FORMAT);
    }

    /**
     * Gets the end time of the event in the input format.
     *
     * @return The end time as a string in the format "d/M/yyyy HHmm"
     */
    public String getTo() {
        return this.to.format(INPUT_FORMAT);
    }
}