
package ch.dragxfly.quantumaccelerator.booster;

import java.io.File;
import java.io.IOException;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author janni
 */
public class Booster {

    Runtime rt = Runtime.getRuntime();
    Label state;
    TextFlow log;
    private boolean backgroundApps;
    private boolean powerPlan;
    private boolean deleteTemp;
    private boolean deleteDownloadInstaller;
    private ProgressIndicator progress;
    private double progressSteps;

    public Booster(Label state, TextFlow log, ProgressIndicator progress, boolean backgroundApps, boolean powerPlan, boolean deleteTemp, boolean deleteDownloadInstaller) {
        this.log = log;
        this.state = state;
        this.progress = progress;
        this.backgroundApps = backgroundApps;
        this.powerPlan = powerPlan;
        this.deleteTemp = deleteTemp;
        this.deleteDownloadInstaller = deleteDownloadInstaller;
    }

    public Booster() {
    }

    public void boost() {
        state.setText("Starting boost...");
        log("Starting boost...");
        if (backgroundApps == true) {
            backgroundApps();
        }
        if (powerPlan == true) {
            log("Setting power plan...");
            powerPlan();
        }
        if (deleteTemp == true) {
            log("Deleting temp files...");
            deleteTemp();
        }
        if (deleteDownloadInstaller == true) {
            log("Deleting installers in downloads folder...");        }
    }

    private boolean backgroundApps() {
        return true;
    }

    private boolean powerPlan() {
        try {
            rt.exec(new String[]{"powercfg", "/s", "8c5e7fda-e8bf-4a96-9a85-a6e23a8c635c"});
            return true;
        } catch (IOException e) {
            log("ERROR while trying to set power plan");
            return false;
        }
    }

    private boolean deleteTemp() {
        try {
            String osDrive = System.getenv("SystemDrive");
            File folder = new File(osDrive + "\\Windows\\Temp");
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                file.delete();
                log("Deleted " + file.getName() + " in windows temp folder");
            }
            return true;
        } catch (Exception e) {
            log("ERROR while deleting temp files");
            return false;
        }
    }

    private void log(String logMessage) {
        Text logText = new Text("");
        logText.setText(logMessage);
        log.getChildren().add(logText);
        log.getChildren().add(new Text(System.lineSeparator()));
    }
    private void getProgressSteps(){
        
    }
}
