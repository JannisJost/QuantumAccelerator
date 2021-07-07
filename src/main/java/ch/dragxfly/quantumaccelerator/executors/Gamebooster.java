package ch.dragxfly.quantumaccelerator.executors;

import shell.PowerShell;
import java.io.File;
import java.io.IOException;
import shell.RegistryEditor;

/**
 *
 * @author jannis
 */
public class Gamebooster {

    private final PowerShell powershell = new PowerShell();
    private final RegistryEditor regedit = new RegistryEditor();

    public void runGameboost() throws InterruptedException, IOException {
        disableSysMain();
        runUltimatePowerPowerShell();
        deactivateBackgroundApps();
        increaseGPUPriotity();
        deactivateWindowsVisualFX();
        stopWindowsSearch();
    }

    public void stopGameboost(boolean isResetPowerPlan, boolean isResetGPUPrio, boolean resetSysMain) throws IOException, InterruptedException {
        if (isResetPowerPlan) {
            runDeactivateUltimatePowerShell();
        }
        //Later only reactivate if user wants to
        reactivateBackgroundApps();
        if (isResetGPUPrio) {
            decreaseGPUPriotityToNormal();
        }
        if (resetSysMain) {
            enableSysMain();
        }
        activateWindowsVisualFX();
        startWindowsSearch();
    }

    private void fpsAdjustPerGame() {

    }

    private void deleteInstallerFromDownloads() {
        try {
            String osDrive = System.getenv("SystemDrive");
            String userName = System.getProperty("user.name");
            File folder = new File(osDrive + "\\Users\\" + userName + "\\Downloads");
            File[] filesInDownload = folder.listFiles();
            for (File file : filesInDownload) {
                if (file.getName().contains("Installer") || file.getName().contains("install") || file.getName().contains("installer") || file.getName().contains("setup") && file.canExecute()) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            System.err.print(e);
        }
    }

    //Disables superfetcher and prefetcher

    private void disableSysMain() {
        powershell.executeCommand("Stop-Service -Force -Name “SysMain”; Set-Service -Name “SysMain” -StartupType Disabled");
    }

    private void enableSysMain() {
        powershell.executeCommand("Start-Service -Force -Name “SysMain”; Set-Service -Name “SysMain” -StartupType Automatic");
    }

    private void runUltimatePowerPowerShell() throws InterruptedException, IOException {
        powershell.runPowershellScript("UltimatePowerModeActivator");
    }

    private void runDeactivateUltimatePowerShell() throws IOException, InterruptedException {
        powershell.runPowershellScript("UltimatePowerModeDeactivator");
    }

    private void deactivateBackgroundApps() {
        regedit.setOrCreateKey("HKCU:\\Software\\Microsoft\\Windows\\CurrentVersion\\BackgroundAccessApplications", "GlobalUserDisabled", "1");
    }

    private void reactivateBackgroundApps() {
        regedit.setOrCreateKey("HKCU:\\Software\\Microsoft\\Windows\\CurrentVersion\\BackgroundAccessApplications", "GlobalUserDisabled", "0");
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

    private void stopWindowsSearch() {
        powershell.executeCommand("Stop-Service -Name WSearch");
    }

    private void startWindowsSearch() {
        powershell.executeCommand("Start-Service -Name WSearch");
    }

}
