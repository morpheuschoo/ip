package seedu.bartholomew.bartholomewfxml;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * An example of a custom control using FXML.
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        if (img != null) {
            displayPicture.setImage(img);
        } else {
            // If image is null, make ImageView invisible
            displayPicture.setVisible(false);
        }
        
        // Apply consistent styling for all dialog boxes
        applyBaseDialogStyle();
    }
    
    /**
     * Applies base styling that's consistent for all dialog boxes.
     */
    private void applyBaseDialogStyle() {
        // Common style properties
        dialog.setStyle(
            "-fx-background-radius: 15; " +
            "-fx-padding: 10px; " +
            "-fx-font-size: 14px; " +
            "-fx-text-fill: white; " +
            "-fx-background-color: #3498DB;"
        );
        
        // Make images the same size
        displayPicture.setFitHeight(45.0);
        displayPicture.setFitWidth(45.0);
        
        // Add spacing between image and text
        this.setSpacing(10.0);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        
        // Change color for user messages (to green)
        dialog.setStyle(
            dialog.getStyle().replace("-fx-background-color: #3498DB;", "-fx-background-color: #2ECC71;")
        );
    }

    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.flip();
        return db;
    }

    public static DialogBox getBartholomewDialog(String text, Image img) {
        return new DialogBox(text, img);
    }
}