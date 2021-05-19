package ch.dragxfly.quantumaccelerator.Style;

import ch.dragxfly.quantumaccelerator.tasks.CustomToolTipTasks;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author janni
 */
public class CustomToolTip implements Observer {

    private ProgressBar progCountdown;
    private Scene scene;
    private final Stage stage = new Stage();
    private Label lblInfo;
    private Button btnClose;
    private VBox vbox;
    private HBox hbox;
    private double xOffset = 0;
    private double yOffset = 0;
    private CustomToolTipTasks tasks;

    public CustomToolTip() {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
    }

    private void resetAll() {
        progCountdown = new ProgressBar();
        progCountdown.setProgress(1);
        progCountdown.setMaxWidth(Double.MAX_VALUE);
        progCountdown.setMinHeight(Double.MIN_VALUE);
        progCountdown.setPrefHeight(10);
        lblInfo = new Label();
        hbox = new HBox();
        vbox = new VBox();
        btnClose = new Button("X");
        vbox.setFillWidth(true);
        btnClose.setCancelButton(true);
        btnClose.setOnAction((ActionEvent ae) -> {
            hideToolTip();
        });
        vbox.setOnMousePressed((MouseEvent event) -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        vbox.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });
        vbox.setSpacing(5);
        hbox.setAlignment(Pos.CENTER_RIGHT);
        lblInfo.setMaxWidth(350);
        lblInfo.setWrapText(true);
        vbox.setPadding(new Insets(5, 5, 5, 5));
    }

    public void showToolTip(String text, MouseEvent event) {
        if (tasks != null) {
            tasks.cancelCountdown();
        }
        resetAll();
        show(text, event);
    }

    private void show(String text, MouseEvent event) {
        lblInfo.setText(text);
        hbox.getChildren().add(btnClose);
        vbox.getChildren().add(hbox);
        vbox.getChildren().add(progCountdown);
        vbox.getChildren().add(lblInfo);
        vbox.getStyleClass().add("customToolTip");
        scene = new Scene(vbox);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("/styles/customToolTip.css");
        stage.toFront();
        stage.setX(event.getScreenX() + 10);
        stage.setY(event.getScreenY() + 10);
        stage.setScene(scene);
        stage.show();
        stage.setAlwaysOnTop(true);
        startTimer();
    }

    private void hideToolTip() {
        //Closes the tooltip
        tasks.cancelCountdown();
        stage.close();
    }

    private void startTimer() {
        tasks = new CustomToolTipTasks();
        tasks.startCountdownTimer(this);
    }

    public void setProgressBarTo(double value) {
        Platform.runLater(() -> {
            progCountdown.setProgress(1 - value);
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        tasks.deleteObserver(this);
        Platform.runLater(() -> {
            hideToolTip();
        });
    }
}
