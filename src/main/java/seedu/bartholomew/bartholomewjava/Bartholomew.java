package seedu.bartholomew.bartholomewjava;

import java.time.format.DateTimeParseException;
import java.util.List;

import seedu.bartholomew.command.CommandType;
import seedu.bartholomew.exceptions.BartholomewExceptions;
import seedu.bartholomew.parser.Parser;
import seedu.bartholomew.storage.Storage;
import seedu.bartholomew.tasks.Task;
import seedu.bartholomew.tasks.TaskList;
import seedu.bartholomew.ui.Ui;

/**
 * Main class for the Bartholomew task management application.
 * This class coordinates the components of the application and handles the main program loop.
 */
public class Bartholomew {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;
    private boolean shouldExit = false;

    /**
     * Constructs a new Bartholomew application with the specified storage file path.
     * Initializes UI, task list, and parser components, and attempts to load task data from storage.
     *
     * @param filePath Path to the file used for task persistence
     */
    public Bartholomew(String filePath) {
        this.ui = new Ui();
        this.tasks = new TaskList();
        this.parser = new Parser();
        
        try {
            this.storage = new Storage(filePath);
            
            try {
                this.tasks = new TaskList(storage.load());
            } catch (BartholomewExceptions.FileReadException e) {
                
            }
        } catch (BartholomewExceptions.StorageException e) {
            
        }
    }

    /**
     * Saves the current task list to persistent storage.
     * If storage is not available, this operation is silently skipped.
     */
    private void saveToStorage() {
        if (storage != null) {
            try {
                storage.save(tasks.getTasks());
            } catch (BartholomewExceptions.FileWriteException e) {
                
            }
        }
    }

    /**
     * Main entry point for the Bartholomew application.
     * Creates a new Bartholomew instance and runs it.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // This will be handled by the GUI launcher
        System.out.println("Hello!");
    }

    /**
     * Generates a response for the user's chat message.
     * 
     * @param input The user's input text
     * @return A formatted response string
     */
    public String getResponse(String input) {
        try {
            CommandType commandType = parser.parseCommandType(input);
            
            if (commandType == null) {
                return ui.showError("I'm not sure what '" + input + "' means.");
            }

            switch (commandType) {
            case BYE:
                shouldExit = true;
                return ui.showGoodbye();
            case LIST:
                return ui.showTaskList(tasks.getTasks());
            case TODO:
                // Fallthrough
            case DEADLINE:
                // Fallthrough
            case EVENT:
                try {
                    Task task = parser.parseTask(input);
                    tasks.addTask(task);
                    saveToStorage();
                    return ui.showTaskAdded(task, tasks.size());
                } catch (BartholomewExceptions e) {
                    return ui.showError(e.getMessage());
                } catch (DateTimeParseException e) {
                    return ui.showDateFormatError();
                }
            case MARK:
                try {
                    int taskNo = parser.parseTaskNumber(input, CommandType.MARK, tasks.size());
                    Task markedTask = tasks.markTaskAsDone(taskNo);
                    saveToStorage();
                    return ui.showTaskMarked(markedTask);
                } catch (BartholomewExceptions e) {
                    return ui.showError(e.getMessage());
                }
            case UNMARK:
                try {
                    int taskNo = parser.parseTaskNumber(input, CommandType.UNMARK, tasks.size());
                    Task unmarkedTask = tasks.markTaskAsNotDone(taskNo);
                    saveToStorage();
                    return ui.showTaskUnmarked(unmarkedTask);
                } catch (BartholomewExceptions e) {
                    return ui.showError(e.getMessage());
                }
            case DELETE:
                try {
                    int taskNo = parser.parseTaskNumber(input, CommandType.DELETE, tasks.size());
                    Task deletedTask = tasks.deleteTask(taskNo);
                    saveToStorage();
                    return ui.showTaskDeleted(deletedTask, tasks.size());
                } catch (BartholomewExceptions e) {
                    return ui.showError(e.getMessage());
                }
            case FIND:
                try {
                    String searchTerm = parser.parseSearchTerm(input);
                    List<Task> matchingTasks = tasks.findTasks(searchTerm);
                    return ui.showSearchResults(matchingTasks, searchTerm);
                } catch (BartholomewExceptions e) {
                    return ui.showError(e.getMessage());
                }
            default:
                return ui.showError("I'm not sure what '" + input + "' means.");
            }
        } catch (Exception e) {
            return ui.showError("An error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Gets the welcome message to display when the application starts.
     * 
     * @return The welcome message
     */
    public String getWelcome() {
        StringBuilder welcome = new StringBuilder(ui.showWelcome());
        if (tasks.size() > 0) {
            welcome.append(ui.showTasksLoaded(tasks.size()));
        }
        return welcome.toString();
    }
    
    /**
     * Checks if the application should exit after processing a command.
     * 
     * @return true if the application should exit, false otherwise
     */
    public boolean shouldExit() {
        return shouldExit;
    }
}