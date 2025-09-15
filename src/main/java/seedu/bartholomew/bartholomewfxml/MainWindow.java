package seedu.bartholomew.bartholomewfxml;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import seedu.bartholomew.bartholomewjava.Bartholomew;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Bartholomew bartholomew;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image bartholomewImage = new Image(this.getClass().getResourceAsStream("/images/bartholomew.png"));

    /**
     * Initializes the chat with Bartholomew.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setBartholomew(Bartholomew b) {
        bartholomew = b;
        displayWelcome();
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Bartholomew's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = bartholomew.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBartholomewDialog(response, bartholomewImage)
        );
        userInput.clear();

        if (bartholomew.shouldExit()) {
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                    Platform.runLater(() -> Platform.exit());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * Displays the welcome message from Bartholomew.
     */
    public void displayWelcome() {
        String welcomeMessage = bartholomew.getWelcome();
        dialogContainer.getChildren().add(
            DialogBox.getBartholomewDialog(welcomeMessage, bartholomewImage)
        );
    }
}