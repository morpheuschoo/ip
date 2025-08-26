package seedu.bartholomew.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.bartholomew.exceptions.BartholomewExceptions;

public class TaskListTest {
    
    private TaskList emptyTaskList;
    private TaskList populatedTaskList;
    private Task todoTask;
    private Task deadlineTask;
    private Task eventTask;
    
    @BeforeEach
    public void setUp() {
        emptyTaskList = new TaskList();
        
        todoTask = new ToDo("Read a book");
        deadlineTask = new Deadline("Submit report", "15/09/2023 1800");
        eventTask = new Event("Team meeting", "20/09/2023 1400", "20/09/2023 1600");
        
        List<Task> tasks = new ArrayList<>();
        tasks.add(todoTask);
        tasks.add(deadlineTask);
        tasks.add(eventTask);
        populatedTaskList = new TaskList(tasks);
    }
    
    @Test
    public void constructor_noArguments_createsEmptyList() {
        TaskList taskList = new TaskList();
        assertEquals(0, taskList.size());
        assertTrue(taskList.isEmpty());
    }
    
    @Test
    public void constructor_withTaskList_copiesTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(todoTask);
        tasks.add(deadlineTask);
        
        TaskList taskList = new TaskList(tasks);
        assertEquals(2, taskList.size());
        assertEquals(todoTask, taskList.getTasks().get(0));
        assertEquals(deadlineTask, taskList.getTasks().get(1));
    }
    
    @Test
    public void addTask_toEmptyList_taskAdded() {
        emptyTaskList.addTask(todoTask);
        assertEquals(1, emptyTaskList.size());
        assertEquals(todoTask, emptyTaskList.getTasks().get(0));
    }
    
    @Test
    public void addTask_toPopulatedList_taskAdded() {
        Task newTask = new ToDo("New task");
        populatedTaskList.addTask(newTask);
        
        assertEquals(4, populatedTaskList.size());
        assertEquals(newTask, populatedTaskList.getTasks().get(3));
    }
    
    @Test
    public void deleteTask_validIndex_returnsDeletedTask() throws BartholomewExceptions.InvalidTaskNumberException {
        Task deleted = populatedTaskList.deleteTask(2);
        assertEquals(deadlineTask, deleted);
        assertEquals(2, populatedTaskList.size());
        assertEquals(eventTask, populatedTaskList.getTasks().get(1));
    }
    
    @Test
    public void deleteTask_invalidIndexZero_throwsException() {
        assertThrows(BartholomewExceptions.InvalidTaskNumberException.class, () -> {
            populatedTaskList.deleteTask(0);
        });
    }
    
    @Test
    public void deleteTask_invalidIndexNegative_throwsException() {
        assertThrows(BartholomewExceptions.InvalidTaskNumberException.class, () -> {
            populatedTaskList.deleteTask(-1);
        });
    }
    
    @Test
    public void deleteTask_invalidIndexTooLarge_throwsException() {
        assertThrows(BartholomewExceptions.InvalidTaskNumberException.class, () -> {
            populatedTaskList.deleteTask(4);
        });
    }
    
    @Test
    public void markTaskAsDone_validIndex_taskMarked() throws BartholomewExceptions.InvalidTaskNumberException {
        Task markedTask = populatedTaskList.markTaskAsDone(1);
        
        assertTrue(markedTask.isDone());
        assertTrue(populatedTaskList.getTask(1).isDone());
        assertEquals(todoTask, markedTask);
    }
    
    @Test
    public void markTaskAsDone_invalidIndex_throwsException() {
        assertThrows(BartholomewExceptions.InvalidTaskNumberException.class, () -> {
            populatedTaskList.markTaskAsDone(4);
        });
    }
    
    @Test
    public void markTaskAsNotDone_validIndex_taskUnmarked() throws BartholomewExceptions.InvalidTaskNumberException {
        populatedTaskList.markTaskAsDone(2);
        assertTrue(populatedTaskList.getTask(2).isDone());
        
        Task unmarkedTask = populatedTaskList.markTaskAsNotDone(2);
        
        assertFalse(unmarkedTask.isDone());
        assertFalse(populatedTaskList.getTask(2).isDone());
        assertEquals(deadlineTask, unmarkedTask);
    }
    
    @Test
    public void markTaskAsNotDone_invalidIndex_throwsException() {
        assertThrows(BartholomewExceptions.InvalidTaskNumberException.class, () -> {
            populatedTaskList.markTaskAsNotDone(0);
        });
    }
    
    @Test
    public void getTask_validIndex_returnsTask() throws BartholomewExceptions.InvalidTaskNumberException {
        assertEquals(todoTask, populatedTaskList.getTask(1));
        assertEquals(deadlineTask, populatedTaskList.getTask(2));
        assertEquals(eventTask, populatedTaskList.getTask(3));
    }
    
    @Test
    public void getTask_invalidIndex_throwsException() {
        assertThrows(BartholomewExceptions.InvalidTaskNumberException.class, () -> {
            populatedTaskList.getTask(4);
        });
    }
    
    @Test
    public void getTasks_returnsDefensiveCopy() {
        List<Task> tasks = populatedTaskList.getTasks();
        tasks.add(new ToDo("New task"));
        
        assertEquals(3, populatedTaskList.size());
    }
    
    @Test
    public void size_emptyList_returnsZero() {
        assertEquals(0, emptyTaskList.size());
    }
    
    @Test
    public void size_populatedList_returnsCorrectSize() {
        assertEquals(3, populatedTaskList.size());
    }
    
    @Test
    public void isEmpty_emptyList_returnsTrue() {
        assertTrue(emptyTaskList.isEmpty());
    }
    
    @Test
    public void isEmpty_populatedList_returnsFalse() {
        assertFalse(populatedTaskList.isEmpty());
    }
}