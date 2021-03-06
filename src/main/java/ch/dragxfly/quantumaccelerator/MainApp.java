package ch.dragxfly.quantumaccelerator;

import ch.dragxfly.quantumaccelerator.models.ViewsModel;
import animatefx.animation.FadeIn;
import ch.dragxfly.quantumaccelerator.views.MainView;
import controller.main.MainViewController;
import ch.dragxfly.quantumaccelerator.views.ViewCreator;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

public class MainApp extends Application {

    private Pane features, storage, delWindowsApps, extras, privacy, settings;
    private Pair<Pane, MainView> gamebooster;

    @Override
    public void start(Stage stage) throws Exception {
        createMainViews();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main/MainView.fxml"));
        Parent root = loader.load();
        MainViewController controller = loader.getController();
        //stores the views to make them accessible to the MainViewController
        ViewsModel viewsModel = new ViewsModel(features, storage, gamebooster, delWindowsApps, extras, privacy, settings);
        controller.setViewsModel(viewsModel);
        Scene scene = new Scene(root);
        scene.getStylesheets().clear();
        scene.getRoot().setStyle("");
        //sets app logo as icon
        Image icon = new Image("/styles/icons/appicon.png");
        stage.getIcons().add(icon);
        stage.setTitle("QuantumAccelerator");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.show();
        controller.setStarterTheme();
        //runs things required on startup
        controller.startup();
        new FadeIn(root).play();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Loads all the views held in the main view
     */
    private void createMainViews() {
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
