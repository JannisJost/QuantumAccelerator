package ch.dragxfly.quantumaccelerator;

import Models.ViewsModel;
import animatefx.animation.FadeIn;
import controller.main.MainViewController;
import ch.dragxfly.quantumaccelerator.ViewManager.ViewCreator;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    private Pane features;
    private Pane storage;
    private Pane gamebooster;
    private Pane delWindowsApps;
    private Pane extras;
    private Pane privacy;
    private Pane settings;

    @Override
    public void start(Stage stage) throws Exception {
        setViews();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main/MainView.fxml"));
        Parent root = loader.load();
        MainViewController controller = loader.getController();
        ViewsModel viewsModel = new ViewsModel(features, storage, gamebooster, delWindowsApps, extras, privacy, settings);
        controller.setViewsModel(viewsModel);
        Scene scene = new Scene(root);
        scene.getStylesheets().clear();
        scene.getRoot().setStyle("");
        Image iconImage = new Image("/styles/icons/appLogo_Big.png");
        stage.getIcons().add(iconImage);
        stage.setTitle("QuantumAccelerator");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        controller.setStarterTheme();
        controller.runStartup();
        new FadeIn(root).play();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Loads all the "main" views
     */
    private void setViews() {
        try {
            ViewCreator creator = new ViewCreator();
            features = creator.getViewFeatures();
            storage = creator.getViewStorage();
            gamebooster = creator.getViewGamebooster();
            delWindowsApps = creator.getViewDelWindowsApps();
            extras = creator.getViewExtras();
            privacy = creator.getViewPrivacy();
            settings = creator.getViewSettings();
        } catch (IOException e) {
            System.err.println("Error while loading view: " + e);
        }
    }

}