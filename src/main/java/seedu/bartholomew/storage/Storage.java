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

public class Storage {
    private final File file;

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
                    throw new BartholomewExceptions.FileException(file.getPath(), 
                            "Could not create file");
                }
            } catch (IOException e) {
                throw new BartholomewExceptions.FileException(file.getPath(), e.getMessage());
            }
        }
    }

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

    private Task parseTaskFromLine(String line) throws BartholomewExceptions.TaskParseException {
        try {
            String[] parts = line.split(" \\| ");
            if (parts.length < 3) {
                throw new BartholomewExceptions.TaskParseException(line, 
                        "Invalid format. \nExpected at least 3 parts.");
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
                        throw new BartholomewExceptions.TaskParseException(line,
                                "Deadline task is missing due date.");
                    }
                    try {
                        task = new Deadline(description, parts[3]);
                    } catch (DateTimeParseException e) {
                        throw new BartholomewExceptions.TaskParseException(line,
                                "Invalid date format: " + e.getMessage());
                    }
                    break;
                case "E":
                    if (parts.length < 5) {
                        throw new BartholomewExceptions.TaskParseException(line,
                                "Event task is missing from/to times.");
                    }
                    try {
                        task = new Event(description, parts[3], parts[4]);
                    } catch (DateTimeParseException e) {
                        throw new BartholomewExceptions.TaskParseException(line,
                                "Invalid date format: " + e.getMessage());
                    }
                    break;
                default:
                    throw new BartholomewExceptions.TaskParseException(line,
                            "Unknown task type: " + type);
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
                    line = "E | " + (isDone ? "1" : "0") + " | " + desc + " | " + 
                           event.getFrom() + " | " + event.getTo();
                } else {
                    // Unknown task type
                    continue;
                }
                
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            throw new BartholomewExceptions.FileWriteException(file.getPath(), e.getMessage());
        }
    }
}
