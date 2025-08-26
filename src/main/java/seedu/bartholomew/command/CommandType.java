package seedu.bartholomew.command;

public enum CommandType {
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    LIST("list"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    BYE("bye"),
    FIND("find");

    private final String command;

    private CommandType(String command) {
        this.command = command;
    }

    public static CommandType fromString(String input) {
        for (CommandType c : CommandType.values()) {
            if (input.startsWith(c.command)) {
                return c;
            }
        }
        return null;
    }
}