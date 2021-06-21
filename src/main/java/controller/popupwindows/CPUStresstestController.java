package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.Hardware.Benchmark.CPUBenchmark;
import ch.dragxfly.quantumaccelerator.ViewManager.ThemeableWindow;
import controller.popupwindows.warning.InfoWindow;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class CPUStresstestController extends ThemeableWindow implements Initializable {

    @FXML
    private CheckBox chkStopIfTempHigh;
    @FXML
    private Button btnRunStressTest;
    @FXML
    private Label lblKeepLoadAt;
    @FXML
    private Slider sliderKeepLoadAt;
    @FXML
    private Button btnClose;
    @FXML
    private VBox vboxConfigure;
    @FXML
    private VBox vboxRunning;
    @FXML
    private Button btnStopCPUStressTest;
    @FXML
    private Slider sliderMaxTemp;
    @FXML
    private Label lblThreads;
    @FXML
    private Label lblCPUTemp;
    @FXML
    private Label lblStressTestStatus;
    @FXML
    private Label lblFanSpeed;
    @FXML
    private Label lblWarning;
    //non FXML
    private double xOffset = 0;
    private double yOffset = 0;
    private CPUBenchmark benchmark;
    private String keepLoadAt;
    private String stopIfTempReaches;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
        keepLoadAt = lblKeepLoadAt.getText();
        stopIfTempReaches = chkStopIfTempHigh.getText();
        sliderMaxTemp.disableProperty().bind(chkStopIfTempHigh.selectedProperty().not());
        //binds the MaxTemp slider to its slider
        sliderMaxTemp.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                chkStopIfTempHigh.setText(stopIfTempReaches + " " + String.format("%.0f", newValue) + "℃/" + String.format("%.2f", newValue.doubleValue() * 1.8 + 32) + "℉");
            }
        });
        //Binds the keeploadat label to its slider
        sliderKeepLoadAt.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lblKeepLoadAt.setText(keepLoadAt + " ~" + Math.round(newValue.doubleValue() / 10.0) * 10 + "%");
            }
        });
    }

    @FXML
    private void runStressTest(ActionEvent event) {
        vboxConfigure.setVisible(false);
        vboxRunning.setVisible(true);
        benchmark = new CPUBenchmark(sliderKeepLoadAt.getValue() / 100, sliderMaxTemp.getValue(), chkStopIfTempHigh.isSelected(), this);
        benchmark.runCPUBenchmark();
    }

    @Override
    public void setTheme() {
        try {
            super.getPref().sync();
        } catch (BackingStoreException e) {

        }
        Scene scene = btnRunStressTest.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(ThemeableWindow.getCURRENTTHEME(), ""));
        scene.getStylesheets().add("/styles/CPUStresstest.css");
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        if (benchmark != null) {
            benchmark.stopLoad();
        }
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void moveWindowSecond(MouseEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.setX(event.getScreenX() + xOffset);
        currentStage.setY(event.getScreenY() + yOffset);
    }

    @FXML
    private void moveWindow(MouseEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        xOffset = currentStage.getX() - event.getScreenX();
        yOffset = currentStage.getY() - event.getScreenY();
    }

    @FXML
    private void stopStressTest(ActionEvent event) {
        benchmark.stopLoad();
        vboxConfigure.setVisible(true);
        vboxRunning.setVisible(false);
    }

    public void adjustStressTestStats(int threads, double cpuTemp) {
        Platform.runLater(() -> {
            lblThreads.setText("Active threads: " + threads);
            lblCPUTemp.setText("CPU temp: " + cpuTemp + "℃");
        });
    }

    @FXML
    private void warningMightOverheat(ActionEvent event) {
        if (!chkStopIfTempHigh.isSelected()) {
            boolean isDisable = !new InfoWindow().ShowInfoWindow("In very rare cases disabling this option might lead to overheating. If your system has overheating issues, you might wanna keep this option enabled");
            chkStopIfTempHigh.setSelected(isDisable);
        }
    }

    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.popup", locale);
        chkStopIfTempHigh.setText(bundle.getString("chkStopIfTempHigh"));
        btnRunStressTest.setText(bundle.getString("btnRunStressTest"));
        lblKeepLoadAt.setText(bundle.getString("lblKeepLoadAt"));
        btnStopCPUStressTest.setText(bundle.getString("btnStopCPUStressTest"));
        lblWarning.setText(bundle.getString("lblWarning"));
    }
}
