package exceptions;

public class BartholomewExceptions {
    
    // Exception thrown when a task description is empty or missing.
    public static class EmptyDescriptionException extends Exception {
        public EmptyDescriptionException(String taskType) {
            super("The description of a " + taskType + " cannot be empty.");
        }
    }
    
    // Exception thrown when a deadline is missing the /by component.
    public static class MissingDeadlineException extends Exception {
        public MissingDeadlineException() {
            super("The deadline format is incorrect. Please use: deadline <description> /by <date>");
        }
    }
    
    // Exception thrown when an event is missing the /from or /to component.
    public static class MissingEventTimeException extends Exception {
        public MissingEventTimeException() {
            super("The event format is incorrect. Please use: event <description> /from <start> /to <end>");
        }
    }
    
    
    // Exception thrown when a task number is invalid (out of range).
    public static class InvalidTaskNumberException extends Exception {
        public InvalidTaskNumberException(int taskNumber) {
            super("Task " + taskNumber + " does not exist in the list.");
        }

        public InvalidTaskNumberException(String invalidInput) {
            super("\"" + invalidInput + "\" is not a valid task number.");
        }
    }
    
    // Exception thrown when an unknown command is entered.
    public static class UnknownCommandException extends Exception {
        public UnknownCommandException(String command) {
            super("I'm not sure what '" + command + "' means.");
        }
    }
}