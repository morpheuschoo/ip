import java.util.Scanner;

public class Bartholomew {
    public static void main(String[] args) {
        Bartholomew bot = new Bartholomew();
        bot.printStart();
        bot.eventLoop();
        bot.printBye();
    }

    private final String divider = "____________________________________________________________\n";
    
    private int taskCount = 0;
    private String[] tasks = new String[100];

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
            
            switch(input) {
                case "bye":
                    scanner.close();
                    return;
                case "list":
                    printList();
                    break;
                default:
                    addTask(input);
                    break;
            }
        }
    }

    private void printList() {
        String resultString = divider;
        for (int i = 0; i < taskCount; i++) {
            resultString += Integer.toString(i + 1) + ". " + tasks[i] + "\n";
        }
        resultString += divider;
        System.out.println(resultString);
    }

    private void addTask(String task) {
        tasks[taskCount] = task;
        taskCount++;
        
        String printResult = divider
                            + "added: " + task + "\n"
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
