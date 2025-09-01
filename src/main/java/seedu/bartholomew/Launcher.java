package seedu.bartholomew;

import javafx.application.Application;
import seedu.bartholomew.bartholomewfxml.Main;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
