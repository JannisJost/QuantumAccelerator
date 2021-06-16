package ch.dragxfly.quantumaccelerator.Executors;

import shellscripts.PowerShell;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author janni
 */
public class Gamebooster {

    private final PowerShell powershell = new PowerShell();

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

    public void freeRAM() {
        System.gc();
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
            showJOptionPane("deleted all installers from downloads folder", "Success");
        } catch (Exception e) {
        }
    }

    private void showJOptionPane(String title, String message) {
        JOptionPane.showMessageDialog(null, title, message, JOptionPane.INFORMATION_MESSAGE);

    }
    //Disables superfetcher and prefetcher

    private void disableSysMain() {
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("Stop-Service -Force -Name “SysMain”; Set-Service -Name “SysMain” -StartupType Disabled");
    }

    private void enableSysMain() {
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("Start-Service -Force -Name “SysMain”; Set-Service -Name “SysMain” -StartupType Automatic");
    }

    private void runUltimatePowerPowerShell() throws InterruptedException, IOException {
        powershell.runPowershellScript("UltimatePowerModeActivator");
    }

    private void runDeactivateUltimatePowerShell() throws IOException, InterruptedException {
        powershell.runPowershellScript("UltimatePowerModeDeactivator");
    }

    private void deactivateBackgroundApps() {
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("Reg Add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\BackgroundAccessApplications /v GlobalUserDisabled /t REG_DWORD /d 1 /f");
    }

    private void reactivateBackgroundApps() {
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("Reg Add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\BackgroundAccessApplications /v GlobalUserDisabled /t REG_DWORD /d 0 /f ");
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
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("Stop-Service -Name WSearch");
    }

    private void startWindowsSearch() {
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("Start-Service -Name WSearch");
    }

}
