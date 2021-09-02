package ch.dragxfly.quantumaccelerator.executors;

import shell.PowerShell;
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

    public void setPerfomancePowerPlan() {
        powershell.runPowershellScript("UltimatePowerModeActivator");
    }

    public void setStandardPowerPlan() {
        powershell.runPowershellScript("UltimatePowerModeDeactivator");
    }

    public void setEcoPowerPlan() {
        powershell.runPowershellScript("EcoPowerPlanActivator");
    }
}
