import java.util.ArrayList;
import java.util.Scanner;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import exceptions.BartholomewExceptions;

import utils.CommandType;

public class Bartholomew {
    private final String MESSAGE_DIVIDER = "____________________________________________________________\n";
    
    private ArrayList<Task> tasks;

    public Bartholomew() {
        this.tasks = new ArrayList<>();
    }

    private void printStart() {
        String printResult = MESSAGE_DIVIDER
                            + "Hello! I'm Bartholomew\n"
                            + "What can I do for you?\n"
                            + MESSAGE_DIVIDER;
        System.out.println(printResult);
    }

    private void eventLoop() {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (true) {
            input = scanner.nextLine();
            CommandType commandType = CommandType.fromString(input);
            
            if (commandType == null) {
                addTask(input);
                continue;
            }

            switch (commandType) {
            case BYE:
                scanner.close();
                return;
            case LIST:
                printList();
                break;
            case MARK:
                try {
                    int taskNo = parseTaskNo(input, CommandType.MARK);
                    markTask(taskNo);
                } catch (BartholomewExceptions.InvalidTaskNumberException e) {
                    System.out.println(MESSAGE_DIVIDER + e.getMessage() + "\n" + MESSAGE_DIVIDER);
                }
                break;
            case UNMARK:
                try {
                    int taskNo = parseTaskNo(input, CommandType.UNMARK);
                    unmarkTask(taskNo);
                } catch (BartholomewExceptions.InvalidTaskNumberException e) {
                    System.out.println(MESSAGE_DIVIDER + e.getMessage() + "\n" + MESSAGE_DIVIDER);
                }
                break;
            case DELETE:
                try {
                    int taskNo = parseTaskNo(input, CommandType.DELETE);
                    deleteTask(taskNo);
                } catch (BartholomewExceptions.InvalidTaskNumberException e) {
                    System.out.println(MESSAGE_DIVIDER + e.getMessage() + "\n" + MESSAGE_DIVIDER);
                }
                break;
            default:
                addTask(input);
                break;
            }
        }
    }

    private void printList() {
        String resultString = MESSAGE_DIVIDER;
        for (int i = 0; i < tasks.size(); i++) {
            resultString += Integer.toString(i + 1) + "." + tasks.get(i).toString() + "\n";
        }
        resultString += MESSAGE_DIVIDER;
        System.out.println(resultString);
    }

    private void addTask(String input) {
        try {
            if (input.startsWith("todo")) {
                String desc = input.substring(4).strip();
                if (desc.isEmpty()) {
                    throw new BartholomewExceptions.EmptyDescriptionException("todo");
                }
                
                tasks.add(new ToDo(desc));
            } else if (input.startsWith("deadline")) {
                String remaining = input.substring(9).strip();
                int sepIdx = remaining.indexOf(" /by ");

                if (sepIdx == -1) {
                    throw new BartholomewExceptions.MissingDeadlineException();
                }
                
                String desc = remaining.substring(0, sepIdx).strip();
                if (desc.isEmpty()) {
                    throw new BartholomewExceptions.EmptyDescriptionException("deadline");
                }

                tasks.add(new Deadline(
                    desc,
                    remaining.substring(sepIdx + 5).strip()
                ));
            } else if (input.startsWith("event")) {
                String remaining = input.substring(6).strip();
                int fromIdx = remaining.indexOf(" /from ");
                int toIdx = remaining.indexOf(" /to ");

                if (fromIdx == -1 || toIdx == -1) {
                    throw new BartholomewExceptions.MissingEventTimeException();
                }

                String desc = remaining.substring(0, fromIdx).strip();
                if (desc.isEmpty()) {
                    throw new BartholomewExceptions.EmptyDescriptionException("event");
                }

                if (fromIdx > toIdx) {
                    throw new BartholomewExceptions.MissingEventTimeException();
                }

                String startTime = remaining.substring(fromIdx + 7, toIdx).strip();
                String endTime = remaining.substring(toIdx + 4).strip();

                if (startTime.isEmpty() || endTime.isEmpty()) {
                    throw new BartholomewExceptions.MissingEventTimeException();
                }


                tasks.add(new Event(
                    desc,
                    startTime,
                    endTime
                ));            
            } else {
                throw new BartholomewExceptions.UnknownCommandException(input);
            }

            String printResult = MESSAGE_DIVIDER
                            + "Got it. I've added this task:\n"
                            + "  " +  tasks.get(tasks.size() - 1).toString() + "\n"
                            + "Now you have " + tasks.size() + " tasks in the list.\n"
                            + MESSAGE_DIVIDER;

            System.out.println(printResult);
        } catch (
            BartholomewExceptions.EmptyDescriptionException |
            BartholomewExceptions.MissingEventTimeException |
            BartholomewExceptions.MissingDeadlineException |
            BartholomewExceptions.UnknownCommandException e
        ) {
            System.out.println(MESSAGE_DIVIDER + e.getMessage() + "\n" + MESSAGE_DIVIDER);
        }
    }

    private void deleteTask(int taskNo) {
        String printResult = MESSAGE_DIVIDER
                           + "Noted. I've removed this task:\n"
                           + "  " + tasks.get(taskNo - 1).toString() + "\n"
                           + "Now you have " + tasks.size() + " in the list.\n"
                           + MESSAGE_DIVIDER;
        
        tasks.remove(taskNo - 1);
        System.out.println(printResult);
    }

    private void markTask(int taskNo) {
        tasks.get(taskNo - 1).markTask();

        String printResult = MESSAGE_DIVIDER
                            + "Nice! I've marked this task as done:\n"
                            + "  " + tasks.get(taskNo - 1).toString() + "\n"
                            + MESSAGE_DIVIDER;
        
        System.out.println(printResult);
    }

    private void unmarkTask(int taskNo) {
        tasks.get(taskNo - 1).unmarkTask();

        String printResult = MESSAGE_DIVIDER
                            + "OK, I've marked this task as not done yet:\n"
                            + "  " + tasks.get(taskNo - 1).toString() + "\n"
                            + MESSAGE_DIVIDER;
        
        System.out.println(printResult);
    }

    private void printBye() {
        String printResult = MESSAGE_DIVIDER
                            + "Bye. Hope to see you again soon!\n"
                            + MESSAGE_DIVIDER;
        System.out.println(printResult);
    }

    private int parseTaskNo(String input, CommandType command) 
            throws BartholomewExceptions.InvalidTaskNumberException {
        int prefixLen = 0;
        switch (command) {
        case MARK:
            prefixLen = 4;
            break;
        case UNMARK:
            // Fallthrough
        case DELETE:
            prefixLen = 6;
            break;
        default:
            throw new IllegalArgumentException(command.name() + " cannot be used with parseTaskNo");
        }

        try {
            String numberPart = input.substring(prefixLen).trim();

            if (numberPart.isEmpty()) {
                throw new BartholomewExceptions.InvalidTaskNumberException("");
            }

            int taskNo = Integer.parseInt(numberPart);

            if (taskNo <= 0 || taskNo > tasks.size()) {
                throw new BartholomewExceptions.InvalidTaskNumberException(taskNo);
            }

            return taskNo;
        } catch (NumberFormatException e) {
            String invalidNumber = input.substring(prefixLen).trim();
            throw new BartholomewExceptions.InvalidTaskNumberException(invalidNumber);
        }
    }

    public static void main(String[] args) {
        Bartholomew bot = new Bartholomew();
        bot.printStart();
        bot.eventLoop();
        bot.printBye();
    }
}
