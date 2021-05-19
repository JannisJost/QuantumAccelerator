package controller.main;

import animatefx.animation.AnimationFX;
import animatefx.animation.SlideInUp;
import ch.dragxfly.quantumaccelerator.CustomControls.ToggleSwitch;
import ch.dragxfly.quantumaccelerator.tasks.GameboosterTasks;
import ch.dragxfly.quantumaccelerator.booster.GameboosterManager;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class GameboosterController implements Initializable {

    @FXML
    private AnchorPane anchorPaneGameboosterSwitch;
    @FXML
    private Label lblGameboostStatus;
    @FXML
    private ToggleButton toggleBtnGameboost;

    private GameboosterManager gameboost;
    private AnimationFX labelAnim;
    @FXML
    private ProgressIndicator progressBoost;
    private Thread thread;
    private Preferences prefs;
    private boolean gameBoosterIsActive;
    boolean resetPowerPlan;
    @FXML
    private Button btnApplyCheckBoxes;
    private boolean resetGPUPrio;
    @FXML
    private Button btnFreeStandbyRAM;
    private final GameboosterTasks tasks = new GameboosterTasks();
    private boolean canRunStandByCleaner = true;
    private Task task;
    @FXML
    private CheckBox chkDelInstallersFromDownload;
    @FXML
    private GridPane gridSettingsDeactivateGamebooster;
    private final ToggleSwitch tglswResetPowerPlan = new ToggleSwitch();
    private final ToggleSwitch tglswCPUPrio = new ToggleSwitch();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tglswResetPowerPlan.setSwitchedOn(true);
        tglswCPUPrio.setSwitchedOn(true);
        gridSettingsDeactivateGamebooster.add(tglswResetPowerPlan, 1, 0);
        gridSettingsDeactivateGamebooster.add(tglswCPUPrio, 1, 1);
        gameBoosterIsActive = toggleBtnGameboost.isSelected();
        gameboost = new GameboosterManager();
        prefs = Preferences.userRoot().node(this.getClass().getName());
        gameBoosterIsActive = prefs.getBoolean("GameboosterIsActive", false);
        toggleBtnGameboost.setSelected(gameBoosterIsActive);
        labelAnim = new SlideInUp(lblGameboostStatus);
        anchorPaneGameboosterSwitch.setStyle(gameBoosterIsActive ? "-fx-background-color:#83c480;" : "-fx-background-color:#c48080;");
        lblGameboostStatus.setText(gameBoosterIsActive ? "active" : "deactivated");
    }

    private void freeRAM(ActionEvent event) {
        gameboost.freeRAM();
    }

    @FXML
    private void activateOrDeactivateGameboost(ActionEvent event) {
        resetPowerPlan = tglswResetPowerPlan.getIsActivated();
        resetGPUPrio = tglswCPUPrio.getIsActivated();
        gameBoosterIsActive = toggleBtnGameboost.isSelected();
        prefs.putBoolean("GameboosterIsActive", gameBoosterIsActive);
        progressBoost.setVisible(true);
        Task boost = getGameboosterTask();
        Task stopBoost = getDeactivateGameboosterTask();
        lblGameboostStatus.setText(gameBoosterIsActive ? "initializing..." : "deactivating...");
        thread = new Thread(gameBoosterIsActive ? boost : stopBoost);
        thread.start();
        boost.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                progressBoost.setVisible(false);
                labelAnim.play();
                anchorPaneGameboosterSwitch.setStyle("-fx-background-color:#83c480;");
                lblGameboostStatus.setText("active");
            }
        });
        stopBoost.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                progressBoost.setVisible(false);
                labelAnim.play();
                anchorPaneGameboosterSwitch.setStyle("-fx-background-color:#c48080;");
                lblGameboostStatus.setText("deactivated");
            }
        });
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

    private Task getDeactivateGameboosterTask() {
        Task<Void> stopTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                gameboost.stopGameboost(resetPowerPlan, resetGPUPrio);
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
                System.out.println("Cleared standby ram");
            }
        });
    }

    @FXML
    private void runSelected(ActionEvent event) {
        if (chkDelInstallersFromDownload.isSelected()) {
            new Thread(tasks.getTaskDeleteInstallerFromDownload()).start();
        }
    }

}
