package seedu.bartholomew;

import java.time.format.DateTimeParseException;

import seedu.bartholomew.command.CommandType;
import seedu.bartholomew.exceptions.BartholomewExceptions;
import seedu.bartholomew.parser.Parser;
import seedu.bartholomew.storage.Storage;
import seedu.bartholomew.tasks.Task;
import seedu.bartholomew.tasks.TaskList;
import seedu.bartholomew.ui.Ui;

public class Bartholomew {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    public Bartholomew(String filePath) {
        this.ui = new Ui();
        this.tasks = new TaskList();
        this.parser = new Parser();
        
        try {
            this.storage = new Storage(filePath);
            
            try {
                this.tasks = new TaskList(storage.load());
                ui.showTasksLoaded(tasks.size());
            } catch (BartholomewExceptions.FileReadException e) {
                ui.showError("Could not load tasks: " + e.getMessage());
            }
        } catch (BartholomewExceptions.StorageException e) {
            ui.showError("Storage setup failed: " + e.getMessage() 
                    + "\nWill continue without saving tasks.");
        }
    }

    public void run() {
        ui.showWelcome();
        
        boolean isRunning = true;
        while (isRunning) {
            try {
                String input = ui.readCommand();
                CommandType commandType = parser.parseCommandType(input);
                
                if (commandType == null) {
                    try {
                        throw new BartholomewExceptions.UnknownCommandException(input);
                    } catch (BartholomewExceptions.UnknownCommandException e) {
                        ui.showError(e.getMessage());
                    }
                    continue;
                }

                switch (commandType) {
                case BYE:
                    isRunning = false;
                    break;
                case LIST:
                    ui.showTaskList(tasks.getTasks());
                    break;
                case TODO:
                    // Fallthrough
                case DEADLINE:
                    // Fallthrough
                case EVENT:
                    try {
                        Task task = parser.parseTask(input);
                        tasks.addTask(task);
                        saveToStorage();
                        ui.showTaskAdded(task, tasks.size());
                    } catch (BartholomewExceptions e) {
                        ui.showError(e.getMessage());
                    } catch (DateTimeParseException e) {
                        ui.showDateFormatError();
                    }
                    break;
                case MARK:
                    try {
                        int taskNo = parser.parseTaskNumber(input, CommandType.MARK, tasks.size());
                        Task markedTask = tasks.markTaskAsDone(taskNo);
                        saveToStorage();
                        ui.showTaskMarked(markedTask);
                    } catch (BartholomewExceptions e) {
                        ui.showError(e.getMessage());
                    }
                    break;
                case UNMARK:
                    try {
                        int taskNo = parser.parseTaskNumber(input, CommandType.UNMARK, tasks.size());
                        Task unmarkedTask = tasks.markTaskAsNotDone(taskNo);
                        saveToStorage();
                        ui.showTaskUnmarked(unmarkedTask);
                    } catch (BartholomewExceptions e) {
                        ui.showError(e.getMessage());
                    }
                    break;
                case DELETE:
                    try {
                        int taskNo = parser.parseTaskNumber(input, CommandType.DELETE, tasks.size());
                        Task deletedTask = tasks.deleteTask(taskNo);
                        saveToStorage();
                        ui.showTaskDeleted(deletedTask, tasks.size());
                    } catch (BartholomewExceptions e) {
                        ui.showError(e.getMessage());
                    }
                    break;
                default:
                    try {
                        throw new BartholomewExceptions.UnknownCommandException(input);
                    } catch (BartholomewExceptions.UnknownCommandException e) {
                        ui.showError(e.getMessage());
                    }
                    break;
                }
            } catch (Exception e) {
                ui.showError("An error occurred: " + e.getMessage() + "\n");
            }
        }
        
        ui.showGoodbye();
    }

    private void saveToStorage() {
        if (storage != null) {
            try {
                storage.save(tasks.getTasks());
            } catch (BartholomewExceptions.FileWriteException e) {
                ui.showError("Error saving tasks: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Bartholomew("data/bartholomew.txt").run();
    }
}