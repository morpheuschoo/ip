package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.time.format.DateTimeParseException;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    private static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    private static final DateTimeFormatter DISPLAY_FORMATTER = 
            DateTimeFormatter.ofPattern("d MMMM yyyy, h:mma");
    
    public Event(String desc, String from, String to) throws DateTimeParseException {
        super(desc);
        this.from = LocalDateTime.parse(from, INPUT_FORMAT);
        this.to = LocalDateTime.parse(to, INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from.format(DISPLAY_FORMATTER) 
                + " to: " + this.to.format(DISPLAY_FORMATTER) + ")";
    }

    public String getFrom() {
        return this.from.format(INPUT_FORMAT);
    }

    public String getTo() {
        return this.to.format(INPUT_FORMAT);
    }
}
