public class Bartholomew {
    public static void main(String[] args) {
        Bartholomew bot = new Bartholomew();
        bot.printStart();
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

    private void printBye() {
        String printResult = "Bye. Hope to see you again soon!\n"
                            + divider;
        System.out.println(printResult);
    }
}
