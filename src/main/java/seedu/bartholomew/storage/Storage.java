package seedu.bartholomew.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import seedu.bartholomew.tasks.Deadline;
import seedu.bartholomew.tasks.Event;
import seedu.bartholomew.tasks.Task;
import seedu.bartholomew.tasks.ToDo;

import seedu.bartholomew.exceptions.BartholomewExceptions;

/**
 * Manages persistent storage of tasks in the Bartholomew task management system.
 * Handles reading tasks from and writing tasks to a file on disk.
 */
public class Storage {
    private final File file;

    /**
     * Creates a new Storage object to manage task persistence at the specified file path.
     * Creates necessary directories and the file if they don't already exist.
     *
     * @param filePath The path where tasks should be saved
     * @throws BartholomewExceptions.FileException If the file cannot be created
     * @throws BartholomewExceptions.DirectoryException If the directory structure cannot be created
     */
    public Storage(String filePath) 
            throws BartholomewExceptions.FileException, 
            BartholomewExceptions.DirectoryException {
        this.file = new File(filePath);
        
        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new BartholomewExceptions.DirectoryException(directory.getPath(), 
                        "Could not create directory structure");
            }
        }

        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (!created) {
                    throw new BartholomewExceptions.FileException(file.getPath(), "Could not create file");
                }
            } catch (IOException e) {
                throw new BartholomewExceptions.FileException(file.getPath(), e.getMessage());
            }
        }
    }

    /**
     * Loads tasks from the storage file.
     * Each line in the file represents one task.
     * Lines that cannot be parsed are skipped with a warning message.
     *
     * @return A list of tasks loaded from the file
     * @throws BartholomewExceptions.FileReadException If an error occurs while reading the file
     */
    public List<Task> load() throws BartholomewExceptions.FileReadException {
        List<Task> tasks = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    Task task = parseTaskFromLine(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (BartholomewExceptions.TaskParseException e) {
                    System.out.println("Warning at line " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new BartholomewExceptions.FileReadException(file.getPath(), e.getMessage());
        }
        
        return tasks;
    }

    /**
     * Parses a single line from the storage file into a Task object.
     * The format is: "TYPE | IS_DONE | DESCRIPTION [| ADDITIONAL_DATA...]"
     * Where TYPE is "T" for ToDo, "D" for Deadline, or "E" for Event
     * IS_DONE is "1" for completed tasks or "0" for incomplete tasks
     * DESCRIPTION is the task description
     * ADDITIONAL_DATA contains due dates for Deadline tasks or start/end times for Event tasks
     *
     * @param line The line to parse
     * @return A Task object representing the parsed line
     * @throws BartholomewExceptions.TaskParseException If the line cannot be parsed into a valid task
     */
    private Task parseTaskFromLine(String line) throws BartholomewExceptions.TaskParseException {
        try {
            String[] parts = line.split(" \\| ");
            if (parts.length < 3) {
                throw new BartholomewExceptions.TaskParseException(line, "Invalid format. \nExpected at least 3 parts.");
            }
            
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];
            
            Task task;
            
            switch (type) {
                case "T":
                    task = new ToDo(description);
                    break;
                case "D":
                    if (parts.length < 4) {
                        throw new BartholomewExceptions.TaskParseException(line, "Deadline task is missing due date.");
                    }
                    try {
                        task = new Deadline(description, parts[3]);
                    } catch (DateTimeParseException e) {
                        throw new BartholomewExceptions.TaskParseException(line, "Invalid date format: " + e.getMessage());
                    }
                    break;
                case "E":
                    if (parts.length < 5) {
                        throw new BartholomewExceptions.TaskParseException(line, "Event task is missing from/to times.");
                    }
                    try {
                        task = new Event(description, parts[3], parts[4]);
                    } catch (DateTimeParseException e) {
                        throw new BartholomewExceptions.TaskParseException(line, "Invalid date format: " + e.getMessage());
                    }
                    break;
                default:
                    throw new BartholomewExceptions.TaskParseException(line, "Unknown task type: " + type);
            }
            
            if (isDone) {
                task.markTask();
            }
            
            return task;
        } catch (Exception e) {
            if (e instanceof BartholomewExceptions.TaskParseException) {
                throw e;
            } else {
                throw new BartholomewExceptions.TaskParseException(line, e.getMessage());
            }
        }
    }

    /**
     * Saves the provided list of tasks to the storage file.
     * Each task is serialized into a single line in the file.
     *
     * @param tasks The list of tasks to save
     * @throws BartholomewExceptions.FileWriteException If an error occurs while writing to the file
     */
    public void save(List<Task> tasks) throws BartholomewExceptions.FileWriteException {
        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : tasks) {
                String line;
                String desc = task.getDescription();
                boolean isDone = task.isDone();
                
                if (task instanceof ToDo) {
                    line = "T | " + (isDone ? "1" : "0") + " | " + desc;
                } else if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    line = "D | " + (isDone ? "1" : "0") + " | " + desc + " | " + deadline.getDueDate();
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    line = "E | " + (isDone ? "1" : "0") + " | " + desc + " | " + event.getFrom() + " | " + event.getTo();
                } else {
                    continue;
                }
                
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            throw new BartholomewExceptions.FileWriteException(file.getPath(), e.getMessage());
        }
    }
}