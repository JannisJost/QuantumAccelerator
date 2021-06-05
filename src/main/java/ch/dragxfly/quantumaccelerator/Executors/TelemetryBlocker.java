package ch.dragxfly.quantumaccelerator.Executors;

import controller.popupwindows.TelemetryOptionsController;
import javafx.concurrent.Task;

/**
 *
 * @author janni
 */
public class TelemetryBlocker {

    private Task telemetryBlockerTask;

    /**
     *
     * @param invocator invocator of this instance
     * @param isDisableMRT defines wether to activate/deactivate "MRT"
     * @param isDisableCEIP defines wether to activate/deactivate "CEIP"
     * @param isDisableTrackingService defines wether to activate/deactivate
     * "Tracking Service"
     * @param isDisablePushService defines wether to activate/deactivate "Push
     * Services"
     */
    public void blockTelemetry(TelemetryOptionsController invocator, boolean isDisableMRT, boolean isDisableCEIP, boolean isDisableTrackingService, boolean isDisablePushService) {
        telemetryBlockerTask = new Task() {
            @Override
            protected Object call() throws Exception {
                setMRT(isDisableMRT);
                setCEIP(isDisableCEIP);
                setTrackingService(isDisableTrackingService);
                setPushService(isDisablePushService);
                return null;
            }
        };
        telemetryBlockerTask.setOnSucceeded(event -> {
            invocator.setApplyingState(false);
        });
        new Thread(telemetryBlockerTask).start();
    }

    private void setMRT(boolean disableMRT) {
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("New-ItemProperty -Path \"HKLM:\\Software\\Policies\\Microsoft\\MRT\" -Name DontReportInfectionInformation -Value " + (disableMRT ? "0" : "1") + " -Force");
    }

    private void setCEIP(boolean disableCEIP) {
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("Get-ScheduledTask -TaskPath \"\\Microsoft\\Windows\\Customer Experience Improvement Program\\\" | " + (disableCEIP ? "Disable-ScheduledTask" : "Enable-ScheduledTask"));
    }

    private void setTrackingService(boolean disableTrackingService) {
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("Stop-Service -Name DiagTrack");
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("Set-Service -Name DiagTrack -StartupType "
                + (disableTrackingService ? "Disable" : "Automatic"));
    }

    private void setPushService(boolean disablePushService) {
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("Stop-Service -Name dmwappushservice");
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("Set-Service -Name dmwappushservice -StartupType "
                + (disablePushService ? "Disable" : "Automatic"));
    }

}
