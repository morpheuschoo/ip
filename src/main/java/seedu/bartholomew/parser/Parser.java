package seedu.bartholomew.parser;

import java.time.format.DateTimeParseException;

import seedu.bartholomew.command.CommandType;
import seedu.bartholomew.tasks.Deadline;
import seedu.bartholomew.tasks.Event;
import seedu.bartholomew.tasks.Task;
import seedu.bartholomew.tasks.ToDo;

import seedu.bartholomew.exceptions.BartholomewExceptions;

public class Parser {

    public CommandType parseCommandType(String input) {
        return CommandType.fromString(input);
    }

    public int parseTaskNumber(String input, CommandType command, int totalTasks) 
            throws BartholomewExceptions.InvalidTaskNumberException {
        int prefixLen = 0;
        switch (command) {
        case MARK:
            prefixLen = 4;
            break;
        case UNMARK:
        case DELETE:
            prefixLen = 6;
            break;
        default:
            throw new IllegalArgumentException(command.name() + " cannot be used with parseTaskNumber");
        }

        try {
            String numberPart = input.substring(prefixLen).trim();

            if (numberPart.isEmpty()) {
                throw new BartholomewExceptions.InvalidTaskNumberException("");
            }

            int taskNo = Integer.parseInt(numberPart);

            if (taskNo <= 0 || taskNo > totalTasks) {
                throw new BartholomewExceptions.InvalidTaskNumberException(taskNo);
            }

            return taskNo;
        } catch (NumberFormatException e) {
            String invalidNumber = input.substring(prefixLen).trim();
            throw new BartholomewExceptions.InvalidTaskNumberException(invalidNumber);
        }
    }

    public Task parseTask(String input) 
            throws BartholomewExceptions, DateTimeParseException {
        if (input.startsWith("todo")) {
            return parseTodo(input);
        } else if (input.startsWith("deadline")) {
            return parseDeadline(input);
        } else if (input.startsWith("event")) {
            return parseEvent(input);
        } else {
            throw new BartholomewExceptions.UnknownCommandException(input);
        }
    }

    private Task parseTodo(String input) throws BartholomewExceptions.EmptyDescriptionException {
        String desc = input.substring(4).strip();
        if (desc.isEmpty()) {
            throw new BartholomewExceptions.EmptyDescriptionException("todo");
        }
        return new ToDo(desc);
    }

    private Task parseDeadline(String input) 
            throws BartholomewExceptions, DateTimeParseException {
        String remaining = input.substring(8).strip();
    
        if (remaining.isEmpty()) {
            throw new BartholomewExceptions.EmptyDescriptionException("deadline");
        }
        
        int sepIdx = remaining.indexOf(" /by ");
        if (sepIdx == -1) {
            throw new BartholomewExceptions.MissingDeadlineException();
        }
        
        String desc = remaining.substring(0, sepIdx).strip();
        String dueDate = remaining.substring(sepIdx + 5).strip();
        
        if (desc.isEmpty()) {
            throw new BartholomewExceptions.EmptyDescriptionException("deadline");
        }
        
        if (dueDate.isEmpty()) {
            throw new BartholomewExceptions.MissingDeadlineException();
        }
        
        return new Deadline(desc, dueDate);
    }

    private Task parseEvent(String input) 
            throws BartholomewExceptions, DateTimeParseException {
        String remaining = input.substring(5).strip();
    
        if (remaining.isEmpty()) {
            throw new BartholomewExceptions.EmptyDescriptionException("event");
        }

        int fromIdx = remaining.indexOf(" /from ");
        int toIdx = remaining.indexOf(" /to ");

        if (fromIdx == -1 || toIdx == -1 || fromIdx > toIdx) {
            throw new BartholomewExceptions.MissingEventTimeException();
        }
        
        String desc = remaining.substring(0, fromIdx).strip();
        if (desc.isEmpty()) {
            throw new BartholomewExceptions.EmptyDescriptionException("event");
        }
        
        String startTime = remaining.substring(fromIdx + 7, toIdx).strip();
        String endTime = remaining.substring(toIdx + 4).strip();
        
        if (startTime.isEmpty() || endTime.isEmpty()) {
            throw new BartholomewExceptions.MissingEventTimeException();
        }
        
        return new Event(desc, startTime, endTime);
    }
}