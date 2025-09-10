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
                // Log error or show notification that existing tasks couldn't be loaded
                System.err.println("Could not load tasks: " + e.getMessage());
            }
        } catch (BartholomewExceptions.StorageException e) {
            // Log error or show notification that storage couldn't be initialized
            System.err.println("Could not initialize storage: " + e.getMessage());
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
                // Log error or notify user that tasks couldn't be saved
                System.err.println("Could not save tasks: " + e.getMessage());
            }
        }
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
                return handleByeCommand();
            case LIST:
                return handleListCommand();
            case TODO:
                // Passthrough
            case DEADLINE:
                // passthrough
            case EVENT:
                return handleAddTaskCommand(input);
            case MARK:
                return handleMarkCommand(input);
            case UNMARK:
                return handleUnmarkCommand(input);
            case DELETE:
                return handleDeleteCommand(input);
            case FIND:
                return handleFindCommand(input);
            default:
                return ui.showError("I'm not sure what '" + input + "' means.");
            }
        } catch (BartholomewExceptions e) {
            return ui.showError(e.getMessage());
        } catch (DateTimeParseException e) {
            return ui.showDateFormatError();
        } catch (Exception e) {
            // Only catch truly unexpected exceptions as a last resort
            return ui.showError("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Handle the bye command.
     * 
     * @return A goodbye message
     */
    private String handleByeCommand() {
        shouldExit = true;
        return ui.showGoodbye();
    }
    
    /**
     * Handle the list command.
     * 
     * @return A formatted list of tasks
     */
    private String handleListCommand() {
        return ui.showTaskList(tasks.getTasks());
    }
    
    /**
     * Handle commands that add a task.
     * 
     * @param input The user input
     * @return A message confirming the task addition
     * @throws BartholomewExceptions If there is an error parsing the task
     * @throws DateTimeParseException If there is an error parsing the date
     */
    private String handleAddTaskCommand(String input) throws BartholomewExceptions, DateTimeParseException {
        Task task = parser.parseTask(input);
        tasks.addTask(task);
        saveToStorage();
        return ui.showTaskAdded(task, tasks.size());
    }
    
    /**
     * Handle the mark command.
     * 
     * @param input The user input
     * @return A message confirming the task was marked
     * @throws BartholomewExceptions If there is an error with the task number
     */
    private String handleMarkCommand(String input) throws BartholomewExceptions {
        int taskNo = parser.parseTaskNumber(input, CommandType.MARK, tasks.size());
        Task markedTask = tasks.markTaskAsDone(taskNo);
        saveToStorage();
        return ui.showTaskMarked(markedTask);
    }
    
    /**
     * Handle the unmark command.
     * 
     * @param input The user input
     * @return A message confirming the task was unmarked
     * @throws BartholomewExceptions If there is an error with the task number
     */
    private String handleUnmarkCommand(String input) throws BartholomewExceptions {
        int taskNo = parser.parseTaskNumber(input, CommandType.UNMARK, tasks.size());
        Task unmarkedTask = tasks.markTaskAsNotDone(taskNo);
        saveToStorage();
        return ui.showTaskUnmarked(unmarkedTask);
    }
    
    /**
     * Handle the delete command.
     * 
     * @param input The user input
     * @return A message confirming the task was deleted
     * @throws BartholomewExceptions If there is an error with the task number
     */
    private String handleDeleteCommand(String input) throws BartholomewExceptions {
        int taskNo = parser.parseTaskNumber(input, CommandType.DELETE, tasks.size());
        Task deletedTask = tasks.deleteTask(taskNo);
        saveToStorage();
        return ui.showTaskDeleted(deletedTask, tasks.size());
    }
    
    /**
     * Handle the find command.
     * 
     * @param input The user input
     * @return A message showing the search results
     * @throws BartholomewExceptions If there is an error parsing the search term
     */
    private String handleFindCommand(String input) throws BartholomewExceptions {
        String searchTerm = parser.parseSearchTerm(input);
        List<Task> matchingTasks = tasks.findTasks(searchTerm);
        return ui.showSearchResults(matchingTasks, searchTerm);
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