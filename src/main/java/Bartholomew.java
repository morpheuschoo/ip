import java.util.Scanner;

public class Bartholomew {
    public static void main(String[] args) {
        Bartholomew bot = new Bartholomew();
        bot.printStart();
        bot.eventLoop();
        bot.printBye();
    }

    private class Task {
        private String desc;
        private boolean completed;

        public Task(String desc) {
            this.desc = desc;
            this.completed = false;
        }

        public String toString() {
            return "[" + (this.completed ? "X" : " ") + "] " + this.desc;
        }

        public void markTask() {
            this.completed = true;
        }

        public void unmarkTask() {
            this.completed = false;
        }
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
                int taskNo = Integer.parseInt(input.substring(4).trim());
                markTask(taskNo);
                continue;
            }

            if (input.startsWith("unmark")) {
                int taskNo = Integer.parseInt(input.substring(6).trim());
                unmarkTask(taskNo);
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

    private void addTask(String task) {
        tasks[taskCount] = new Task(task);
        taskCount++;
        
        String printResult = divider
                            + "added: " + task + "\n"
                            + divider;
        System.out.println(printResult);
    }

    private void markTask(int taskNo) {
        tasks[taskNo - 1].markTask();

        String printResult = divider
                            + "Nice! I've marked this task as done:\n"
                            + tasks[taskNo - 1].toString() + "\n"
                            + divider;
        
        System.out.println(printResult);
    }

    private void unmarkTask(int taskNo) {
        tasks[taskNo - 1].unmarkTask();

        String printResult = divider
                            + "OK, I've marked this task as not done yet:\n"
                            + tasks[taskNo - 1].toString() + "\n"
                            + divider;
        
        System.out.println(printResult);
    }

    private void printBye() {
        String printResult = divider
                            + "Bye. Hope to see you again soon!\n"
                            + divider;
        System.out.println(printResult);
    }
}
