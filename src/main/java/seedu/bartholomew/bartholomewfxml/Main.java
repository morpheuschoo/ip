package seedu.bartholomew.bartholomewfxml;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import seedu.bartholomew.bartholomewjava.Bartholomew;

/**
 * A GUI for Bartholomew using FXML.
 */
public class Main extends Application {

    private Bartholomew bartholomew = new Bartholomew("data/bartholomew.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            
            stage.setScene(scene);
            stage.setTitle("Bartholomew");
            stage.setResizable(false);
            
            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setBartholomew(bartholomew);
            
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}