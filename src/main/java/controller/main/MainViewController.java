package controller.main;

import Models.ViewsModel;
import ch.dragxfly.quantumaccelerator.ViewManager.ViewOpener;
import animatefx.animation.AnimationFX;
import animatefx.animation.BounceInRight;
import animatefx.animation.BounceOutRight;
import animatefx.animation.RotateIn;
import animatefx.animation.RotateOut;
import ch.dragxfly.quantumaccelerator.FXMLmanager;
import ch.dragxfly.quantumaccelerator.Hardware.HardwareObserver;
import ch.dragxfly.quantumaccelerator.Style.Animations.ButtonAnimator;
import ch.dragxfly.quantumaccelerator.Style.Animations.MainAnimations;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainViewController implements Initializable, Observer {

    private CheckBox backgroundApps;
    private CheckBox powerPlan;
    private CheckBox deleteTemp;
    private CheckBox delDownloadInstaller;
    private Label state;
    private TextFlow log;
    private ResourceBundle bundle;
    private Locale locale;
    @FXML
    private Button btnGameBooster;
    private ProgressIndicator progress;
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
    //Non FXML
    private final ButtonAnimator btnAnimator = new ButtonAnimator();
    private final NotificationManager notificationManager = new NotificationManager();
    @FXML
    private Button btnAbout;
    @FXML
    private Button btnCloseApplication;

    private double xOffset;
    private double yOffset;
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
    private final Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
    boolean lightThemeActive;
    @FXML
    private Button btnFullscreen;
    private static final javafx.geometry.Rectangle2D SCREEN_BOUNDS = Screen.getPrimary()
            .getVisualBounds();
    private boolean isFullscreen = false;
    @FXML
    private Button btnMinimize;
    private static final String LIGHTTHEMEISACTIVE = "darktheme";
    private static final String CURRENTTHEME = "currentTheme";
    private final Preferences pref = Preferences.userRoot();
    private Scene scene;
    private final ViewOpener viewOpener = new ViewOpener();
    private Pane view;
    private final HardwareObserver hardware = new HardwareObserver();
    @FXML
    private ProgressIndicator progCPUUsage;
    @FXML
    private ProgressIndicator progMemory;
    @FXML
    private Button btnShowSystemGraph;
    @FXML
    private ImageView imgSettings;
    private final MainAnimations animation = new MainAnimations();
    private static final String ANIMATIONSACTIVE = "playanimations";
    @FXML
    private ImageView imgMaximize;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hardware.addObserver(this);
        hardware.startObserver();
        //Sets the "Feature" view as default view from start
        FXMLmanager object = new FXMLmanager();
        Pane view = object.getPage("Features");
        mainPane.setCenter(view);
    }

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

    public void loadLang(String lang) {
        locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("languages.lang", locale);
        btnFeatures.setText(bundle.getString("btnFeatures"));
        btnShowStorage.setText(bundle.getString("btnShowStorage"));
        btnShowPrivacy.setText(bundle.getString("btnShowPrivacy"));
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

    @FXML
    private void openViewAbout(ActionEvent event) {
        viewOpener.openThemeableView("/fxml/About.fxml", "About", false);
    }

    @FXML
    private void moveWindowSecond(MouseEvent event) {
        //Moves the window if it is not in Fullscreen
        if (!isFullscreen) {
            Stage currentStage = (Stage) btnCloseApplication.getScene().getWindow();
            currentStage.setX(event.getScreenX() + xOffset);
            currentStage.setY(event.getScreenY() + yOffset);
        }
    }

    @FXML
    private void moveWindow(MouseEvent event) {
        if (!isFullscreen) {
            Stage currentStage = (Stage) btnCloseApplication.getScene().getWindow();
            xOffset = currentStage.getX() - event.getScreenX();
            yOffset = currentStage.getY() - event.getScreenY();
        }
    }

    @FXML
    private void showOrHideNotifications(ActionEvent event) throws IOException {
        btnAnimator.animate(btnNotifications);
        view = FXMLLoader.load(getClass().getResource("/fxml/Main/Notifications.fxml"));;
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
                AnimationFX animation = new BounceOutRight(view);
                animation.play();
                btnNotifications.setDisable(true);
                animation.setOnFinished(new EventHandler<ActionEvent>() {
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

    @FXML
    private void closeApplication(ActionEvent event) {
        //Add logic to save variables before closing
        try {
            pref.flush();
        } catch (Exception e) {
            System.err.println("Error while flushing pref on closing");
        }
        System.exit(0);
    }

    public void setViewsModel(ViewsModel viewsModel) {
        this.viewsModel = viewsModel;
    }

    private void selectButton(Button b) {
        if (menuButtonPressedLast != null) {
            //Resets the look of the now unselected button
            menuButtonPressedLast.setStyle("");
        }
        b.setStyle("-fx-border-width: 0 0 0 5;");
        menuButtonPressedLast = b;
    }

    public void setStarterTheme() {
        if (pref.get(CURRENTTHEME, "/styles/darktheme.css").equals("/styles/darktheme.css")) {
            lightThemeActive = true;
        } else {
            lightThemeActive = false;
        }
        btnChangeTheme.fire();
    }

    @FXML
    public void changeTheme(ActionEvent event) {
        String file;
        Scene scene = ((Node) event.getSource()).getScene();
        scene.getStylesheets().clear();
        if (lightThemeActive) {
            scene.getStylesheets().add("/styles/darktheme.css");
            pref.put(CURRENTTHEME, "/styles/darktheme.css");
            file = "/styles/icons/button/darktheme.png";
        } else {
            scene.getStylesheets().add("/styles/lighttheme.css");
            pref.put(CURRENTTHEME, "/styles/lighttheme.css");
            file = "/styles/icons/button/lighttheme.png";
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
            animOut.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    imgTheme.setImage(image);
                    new RotateIn(imgTheme).play();
                }
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
            imgMaximize.setImage(new Image("/styles/icons/menubar/notMaximized.png"));
        } else {
            stage.setMaximized(false);
            imgMaximize.setImage(new Image("/styles/icons/menubar/maximize.png"));
        }
        isFullscreen = !isFullscreen;
        System.setProperty("prism.forcerepaint", "false");
    }

    @FXML
    private void minimize(ActionEvent event) {
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        double memoryUsage = hardware.getMemoryUsage();
        double CPUUsage = hardware.getCpuUsage();
        if (memoryUsage >= 0.75) {
            progMemory.setStyle("-fx-accent: #b80000 ;");
        } else {
            progMemory.setStyle("");
        }
        if (CPUUsage >= 0.90) {
            progCPUUsage.setStyle("-fx-accent: #b80000 ;");

        } else {
            progCPUUsage.setStyle("");
        }
        //Runlater sets the prog indicators
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                progMemory.setProgress(memoryUsage);
                progCPUUsage.setProgress(CPUUsage);
            }
        });
    }

    @FXML
    private void showSystemGraph(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SystemMonitor.fxml"));
            Parent root = loader.load();
            SystemMonitorController controller = loader.getController();
            hardware.addObserver(controller);
            controller.setHardware(hardware);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setTitle("System monitor");
            stage.show();
            controller.setTheme();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @FXML
    private void runGarbageCollector(MouseEvent event) {
        System.gc();
    }

    public void runStartup() {
        mainPane.setCenter(viewsModel.getFeatures());
        selectButton(btnFeatures);
        viewOpener.openThemeableView("/fxml/RestorePointCreator.fxml", "Restore", true);
    }
}
