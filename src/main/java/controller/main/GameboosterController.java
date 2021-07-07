package controller.main;

import ch.dragxfly.quantumaccelerator.customControls.ToggleSwitch;
import ch.dragxfly.quantumaccelerator.tasks.GameboosterTasks;
import ch.dragxfly.quantumaccelerator.executors.Gamebooster;
import ch.dragxfly.quantumaccelerator.style.logo.Logo;
import ch.dragxfly.quantumaccelerator.views.MultilingualView;
import controller.popupwindows.warning.InfoDecisionWindow;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
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
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author jannis
 */
public class GameboosterController extends MultilingualView implements Initializable {

    @FXML
    private AnchorPane anchorPaneGameboosterSwitch;
    @FXML
    private Label lblGameboostStatus;
    @FXML
    private ToggleButton toggleBtnGameboost;
    @FXML
    private Button btnApplyCheckBoxes;
    @FXML
    private Button btnFreeStandbyRAM;
    @FXML
    private CheckBox chkDelInstallersFromDownload;
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
    //non FXML
    private Task task;
    private Logo logo;
    private Thread thread;
    boolean resetPowerPlan;
    private Preferences prefs;
    private Gamebooster gameboost;
    private boolean isResetGPUPrio;
    private boolean isResetSysMain;
    private boolean gameBoosterIsActive;
    private boolean canRunStandByCleaner = true;
    private final ToggleSwitch tglswCPUPrio = new ToggleSwitch();
    private final GameboosterTasks tasks = new GameboosterTasks();
    private final ToggleSwitch tglswResetSysMain = new ToggleSwitch();
    private final ToggleSwitch tglswResetPowerPlan = new ToggleSwitch();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
        tglswResetPowerPlan.setActivated(true);
        tglswCPUPrio.setActivated(true);
        tglswResetSysMain.setActivated(true);
        gridSettingsDeactivateGamebooster.add(tglswResetPowerPlan, 1, 0);
        gridSettingsDeactivateGamebooster.add(tglswCPUPrio, 1, 1);
        gridSettingsDeactivateGamebooster.add(tglswResetSysMain, 1, 2);
        gameBoosterIsActive = toggleBtnGameboost.isSelected();
        gameboost = new Gamebooster();
        prefs = Preferences.userRoot().node(this.getClass().getName());
        gameBoosterIsActive = prefs.getBoolean("GameboosterIsActive", false);
        toggleBtnGameboost.setSelected(gameBoosterIsActive);
        anchorPaneGameboosterSwitch.setStyle(gameBoosterIsActive ? "-fx-background-color:#83c480;" : "-fx-background-color:#c48080;");
        lblGameboostStatus.setText(gameBoosterIsActive ? "active" : "deactivated");
        try {
            logo = new Logo(64);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        logo.setShowing(false);
        vboxGameboosterSwitch.getChildren().add(logo);
    }

    @FXML
    private void activateOrDeactivateGameboost(ActionEvent event) {
        resetPowerPlan = tglswResetPowerPlan.isActivated();
        isResetGPUPrio = tglswCPUPrio.isActivated();
        isResetSysMain = tglswResetSysMain.isActivated();
        gameBoosterIsActive = toggleBtnGameboost.isSelected();
        prefs.putBoolean("GameboosterIsActive", gameBoosterIsActive);
        logo.setShowing(true, true);
        logo.playLoadingAnimation();
        Task boost = getGameboosterTask();
        Task stopBoost = getDeactivateGameboosterTask();
        lblGameboostStatus.setText(gameBoosterIsActive ? "initializing..." : "deactivating...");
        thread = new Thread(gameBoosterIsActive ? boost : stopBoost);
        thread.start();
        boost.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                logo.playLoadingFinishedAnimation();
                anchorPaneGameboosterSwitch.setStyle("-fx-background-color:#83c480;");
                lblGameboostStatus.setText("active");
            }
        });
        stopBoost.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                logo.playLoadingFinishedAnimation();
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

    @FXML
    private void runSelected(ActionEvent event) {
        if (chkDelInstallersFromDownload.isSelected()) {
            Task task = tasks.getTaskDeleteInstallerFromDownload();
            Thread t = new Thread(task);
            t.start();
            task.setOnSucceeded(e -> {
                new InfoDecisionWindow().ShowInfoWindow("Completed all selected");
            });
        }
    }

    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.lang", locale);
        btnApplyCheckBoxes.setText(bundle.getString("btnRun"));
        btnFreeStandbyRAM.setText(bundle.getString("btnFreeStandbyRAM"));
        chkDelInstallersFromDownload.setText(bundle.getString("chkDeleteInstallerDownload"));
        lblOnDeactivateBooster.setText(bundle.getString("lblOnDeactivateBooster"));
        lblResetPowerPlan.setText(bundle.getString("lblResetPowerPlan"));
        lblResetCPUPrio.setText(bundle.getString("lblResetCPUPrio"));
        lblResetSysMain.setText(bundle.getString("lblResetSysMain"));
    }
}
