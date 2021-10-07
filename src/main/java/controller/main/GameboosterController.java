package controller.main;

import animatefx.animation.GlowText;
import ch.dragxfly.quantumaccelerator.customControls.ToggleSwitch;
import ch.dragxfly.quantumaccelerator.tasks.GameboosterTasks;
import ch.dragxfly.quantumaccelerator.executors.Gamebooster;
import ch.dragxfly.quantumaccelerator.views.MainView;
import ch.dragxfly.quantumaccelerator.views.MultilingualView;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author jannis
 */
public class GameboosterController extends MultilingualView implements Initializable, MainView {

    @FXML
    private ToggleButton toggleBtnGameboost;
    @FXML
    private Button btnFreeStandbyRAM;
    @FXML
    private GridPane gridSettingsDeactivateGamebooster;
    @FXML
    private VBox vboxGameboosterSwitch;
    @FXML
    private Label lblOnDeactivateBooster;
    @FXML
    private Label lblResetPowerPlan;
    @FXML
    private Label lblResetCPUPrio;
    @FXML
    private Label lblResetSysMain;
    @FXML
    private VBox vboxBooster;
    @FXML
    private ImageView imgRocket;
    @FXML
    private AnchorPane container;
    @FXML
    private VBox vboxEnergy;
    @FXML
    private VBox vboxTweaks;
    @FXML
    private RadioButton chkFullPower;
    @FXML
    private RadioButton chkStandart;
    @FXML
    private RadioButton chkEco;
    @FXML
    private Rectangle recPowerVisualizer;
    @FXML
    private Label lblTitleEnergy;
    @FXML
    private Label lblTitleBooster;
    @FXML
    private Label lblTitleTools;
    //non FXML
    private Task task;
    private Thread thread;
    boolean resetPowerPlan;
    private GlowText glowText;
    private Preferences prefs;
    private Gamebooster gameboost;
    private boolean isResetGPUPrio, isResetSysMain, gameBoosterIsActive;
    private ScaleTransition pulseAnimation;
    private boolean canRunStandByCleaner = true;
    private final ToggleGroup tglGrpPower = new ToggleGroup();
    private final ToggleSwitch tglswCPUPrio = new ToggleSwitch();
    private final GameboosterTasks tasks = new GameboosterTasks();
    private final ToggleSwitch tglswResetSysMain = new ToggleSwitch();
    private final ToggleSwitch tglswResetPowerPlan = new ToggleSwitch();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chkFullPower.layoutYProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateVisualPowerIndicatorHeight();
            }
        });
        pulseAnimation = new ScaleTransition(Duration.seconds(0.2), toggleBtnGameboost);
        prefs = Preferences.userRoot().node(this.getClass().getName());
        gameBoosterIsActive = prefs.getBoolean("GameboosterIsActive", false);
        tglGrpPower.getToggles().add(chkFullPower);
        tglGrpPower.getToggles().add(chkStandart);
        tglGrpPower.getToggles().add(chkEco);
        vboxBooster.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.web("#0A0B21"), 20, 0, 0, 0));
        vboxEnergy.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.web("#0A0B21"), 20, 0, 0, 0));
        vboxTweaks.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.web("#0A0B21"), 20, 0, 0, 0));
        BackgroundImage boosterBackground = new BackgroundImage(new Image("/styles/icons/booster/BoosterBackgroundDark.png", true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        );
        container.setBackground(new Background(boosterBackground));
        setLanguage(super.getLanguage());
        tglswResetPowerPlan.setActivated(true);
        tglswCPUPrio.setActivated(true);
        tglswResetSysMain.setActivated(true);
        gridSettingsDeactivateGamebooster.add(tglswResetPowerPlan, 1, 0);
        gridSettingsDeactivateGamebooster.add(tglswCPUPrio, 1, 1);
        gridSettingsDeactivateGamebooster.add(tglswResetSysMain, 1, 2);
        gameBoosterIsActive = toggleBtnGameboost.isSelected();
        gameboost = new Gamebooster();
        toggleBtnGameboost.setSelected(gameBoosterIsActive);
        toggleBtnGameboost.setText(gameBoosterIsActive ? "Stop" : "Boost");
    }

    @FXML
    private void activateOrDeactivateGameboost(ActionEvent event) {
        playLoadingAnimations();
        vboxEnergy.setDisable(true);
        resetPowerPlan = tglswResetPowerPlan.isActivated();
        isResetGPUPrio = tglswCPUPrio.isActivated();
        isResetSysMain = tglswResetSysMain.isActivated();
        gameBoosterIsActive = toggleBtnGameboost.isSelected();
        prefs.putBoolean("GameboosterIsActive", gameBoosterIsActive);
        Task boost = getGameboosterTask();
        Task stopBoost = getDeactivateGameboosterTask();
        toggleBtnGameboost.setText(gameBoosterIsActive ? "initializing" : "deactivating");
        thread = new Thread(gameBoosterIsActive ? boost : stopBoost);
        thread.start();
        boost.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                stopLoadingAnimations();
                toggleBtnGameboost.setText("Stop");
            }
        });
        stopBoost.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                vboxEnergy.setDisable(false);
                stopLoadingAnimations();
                toggleBtnGameboost.setText("Boost");
            }
        });
    }

    private void playLoadingAnimations() {
        toggleBtnGameboost.setStyle("-fx-font-size: 16; -fx-font-family: \"Unispace\";");
        toggleBtnGameboost.setContentDisplay(ContentDisplay.TEXT_ONLY);
        glowText = new GlowText(toggleBtnGameboost, Color.web("#CE4AEA"), Color.web("#3F4DD9"));
        glowText.setCycleCount(Animation.INDEFINITE);
        glowText.setSpeed(0.5);
        pulseAnimation = new ScaleTransition(Duration.seconds(1), toggleBtnGameboost);
        pulseAnimation.setFromX(1);
        pulseAnimation.setFromY(1);
        pulseAnimation.setToX(1.1);
        pulseAnimation.setToY(1.1);
        pulseAnimation.setAutoReverse(true);
        pulseAnimation.setCycleCount(Animation.INDEFINITE);
        glowText.play();
        pulseAnimation.play();
    }

    private void stopLoadingAnimations() {
        toggleBtnGameboost.setContentDisplay(ContentDisplay.TOP);
        toggleBtnGameboost.setStyle("-fx-font-size: 12; -fx-font-family: \"Unispace\"; -fx-text-fill:white;");
        pulseAnimation.stop();
        glowText.stop();
    }

    private Task getGameboosterTask() {
        Task<Void> boosterTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                gameboost.runGameboost();
                return null;
            }
        };
        return boosterTask;
    }

    /**
     * Creates a task which deactivates Gamebooster
     *
     * @return task which deactivates Gamebooster
     */
    private Task getDeactivateGameboosterTask() {
        Task<Void> stopTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                gameboost.stopGameboost(resetPowerPlan, isResetGPUPrio, isResetSysMain);
                return null;
            }
        };
        return stopTask;
    }

    @FXML
    private void freeStandbyRAM(ActionEvent event) {
        if (canRunStandByCleaner) {
            task = tasks.getTaskClearStandbyRAM();
            btnFreeStandbyRAM.setDisable(true);
            canRunStandByCleaner = false;
            new Thread(task).start();
        }
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                btnFreeStandbyRAM.setDisable(false);
                canRunStandByCleaner = true;
            }
        });
    }

    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.lang", locale);
        btnFreeStandbyRAM.setText(bundle.getString("btnFreeStandbyRAM"));
        lblOnDeactivateBooster.setText(bundle.getString("lblOnDeactivateBooster"));
        lblResetPowerPlan.setText(bundle.getString("lblResetPowerPlan"));
        lblResetCPUPrio.setText(bundle.getString("lblResetCPUPrio"));
        lblResetSysMain.setText(bundle.getString("lblResetSysMain"));
        chkFullPower.setText(bundle.getString("chkFullPower"));
        chkStandart.setText(bundle.getString("chkStandart"));
        chkEco.setText(bundle.getString("chkEco"));
        lblTitleEnergy.setText(bundle.getString("lblTitleEnergy"));
        lblTitleBooster.setText(bundle.getString("lblTitleBooster"));
        lblTitleTools.setText(bundle.getString("lblTitleTools"));
    }

    @FXML
    private void playBoostAnimation(MouseEvent event) {
        if (pulseAnimation.getStatus() != Status.RUNNING) {
            pulseAnimation.setToX(1.1);
            pulseAnimation.setToY(1.1);
            pulseAnimation.play();
        }
    }

    @FXML
    private void stopBoostAnimation(MouseEvent event) {
        if (pulseAnimation.getStatus() != Status.RUNNING) {
            pulseAnimation.setToY(1);
            pulseAnimation.setToX(1);
            pulseAnimation.play();
        }
    }

    @FXML
    private void activateFullPower(ActionEvent event) {
        new Thread(tasks.getTaskActivatePerformancePlan()).start();
        updateVisualPowerIndicatorHeight();
    }

    @FXML
    private void acitvateNeutralPower(ActionEvent event) {
        new Thread(tasks.getTaskActivateStandardPlan()).start();
        updateVisualPowerIndicatorHeight();
    }

    @FXML
    private void activateEcoPower(ActionEvent event) {
        new Thread(tasks.getTaskActivateEcoPlan()).start();
        updateVisualPowerIndicatorHeight();
    }

    private void updateVisualPowerIndicatorHeight() {
        double newHeight = 0;
        recPowerVisualizer.setWidth(vboxEnergy.getWidth());
        if (chkFullPower.isSelected()) {
            newHeight = vboxEnergy.getHeight() - chkFullPower.getLayoutY();
        } else if (chkStandart.isSelected()) {
            newHeight = vboxEnergy.getHeight() - chkStandart.getLayoutY();
        } else if (chkEco.isSelected()) {
            newHeight = vboxEnergy.getHeight() - chkEco.getLayoutY();
        }
        Timeline resize = new Timeline(
                new KeyFrame(Duration.seconds(1.5), new KeyValue(recPowerVisualizer.heightProperty(), newHeight))
        );
        resize.play();
    }

    @Override
    public void onOpen() {
    }

    public void changeTheme(boolean isLightTheme) {
        BackgroundImage boosterBackground;
        if (isLightTheme) {
            boosterBackground = new BackgroundImage(new Image("/styles/icons/booster/BoosterBackgroundLight.png", true),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    new BackgroundSize(1.0, 1.0, true, true, false, false)
            );
        } else {
            boosterBackground = new BackgroundImage(new Image("/styles/icons/booster/BoosterBackgroundDark.png", true),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    new BackgroundSize(1.0, 1.0, true, true, false, false)
            );
        }
        container.setBackground(new Background(boosterBackground));
    }
}
