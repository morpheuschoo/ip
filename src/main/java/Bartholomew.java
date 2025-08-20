import java.util.Scanner;

public class Bartholomew {
    public static void main(String[] args) {
        Bartholomew bot = new Bartholomew();
        bot.printStart();
        bot.eventLoop();
        bot.printBye();
    }
    
    private final String divider = "____________________________________________________________\n";
    
    private Task[] tasks;
    private int taskCount;

    public Bartholomew() {
        this.taskCount = 0;
        this.tasks = new Task[100];
    }

    private void printStart() {
        String printResult = divider
                            + "Hello! I'm Bartholomew\n"
                            + "What can I do for you?\n"
                            + divider;
        System.out.println(printResult);
    }

    private void eventLoop() {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (true) {
            input = scanner.nextLine();
            
            if (input.equals("bye")) {
                scanner.close();
                return;
            }

            if (input.equals("list")) {
                printList();
                continue;
            }

            if (input.startsWith("mark")) {
                try {
                    int taskNo = parseTaskNo(input, true);
                    markTask(taskNo);
                } catch (BartholomewExceptions.InvalidTaskNumberException e) {
                    System.out.println(divider + e.getMessage() + "\n" + divider);
                }
                continue;
            }

            if (input.startsWith("unmark")) {
                try {
                    int taskNo = parseTaskNo(input, false);
                    unmarkTask(taskNo);
                } catch (BartholomewExceptions.InvalidTaskNumberException e) {
                    System.out.println(divider + e.getMessage() + "\n" + divider);
                }
                continue;
            }

            addTask(input);
        }
    }

    private void printList() {
        String resultString = divider;
        for (int i = 0; i < this.taskCount; i++) {
            resultString += Integer.toString(i + 1) + "." + tasks[i].toString() + "\n";
        }
        resultString += divider;
        System.out.println(resultString);
    }

    private void addTask(String input) {
        try {
            if (input.startsWith("todo")) {
                String desc = input.substring(4).strip();
                if (desc.isEmpty()) {
                    throw new BartholomewExceptions.EmptyDescriptionException("todo");
                }
                
                tasks[taskCount] = new ToDo(desc);
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

                tasks[taskCount] = new Deadline(
                    desc,
                    remaining.substring(sepIdx + 5).strip()
                );
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


                tasks[taskCount] = new Event(
                    desc,
                    startTime,
                    endTime
                );            
            } else {
                throw new BartholomewExceptions.UnknownCommandException(input);
            }

            String printResult = divider
                            + "Got it. I've added this task:\n"
                            + "  " +  tasks[taskCount].toString() + "\n"
                            + "Now you have " + (taskCount + 1) + " tasks in the list.\n"
                            + divider;

            taskCount++;

            System.out.println(printResult);
        } catch (
            BartholomewExceptions.EmptyDescriptionException |
            BartholomewExceptions.MissingEventTimeException |
            BartholomewExceptions.MissingDeadlineException |
            BartholomewExceptions.UnknownCommandException e
        ) {
            System.out.println(divider + e.getMessage() + "\n" + divider);
        }
    }

    private void markTask(int taskNo) {
        tasks[taskNo - 1].markTask();

        String printResult = divider
                            + "Nice! I've marked this task as done:\n"
                            + "  " + tasks[taskNo - 1].toString() + "\n"
                            + divider;
        
        System.out.println(printResult);
    }

    private void unmarkTask(int taskNo) {
        tasks[taskNo - 1].unmarkTask();

        String printResult = divider
                            + "OK, I've marked this task as not done yet:\n"
                            + "  " + tasks[taskNo - 1].toString() + "\n"
                            + divider;
        
        System.out.println(printResult);
    }

    private void printBye() {
        String printResult = divider
                            + "Bye. Hope to see you again soon!\n"
                            + divider;
        System.out.println(printResult);
    }

    private int parseTaskNo(String input, boolean isMark) 
        throws BartholomewExceptions.InvalidTaskNumberException {
        int prefixLen = isMark ? 4 : 6;
        try {
            String numberPart = input.substring(prefixLen).trim();

            if (numberPart.isEmpty()) {
                throw new BartholomewExceptions.InvalidTaskNumberException("");
            }

            int taskNo = Integer.parseInt(numberPart);

            if (taskNo <= 0 || taskNo > taskCount) {
                throw new BartholomewExceptions.InvalidTaskNumberException(taskNo);
            }

            return taskNo;
        } catch (NumberFormatException e) {
            String invalidNumber = input.substring(prefixLen).trim();
            throw new BartholomewExceptions.InvalidTaskNumberException(invalidNumber);
        }
    }
}
