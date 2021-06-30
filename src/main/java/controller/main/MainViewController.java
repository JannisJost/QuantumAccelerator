package controller.main;

import Models.ViewsModel;
import ch.dragxfly.quantumaccelerator.ViewManager.ViewOpener;
import animatefx.animation.AnimationFX;
import animatefx.animation.BounceInRight;
import animatefx.animation.BounceOutRight;
import animatefx.animation.RotateIn;
import animatefx.animation.RotateOut;
import ch.dragxfly.quantumaccelerator.CustomControls.progressindicator.CustomProgressIndicator;
import ch.dragxfly.quantumaccelerator.Hardware.HardwareObserver;
import ch.dragxfly.quantumaccelerator.Style.Animations.ButtonAnimator;
import ch.dragxfly.quantumaccelerator.Style.Animations.MainAnimations;
import ch.dragxfly.quantumaccelerator.ViewManager.MultilingualView;
import ch.dragxfly.quantumaccelerator.notifications.NotificationManager;
import controller.popupwindows.SystemMonitorController;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainViewController extends MultilingualView implements Initializable, Observer {

    @FXML
    private Button btnGameBooster;
    @FXML
    private BorderPane mainPane;
    @FXML
    private Button btnFeatures;
    @FXML
    private Button btnShowStorage;
    @FXML
    private VBox vbox1;
    @FXML
    private Button btnDelWindowsApps;
    @FXML
    private Button btnShowPrivacy;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnExtras;
    @FXML
    private Button btnAbout;
    @FXML
    private Button btnCloseApplication;
    @FXML
    private Button btnNotifications;
    @FXML
    private ButtonBar btnBarLeft;
    private ViewsModel viewsModel;
    private Button menuButtonPressedLast;
    @FXML
    private Button btnChangeTheme;
    @FXML
    private ImageView imgTheme;
    boolean lightThemeActive;
    @FXML
    private Button btnFullscreen;
    @FXML
    private Button btnMinimize;
//    private ProgressIndicator progCPUUsage;
//    private ProgressIndicator progMemory;
    @FXML
    private Button btnShowSystemGraph;
    @FXML
    private ImageView imgSettings;
    @FXML
    private ImageView imgMaximize;
    @FXML
    private HBox boxCPU;
    @FXML
    private HBox boxMemory;
    //Non FXML
    private final NotificationManager notificationManager = new NotificationManager();
    private final Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
    private static final String LIGHTTHEMEISACTIVE = "darktheme";
    private static final String CURRENTTHEME = "currentTheme";
    private final ButtonAnimator btnAnimator = new ButtonAnimator();
    private final MainAnimations animation = new MainAnimations();
    private static final String ANIMATIONSACTIVE = "playanimations";
    private final Preferences pref = Preferences.userRoot();
    private final ViewOpener viewOpener = new ViewOpener();
    private Pane view;
    private boolean isFullscreen = false;
    private double xOffset;
    private double yOffset;
    private final HardwareObserver hardware = new HardwareObserver();
    private CustomProgressIndicator progCPU = new CustomProgressIndicator("/styles/icons/menubar/CPU.png");
    private CustomProgressIndicator progMemory = new CustomProgressIndicator("/styles/icons/menubar/memory.png");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
        progCPU.setStyleSheet(pref.get(CURRENTTHEME, "/styles/darktheme.css"));
        progCPU.setRadius(38);
        progMemory.setRadius(38);
        boxCPU.getChildren().add(progCPU);
        boxMemory.getChildren().add(progMemory);
        hardware.addObserver(this);
        hardware.startObserver();
        //Sets the "Feature" view as default view from start
        mainPane.setCenter(view);
    }

    /**
     * Sets "Features" as center content
     *
     * @param event
     */
    @FXML
    private void showFeatures(ActionEvent event) {
        if (menuButtonPressedLast != btnFeatures) {
            btnAnimator.animate(btnFeatures);
            view = viewsModel.getFeatures();
            mainPane.setCenter(view);
            selectButton(btnFeatures);
        }
    }

    @FXML
    private void showStorage(ActionEvent event) {
        if (menuButtonPressedLast != btnShowStorage) {
            btnAnimator.animate(btnShowStorage);
            view = viewsModel.getStorage();
            mainPane.setCenter(view);
            selectButton(btnShowStorage);
        }
    }

    @FXML
    private void showDelWindowsApps(ActionEvent event) {
        if (menuButtonPressedLast != btnDelWindowsApps) {
            btnAnimator.animate(btnDelWindowsApps);
            view = viewsModel.getDelWindowsApps();
            mainPane.setCenter(view);
            selectButton(btnDelWindowsApps);
        }
    }

    @FXML
    private void showGamingbooster(ActionEvent event) {
        if (menuButtonPressedLast != btnGameBooster) {
            btnAnimator.animate(btnGameBooster);
            view = viewsModel.getGamebooster();
            mainPane.setCenter(view);
            selectButton(btnGameBooster);
        }
    }

    @FXML
    private void showPrivacy(ActionEvent event) {
        if (menuButtonPressedLast != btnShowPrivacy) {
            btnAnimator.animate(btnShowPrivacy);
            view = viewsModel.getPrivacy();
            mainPane.setCenter(view);
            selectButton(btnShowPrivacy);
        }
    }

    @FXML
    private void openSettings(ActionEvent event) {
        if (menuButtonPressedLast != btnSettings) {
            mainPane.setCenter(viewsModel.getSettings());
            animation.animateSettings(imgSettings);
            selectButton(btnSettings);
        }
    }

    @FXML
    private void showExtras(ActionEvent event) {
        if (menuButtonPressedLast != btnExtras) {
            btnAnimator.animate(btnExtras);
            view = viewsModel.getExtras();
            mainPane.setCenter(view);
            selectButton(btnExtras);
        }
    }

    /**
     * Opens the "about" aka. credits window
     *
     * @param event about button clicked
     */
    @FXML
    private void openViewAbout(ActionEvent event) {
        viewOpener.openThemeableView("/fxml/About.fxml", "About", false);
    }

    /**
     * Used to move the window with the cursor
     *
     * @param event
     */
    @FXML
    private void moveWindowSecond(MouseEvent event) {
        //Moves the window if it is not in Fullscreen
        if (!isFullscreen) {
            Stage currentStage = (Stage) btnCloseApplication.getScene().getWindow();
            currentStage.setX(event.getScreenX() + xOffset);
            currentStage.setY(event.getScreenY() + yOffset);
        }
    }

    /**
     * Used to move the window with the cursor
     *
     * @param event
     */
    @FXML
    private void moveWindow(MouseEvent event) {
        if (!isFullscreen) {
            Stage currentStage = (Stage) btnCloseApplication.getScene().getWindow();
            xOffset = currentStage.getX() - event.getScreenX();
            yOffset = currentStage.getY() - event.getScreenY();
        }
    }

    /**
     * Shows or hides the notification tab
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void showOrHideNotifications(ActionEvent event) throws IOException {
        btnAnimator.animate(btnNotifications);
        view = FXMLLoader.load(getClass().getResource("/fxml/Main/Notifications.fxml"));
        if (!notificationManager.notificationIsShowing()) {
            notificationManager.changeViewed();
            mainPane.setRight(view);
            if (pref.getBoolean(ANIMATIONSACTIVE, true)) {
                new BounceInRight(view).play();
            }
        } else {
            //Plays animation if activated
            if (pref.getBoolean(ANIMATIONSACTIVE, true)) {
                view = (Pane) mainPane.getRight();
                AnimationFX animationNotificationOut = new BounceOutRight(view);
                animationNotificationOut.play();
                btnNotifications.setDisable(true);
                animationNotificationOut.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        btnNotifications.setDisable(false);
                        mainPane.setRight(null);
                    }
                });
                //Plays no animation
            } else {
                mainPane.setRight(null);
            }
            notificationManager.changeViewed();
        }
    }

    /**
     * Closes/stops the whole application and saves the prefs before
     *
     * @param event
     */
    @FXML
    private void closeApplication(ActionEvent event) {
        try {
            pref.flush();
        } catch (BackingStoreException e) {
            System.err.println("Error while flushing pref while closing");
        }
        System.exit(0);
    }

    public void setViewsModel(ViewsModel viewsModel) {
        this.viewsModel = viewsModel;
    }

    private void selectButton(Button pressedButton) {
        if (menuButtonPressedLast != null) {
            //Resets the look of the unselected button
            menuButtonPressedLast.setStyle("");
        }
        pressedButton.setStyle("-fx-border-width: 0 0 0 5;");
        menuButtonPressedLast = pressedButton;
    }

    public void setStarterTheme() {
        lightThemeActive = pref.get(CURRENTTHEME, "/styles/darktheme.css").equals("/styles/darktheme.css");
        btnChangeTheme.fire();
    }

    @FXML
    public void changeTheme(ActionEvent event) {
        String file;
        Scene currentScene = ((Node) event.getSource()).getScene();
        currentScene.getStylesheets().clear();
        if (lightThemeActive) {
            currentScene.getStylesheets().add("/styles/darktheme.css");
            progCPU.setStyleSheet("/styles/darktheme.css");
            progMemory.setStyleSheet("/styles/darktheme.css");
            pref.put(CURRENTTHEME, "/styles/darktheme.css");
            file = "/styles/icons/button/darktheme.png";
        } else {
            currentScene.getStylesheets().add("/styles/lighttheme.css");
            pref.put(CURRENTTHEME, "/styles/lighttheme.css");
            file = "/styles/icons/button/lighttheme.png";
            progMemory.setStyleSheet("/styles/lighttheme.css");
            progCPU.setStyleSheet("/styles/lighttheme.css");
        }
        try {
            pref.flush();
        } catch (BackingStoreException e) {

        }
        Image image = new Image(file);
        lightThemeActive = !lightThemeActive;
        //If animation is active
        if (pref.getBoolean(ANIMATIONSACTIVE, true)) {
            AnimationFX animOut = new RotateOut(imgTheme);
            animOut.play();
            prefs.putBoolean(LIGHTTHEMEISACTIVE, lightThemeActive);
            animOut.setOnFinished((ActionEvent arg0) -> {
                imgTheme.setImage(image);
                new RotateIn(imgTheme).play();
            });
        } else {
            imgTheme.setImage(image);
        }
    }

    @FXML
    private void fullScreen(ActionEvent event) {
        System.setProperty("prism.forcerepaint", "true");
        Stage stage = (Stage) btnCloseApplication.getScene().getWindow();
        if (!isFullscreen) {
            stage.setMaximized(true);
            btnCloseApplication.getScene().getRoot().setStyle("-fx-background-radius: 0;");

            imgMaximize.setImage(new Image("/styles/icons/menubar/notMaximized.png"));
        } else {
            stage.setMaximized(false);
            btnCloseApplication.getScene().getRoot().setStyle("-fx-background-radius: 12;");
            imgMaximize.setImage(new Image("/styles/icons/menubar/maximize.png"));
        }
        isFullscreen = !isFullscreen;
        System.setProperty("prism.forcerepaint", "false");
    }

    /**
     * Iconifies the window
     *
     * @param event
     */
    @FXML
    private void minimize(ActionEvent event) {
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        Double memoryUsage = hardware.getMemoryUsage() * 100;
        Double CPUUsage = hardware.getCpuUsage() * 100;
        //Runlater sets the prog indicators
        Platform.runLater(() -> {
            progMemory.setProgress(memoryUsage.intValue());
            progCPU.setProgress(CPUUsage.intValue());
        });
    }

    /**
     * Shows the "system monitor" popup
     *
     * @param event
     */
    @FXML
    private void showSystemGraph(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SystemMonitor.fxml"));
            Parent root = loader.load();
            SystemMonitorController controller = loader.getController();
            hardware.addObserver(controller);
            controller.setHardware(hardware);
            Scene sceneSystemMonitor = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(sceneSystemMonitor);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initStyle(StageStyle.TRANSPARENT);
            sceneSystemMonitor.setFill(Color.TRANSPARENT);
            stage.setTitle("System monitor");
            stage.show();
            controller.setTheme();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Is called from the invocator on startup
     */
    public void startup() {
        btnCloseApplication.getScene().getRoot().setStyle("-fx-background-radius: 12;");
        mainPane.setCenter(viewsModel.getFeatures());
        selectButton(btnFeatures);
        viewOpener.openThemeableView("/fxml/RestorePointCreator.fxml", "Restore", true);
    }

    private void runGarbageCollector(MouseEvent event) {
        System.gc();
    }

    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.lang", locale);
        btnExtras.setText(bundle.getString("btnExtras"));
        btnNotifications.setText(bundle.getString("btnNotifications"));
        btnShowStorage.setText(bundle.getString("btnShowStorage"));
        btnShowPrivacy.setText(bundle.getString("btnShowPrivacy"));
        btnGameBooster.setText(bundle.getString("btnGameBooster"));
    }

}
