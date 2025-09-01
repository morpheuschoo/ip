package seedu.bartholomew.bartholomewfxml;

import seedu.bartholomew.bartholomewjava.Bartholomew;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Bartholomew bartholomew = new Bartholomew("data/bartholomew.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            MainWindow controller = fxmlLoader.getController();
            controller.setDuke(bartholomew);
            
            // Show welcome message on startup
            controller.displayWelcome();
            
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


