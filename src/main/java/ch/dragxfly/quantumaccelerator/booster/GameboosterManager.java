package ch.dragxfly.quantumaccelerator.booster;

import shellscripts.PowerShell;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author janni
 */
public class GameboosterManager {

    private final PowerShell powershell = new PowerShell();

    public void runGameboost() throws InterruptedException, IOException {
        runUltimatePowerPowerShell();
        deactivateBackgroundApps();
        increaseGPUPriotity();
        deactivateWindowsVisualFX();
    }

    public void stopGameboost(boolean resetPowerPlan, boolean resetGPUPrio) throws IOException, InterruptedException {
        if (resetPowerPlan) {
            runDeactivateUltimatePowerShell();
        }
        //Later only reactivate if user wants to
        reactivateBackgroundApps();
        if (resetGPUPrio) {
            decreaseGPUPriotityToNormal();
        }
        activateWindowsVisualFX();
    }

    public void killUnnecessaryBackgroundProcesses() {
        try {
            String dir = System.getProperty("user.dir");
            dir = dir.replaceAll("\\\\", "/");
            String batchFileLocation = dir + "/batch_files/closeBackgroundProcesses.bat";
            Process p = Runtime.getRuntime().exec(batchFileLocation);
            p.waitFor();
            showJOptionPane("killed background processes", "Success");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void freeRAM() {
        System.gc();
    }

    private void fpsAdjustPerGame() {

    }

    private void deleteInstallerFromDownloads() {
        File[] installerFiles;
        try {
            String osDrive = System.getenv("SystemDrive");
            String userName = System.getProperty("user.name");
            File folder = new File(osDrive + "\\Users\\" + userName + "\\Downloads");
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                if (file.getName().contains("Installer") || file.getName().contains("installer") || file.getName().contains("setup") && file.canExecute()) {
                    file.delete();
                }
            }
            showJOptionPane("deleted all installers from downloads folder", "Success");
        } catch (Exception e) {
        }
    }

    private void showJOptionPane(String title, String message) {
        JOptionPane.showMessageDialog(null, title, message, JOptionPane.INFORMATION_MESSAGE);

    }

    private void runUltimatePowerPowerShell() throws InterruptedException, IOException {
        powershell.runPowershellScript("UltimatePowerModeActivator");
    }

    private void runDeactivateUltimatePowerShell() throws IOException, InterruptedException {
        powershell.runPowershellScript("UltimatePowerModeDeactivator");
    }

    private void deactivateBackgroundApps() {
        powershell.runPowershellScript("DeactivateBackgroundApps");
    }

    private void reactivateBackgroundApps() {
        powershell.runPowershellScript("ReactivateBackgroundApps");
    }

    private void increaseGPUPriotity() {
        powershell.runPowershellScript("IncreaseGPUPriority");
    }

    private void decreaseGPUPriotityToNormal() {
        powershell.runPowershellScript("NormalGPUPriority");
    }

    private void deactivateWindowsVisualFX() {
        powershell.runPowershellScript("DisableWindowsVisualFX");
    }

    private void activateWindowsVisualFX() {
        powershell.runPowershellScript("EnableWindowsVisualFX");
    }

}
