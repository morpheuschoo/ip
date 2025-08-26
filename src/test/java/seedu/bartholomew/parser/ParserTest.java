package seedu.bartholomew.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.bartholomew.command.CommandType;
import seedu.bartholomew.exceptions.BartholomewExceptions;
import seedu.bartholomew.tasks.Deadline;
import seedu.bartholomew.tasks.Event;
import seedu.bartholomew.tasks.ToDo;
import seedu.bartholomew.tasks.Task;

public class ParserTest {

    private Parser parser;
    
    @BeforeEach
    public void setUp() {
        parser = new Parser();
    }

    @Test
    public void parseTask_validTodoTask_returnsTodoTask() throws BartholomewExceptions {
        String input = "todo read a book";
        Task task = parser.parseTask(input);
        
        assertEquals(ToDo.class, task.getClass());
        assertEquals("read a book", task.getDescription());
    }
    
    @Test
    public void parseTask_emptyTodoDescription_throwsEmptyDescriptionException() {
        String input = "todo ";
        
        assertThrows(BartholomewExceptions.EmptyDescriptionException.class, () -> {
            parser.parseTask(input);
        });
    }
    
    @Test
    public void parseTask_validDeadlineTask_returnsDeadlineTask() throws BartholomewExceptions {
        String input = "deadline submit report /by 12/12/2023 1800";
        Task task = parser.parseTask(input);
        
        assertEquals(Deadline.class, task.getClass());
        assertEquals("submit report", task.getDescription());
        assertEquals("12/12/2023 1800", ((Deadline) task).getDueDate());
    }
    
    @Test
    public void parseTask_missingDeadlineBy_throwsMissingDeadlineException() {
        String input = "deadline submit report";
        
        assertThrows(BartholomewExceptions.MissingDeadlineException.class, () -> {
            parser.parseTask(input);
        });
    }

    @Test
    public void parseTask_emptyDeadlineDescription_throwsEmptyDescriptionException() {
        String input = "deadline /by 10/09/2023 1400";

        assertThrows(BartholomewExceptions.EmptyDescriptionException.class, () -> {
            parser.parseTask(input);
        });
    }
    
    @Test
    public void parseTask_validEventTask_returnsEventTask() throws BartholomewExceptions {
        String input = "event team meeting /from 10/09/2023 1400 /to 10/09/2023 1600";
        Task task = parser.parseTask(input);
        
        assertEquals(Event.class, task.getClass());
        assertEquals("team meeting", task.getDescription());
        assertEquals("10/9/2023 1400", ((Event) task).getFrom());
        assertEquals("10/9/2023 1600", ((Event) task).getTo());
    }
    
    @Test
    public void parseTask_missingEventFromTo_throwsMissingEventTimeException() {
        String input = "event team meeting";
        
        assertThrows(BartholomewExceptions.MissingEventTimeException.class, () -> {
            parser.parseTask(input);
        });
    }
    
    @Test
    public void parseTask_missingEventTo_throwsMissingEventTimeException() {
        String input = "event team meeting /from 10/09/2023 1400";
        
        assertThrows(BartholomewExceptions.MissingEventTimeException.class, () -> {
            parser.parseTask(input);
        });
    }
    
    @Test
    public void parseTask_invalidOrder_throwsMissingEventTimeException() {
        String input = "event team meeting /to 10/09/2023 1600 /from 10/09/2023 1400";
        
        assertThrows(BartholomewExceptions.MissingEventTimeException.class, () -> {
            parser.parseTask(input);
        });
    }

    @Test
    public void parseTask_emptyEventDescription_throwsEmptyDescriptionException() {
        String input = "event /from 10/09/2023 1400 /to 10/09/2023 1600";

        assertThrows(BartholomewExceptions.EmptyDescriptionException.class, () -> {
            parser.parseTask(input);
        });
    }
    
    @Test
    public void parseTask_unknownTaskType_throwsUnknownCommandException() {
        String input = "unknown task";
        
        assertThrows(BartholomewExceptions.UnknownCommandException.class, () -> {
            parser.parseTask(input);
        });
    }
    
    @Test
    public void parseTaskNumber_validMarkTaskNumber_returnsTaskNumber() throws BartholomewExceptions.InvalidTaskNumberException {
        String input = "mark 2";
        int totalTasks = 3;
        
        int result = parser.parseTaskNumber(input, CommandType.MARK, totalTasks);
        
        assertEquals(2, result);
    }
    
    @Test
    public void parseTaskNumber_validUnmarkTaskNumber_returnsTaskNumber() throws BartholomewExceptions.InvalidTaskNumberException {
        String input = "mark 1";
        int totalTasks = 3;
        
        int result = parser.parseTaskNumber(input, CommandType.UNMARK, totalTasks);
        
        assertEquals(1, result);
    }
    
    @Test
    public void parseTaskNumber_validDeleteTaskNumber_returnsTaskNumber() throws BartholomewExceptions.InvalidTaskNumberException {
        String input = "delete 3";
        int totalTasks = 3;
        
        int result = parser.parseTaskNumber(input, CommandType.DELETE, totalTasks);
        
        assertEquals(3, result);
    }
    
    @Test
    public void parseTaskNumber_emptyTaskNumber_throwsInvalidTaskNumberException() {
        String input = "mark";
        int totalTasks = 3;
        
        assertThrows(BartholomewExceptions.InvalidTaskNumberException.class, () -> {
            parser.parseTaskNumber(input, CommandType.MARK, totalTasks);
        });
    }
    
    @Test
    public void parseTaskNumber_nonNumericTaskNumber_throwsInvalidTaskNumberException() {
        String input = "mark abc";
        int totalTasks = 3;
        
        assertThrows(BartholomewExceptions.InvalidTaskNumberException.class, () -> {
            parser.parseTaskNumber(input, CommandType.MARK, totalTasks);
        });
    }
    
    @Test
    public void parseTaskNumber_zeroTaskNumber_throwsInvalidTaskNumberException() {
        String input = "mark 0";
        int totalTasks = 3;
        
        assertThrows(BartholomewExceptions.InvalidTaskNumberException.class, () -> {
            parser.parseTaskNumber(input, CommandType.MARK, totalTasks);
        });
    }
    
    @Test
    public void parseTaskNumber_negativeTaskNumber_throwsInvalidTaskNumberException() {
        String input = "mark -1";
        int totalTasks = 3;
        
        assertThrows(BartholomewExceptions.InvalidTaskNumberException.class, () -> {
            parser.parseTaskNumber(input, CommandType.MARK, totalTasks);
        });
    }
    
    @Test
    public void parseTaskNumber_tooLargeTaskNumber_throwsInvalidTaskNumberException() {
        String input = "mark 4";
        int totalTasks = 3;
        
        assertThrows(BartholomewExceptions.InvalidTaskNumberException.class, () -> {
            parser.parseTaskNumber(input, CommandType.MARK, totalTasks);
        });
    }
    
    @Test
    public void parseTaskNumber_invalidCommand_throwsIllegalArgumentException() {
        String input = "list";
        int totalTasks = 3;
        
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parseTaskNumber(input, CommandType.LIST, totalTasks);
        });
    }
}