package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.CustomControls.ToggleSwitch;
import ch.dragxfly.quantumaccelerator.ViewManager.ThemeableWindow;
import ch.dragxfly.quantumaccelerator.Hardware.HardwareObserver;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class SystemMonitorController extends ThemeableWindow implements Initializable, Observer {

    private final List<Pair<Double, String>> cpuUsageHistory = new LinkedList<>();
    private final List<Pair<Double, String>> cpuTempHistory = new LinkedList<>();

    private final List<Double> memoryUsageHistory = new ArrayList<>();
    @FXML
    private Button btnClose;
    @FXML
    private LineChart<String, Double> lineChartCPUUsage;
    private HardwareObserver hardware;
    private Pair<Double, String> cpuLoadPair;
    private Pair<Double, String> cpuTempPair;
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean alwaysOnTop = false;
    @FXML
    private Button btnAlwaysOnTop;
    @FXML
    private HBox hboxTopBar;
    private final ToggleSwitch tglswMeasureCPUTemp = new ToggleSwitch();
    private BooleanProperty showCPUTemp = new SimpleBooleanProperty(false);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tglswMeasureCPUTemp.setActivated(true);
        hboxTopBar.getChildren().add(0, tglswMeasureCPUTemp);
        showCPUTemp.bindBidirectional(tglswMeasureCPUTemp.isSwitchedOn());
        showCPUTemp.addListener(event -> {
            changeShowCPUTemp();
        });
        hboxTopBar.getChildren().add(0, new Label("Measure CPU temp"));
    }

    @Override
    public void update(Observable o, Object arg) {
        double cpuUsage = hardware.getCpuUsage() * 100;
        double cpuTemp = hardware.getCPUTemp();
        //Tell hardwareObserver to measure CPU temp at first run
        if (cpuTempHistory.isEmpty()) {
            hardware.setMeasureCPUTemp(true);
        }
        Date now = new Date();
        String time = Integer.toString(now.getSeconds()) + "\"";
        cpuLoadPair = new Pair(cpuUsage, time);
        cpuTempPair = new Pair(cpuTemp, time);
        cpuUsageHistory.add(cpuLoadPair);
        cpuTempHistory.add(cpuTempPair);
        shortenLists();
        Platform.runLater(() -> {
            fillChart();
        });
    }

    @Override
    public void setTheme() {
        try {
            super.getPref().sync();
        } catch (BackingStoreException e) {

        }
        Scene scene = btnClose.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(ThemeableWindow.getCURRENTTHEME(), ""));
    }

    public void setHardware(HardwareObserver hardware) {
        this.hardware = hardware;
    }

    private void fillChart() {
        lineChartCPUUsage.getData().clear();
        XYChart.Series seriesLoad = new XYChart.Series();
        XYChart.Series seriesTemp = new XYChart.Series();
        for (Pair<Double, String> value : cpuUsageHistory) {
            seriesLoad.getData().add(new XYChart.Data(value.getValue(), value.getKey()));
        }
        for (Pair<Double, String> value : cpuTempHistory) {
            if (value.getKey() != 0) {
                seriesTemp.getData().add(new XYChart.Data(value.getValue(), value.getKey()));
            }
        }
        lineChartCPUUsage.getData().add(seriesLoad);
        if (showCPUTemp.getValue()) {
            lineChartCPUUsage.getData().add(seriesTemp);
        }
    }

    private void shortenLists() {
        if (cpuUsageHistory.size() == 15) {
            cpuUsageHistory.remove(0);
        }
        if (cpuTempHistory.size() == 15) {
            cpuTempHistory.remove(0);
        }
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        cpuTempHistory.clear();
        cpuUsageHistory.clear();
        hardware.setMeasureCPUTemp(false);
        hardware.deleteObserver(this);
        Stage s = (Stage) btnClose.getScene().getWindow();
        s.close();
    }

    @FXML
    private void moveWindowSecond(MouseEvent event) {
        if (!alwaysOnTop) {
            Stage currentStage = (Stage) btnClose.getScene().getWindow();
            currentStage.setX(event.getScreenX() + xOffset);
            currentStage.setY(event.getScreenY() + yOffset);
        }
    }

    @FXML
    private void moveWindow(MouseEvent event) {
        if (!alwaysOnTop) {
            Stage currentStage = (Stage) btnClose.getScene().getWindow();
            xOffset = currentStage.getX() - event.getScreenX();
            yOffset = currentStage.getY() - event.getScreenY();
        }

    }

    @FXML
    private void changeAlwaysOnTop(ActionEvent event) {
        lineChartCPUUsage.mouseTransparentProperty().set(true);
        Scene scene = btnClose.getScene();
        Stage s = (Stage) btnClose.getScene().getWindow();
        scene.getStylesheets().clear();
        alwaysOnTop = !alwaysOnTop;
        s.setAlwaysOnTop(alwaysOnTop);
        if (alwaysOnTop) {
            s.setX(0);
            s.setY(0);
            scene.getStylesheets().add("/styles/transparent_SystemMonitor.css");
            btnAlwaysOnTop.setText("↩");
        } else {
            s.centerOnScreen();
            setTheme();
            btnAlwaysOnTop.setText("Toggle always on top");
        }
    }

    private void changeShowCPUTemp() {
        if (showCPUTemp.getValue()) {
            hardware.setMeasureCPUTemp(true);
        } else {
            hardware.setMeasureCPUTemp(false);
        }
        cpuTempHistory.clear();
    }
}
