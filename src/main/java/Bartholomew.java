import java.util.Scanner;

public class Bartholomew {
    public static void main(String[] args) {
        Bartholomew bot = new Bartholomew();
        bot.printStart();
        bot.eventLoop();
        bot.printBye();
    }

    private String divider = "____________________________________________________________\n";

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
                break;
            }

            printEcho(input);
        }
        scanner.close();
    }

    private void printEcho(String msg) {
        String printResult = divider
                            + msg + "\n"
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
