package bartholomew.exceptions;

public class BartholomewExceptions extends Exception {
    
    public BartholomewExceptions(String message) {
        super(message);
    }
    
    /**
     * Exception thrown when a task description is empty or missing.
     */
    public static class EmptyDescriptionException extends BartholomewExceptions {
        public EmptyDescriptionException(String taskType) {
            super("The description of a " + taskType + " cannot be empty.\n");
        }
    }
    
    /**
     * Exception thrown when a deadline is missing the /by component.
     */
    public static class MissingDeadlineException extends BartholomewExceptions {
        public MissingDeadlineException() {
            super("The deadline format is incorrect.\nPlease use: deadline <description> /by <date>\n");
        }
    }
    
    /**
     * Exception thrown when an event is missing the /from or /to component.
     */
    public static class MissingEventTimeException extends BartholomewExceptions {
        public MissingEventTimeException() {
            super("The event format is incorrect.\nPlease use: event <description> /from <start> /to <end>\n");
        }
    }
    
    /**
     * Exception thrown when a task number is invalid (out of range).
     */
    public static class InvalidTaskNumberException extends BartholomewExceptions {
        public InvalidTaskNumberException(int taskNumber) {
            super("Task " + taskNumber + " does not exist in the list.\n");
        }

        public InvalidTaskNumberException(String invalidInput) {
            super("\"" + invalidInput + "\" is not a valid task number.\n");
        }
    }
    
    /**
     * Exception thrown when an unknown command is entered.
     */
    public static class UnknownCommandException extends BartholomewExceptions {
        public UnknownCommandException(String command) {
            super("I'm not sure what '" + command + "' means.\n");
        }
    }

    /**
     * Base class for all storage-related exceptions.
     */
    public static class StorageException extends BartholomewExceptions {
        public StorageException(String message) {
            super(message);
        }
    }
    
    /**
     * Exception thrown when there's an issue with file operations.
     */
    public static class FileException extends StorageException {
        public FileException(String filePath, String message) {
            super("File error with '" + filePath + "':\n" + message + "\n");
        }
        
        public FileException(String filePath) {
            super("Could not access file: " + filePath + "\n");
        }
    }
    
    /**
     * Exception thrown when there's an issue with directory operations.
     */
    public static class DirectoryException extends StorageException {
        public DirectoryException(String dirPath, String message) {
            super("Directory error with '" + dirPath + "':\n" + message + "\n");
        }
        
        public DirectoryException(String dirPath) {
            super("Could not access directory: " + dirPath + "\n");
        }
    }
    
    /**
     * Exception thrown when there's an issue reading from a file.
     */
    public static class FileReadException extends FileException {
        public FileReadException(String filePath, String message) {
            super(filePath, "Failed to read.\n" + message);
        }
        
        public FileReadException(String filePath) {
            super(filePath, "Failed to read file.");
        }
    }
    
    /**
     * Exception thrown when there's an issue writing to a file.
     */
    public static class FileWriteException extends FileException {
        public FileWriteException(String filePath, String message) {
            super(filePath, "Failed to write.\n" + message);
        }
        
        public FileWriteException(String filePath) {
            super(filePath, "Failed to write to file.");
        }
    }

    /**
     * Exception thrown when there's an issue parsing a task from storage.
     */
    public static class TaskParseException extends StorageException {
        public TaskParseException(String line, String reason) {
            super("Could not parse task from line: '" + line + "'.\n" +
                  "Reason: " + reason + "\n");
        }
        
        public TaskParseException(String line) {
            super("Could not parse task from line: '" + line + "'\n");
        }
    }
}