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
     * @param isEnabledMRT defines wether to activate/deactivate "MRT"
     * @param isEnabledCEIP defines wether to activate/deactivate "CEIP"
     * @param isEnabledTrackingService defines wether to activate/deactivate
     * "Tracking Service"
     * @param isEnabledPushService defines wether to activate/deactivate "Push
     * Services"
     * @param isEnabledWifiSense defines wether to activate/deactivate "Wifi
     * Sense"
     */
    public void blockTelemetry(TelemetryOptionsController invocator, boolean isEnabledMRT, boolean isEnabledCEIP,
            boolean isEnabledTrackingService, boolean isEnabledPushService, boolean isEnabledWifiSense, boolean isEnabledSendMalewareSamples) {
        telemetryBlockerTask = new Task() {
            @Override
            protected Object call() throws Exception {
                setMRT(isEnabledMRT);
                setCEIP(isEnabledCEIP);
                setTrackingService(isEnabledTrackingService);
                setPushService(isEnabledPushService);
                setWifiSense(isEnabledWifiSense);
                setSendMalewareSamples(isEnabledSendMalewareSamples);
                return null;
            }
        };
        telemetryBlockerTask.setOnSucceeded(event -> {
            invocator.setApplyingState(false);
        });
        new Thread(telemetryBlockerTask).start();
    }

    private void setMRT(boolean isEnabledMRT) {
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("New-ItemProperty -Path \"HKLM:\\Software\\Policies\\Microsoft\\MRT\" -Name DontReportInfectionInformation -Value " + (isEnabledMRT ? "1" : "0") + " -Force");
    }

    private void setCEIP(boolean isEnabledCEIP) {
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("Get-ScheduledTask -TaskPath \"\\Microsoft\\Windows\\Customer Experience Improvement Program\\\" | " + (isEnabledCEIP ? "Enable-ScheduledTask" : "Disable-ScheduledTask"));
    }

    private void setTrackingService(boolean isEnabledTrackingService) {
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("Set-Service -Name DiagTrack -StartupType "
                + (isEnabledTrackingService ? "Automatic" : "Disable"));
    }

    private void setPushService(boolean disablePushService) {
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("Set-Service -Name dmwappushservice -StartupType "
                + (disablePushService ? "Disable" : "Automatic"));
    }

    private void setWifiSense(boolean isEnabledWifiSense) {
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("New-ItemProperty -Path \"HKLM:\\SOFTWARE\\Microsoft\\WcmSvc\\wifinetworkmanager\\config\" -Name AutoConnectAllowedOEM -Value " + (isEnabledWifiSense ? "1" : "0") + "-Force");
    }

    private void setSendMalewareSamples(boolean isEnabledSendMalewareSamples) {
        com.profesorfalken.jpowershell.PowerShell.executeSingleCommand("New-ItemProperty -Path \"HKLM:\\Software\\Policies\\Microsoft\\Windows Defender\\Spynet\" -Name SubmitSamplesConsent -Value " + (isEnabledSendMalewareSamples ? "1" : "2") + " -Force");
    }
}
