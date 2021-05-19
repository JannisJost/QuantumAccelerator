package ch.dragxfly.quantumaccelerator.notifications;

import ch.dragxfly.quantumaccelerator.ViewManager.ThemeableWindow;
import java.util.Observable;
import java.util.Observer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author janni
 */
public class LoadingScreen extends ThemeableWindow implements Observer {

    Scene scene;
    private final String text;
    VBox vbox = new VBox();
    private final Stage stage;
    private final ProgressIndicator loader = new ProgressIndicator();

    public LoadingScreen(String text) {
        loader.setScaleX(1.5);
        loader.setScaleY(1.5);
        this.text = text;
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        vbox.setFillWidth(true);
        Label lblText = new Label(text);
        vbox.getChildren().addAll(loader, lblText);
        stage = new Stage();
        stage.setWidth(320);
        stage.setHeight(224);
        BorderPane root = new BorderPane(vbox);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        loader.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        lblText.setFont(new Font(25));
    }

    public void showLoading() {
        stage.show();
        setTheme();
    }

    public void closeScene() {
        stage.close();
    }

    @Override
    public void update(Observable o, Object arg) {
        closeScene();
    }

    @Override
    public void setTheme() {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(super.getCURRENTTHEME(), ""));
    }
}
