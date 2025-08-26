package seedu.bartholomew.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private LocalDateTime dueDate;
    
    private static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    private static final DateTimeFormatter DISPLAY_FORMATTER = 
            DateTimeFormatter.ofPattern("d MMMM yyyy, h:mma");

    public Deadline(String desc, String dueDate) throws DateTimeParseException {
        super(desc);
        this.dueDate = LocalDateTime.parse(dueDate, INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.dueDate.format(DISPLAY_FORMATTER) + ")"; 
    }

    public String getDueDate() {
        return this.dueDate.format(INPUT_FORMAT);
    }
}
