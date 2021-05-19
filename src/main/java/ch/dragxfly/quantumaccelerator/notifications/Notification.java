package ch.dragxfly.quantumaccelerator.notifications;

import java.util.Date;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author janni
 */
public class Notification {

    private final String title;
    private final String message;
    private Date date;

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public Pane createPane() {
        Pane pane = new Pane();
        VBox vbox = new VBox();
        Label lblText = new Label();
        lblText.setWrapText(true);
        lblText.setText(message);
        Label lblTitle = new Label(title);
        lblTitle.setTextFill(Color.web("white"));
        lblTitle.setStyle(
                "-fx-background-color:#6ea2f7;"
                + "-fx-font-color: white;");
        lblText.setStyle("-fx-background-color:white;");
        vbox.prefWidthProperty().bind(pane.widthProperty());
        vbox.prefHeightProperty().bind(pane.heightProperty());
        lblTitle.prefWidthProperty().bind(vbox.widthProperty());
        lblText.prefWidthProperty().bind(vbox.widthProperty());
        vbox.setFillWidth(true);
        vbox.getChildren().add(0, lblTitle);
        vbox.getChildren().add(1, lblText);
        pane.getChildren().add(vbox);
        return pane;
    }
}
