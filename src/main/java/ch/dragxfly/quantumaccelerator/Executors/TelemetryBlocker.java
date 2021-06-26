package ch.dragxfly.quantumaccelerator.Executors;

import controller.popupwindows.TelemetryOptionsController;
import javafx.concurrent.Task;
import shellscripts.PowerShell;
import shellscripts.RegistryEditor;

/**
 *
 * @author janni
 */
public class TelemetryBlocker {

    private Task telemetryBlockerTask;
    private PowerShell ps = new PowerShell();
    private RegistryEditor regEdit = new RegistryEditor();

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
     * @param isEnabledSendMalewareSamples
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
        regEdit.setOrCreateKey("HKLM:\\SOFTWARE\\Policies\\Microsoft\\MRT", "DontReportInfectionInformation", (isEnabledMRT ? "1" : "0"));
    }

    private void setCEIP(boolean isEnabledCEIP) {
        ps.executeCommand("Get-ScheduledTask -TaskPath \"\\Microsoft\\Windows\\Customer Experience Improvement Program\\\" | " + (isEnabledCEIP ? "Enable-ScheduledTask" : "Disable-ScheduledTask"));
    }

    private void setTrackingService(boolean isEnabledTrackingService) {
        ps.executeCommand("Set-Service -Name DiagTrack -StartupType "
                + (isEnabledTrackingService ? "Automatic" : "Disable"));
    }

    private void setPushService(boolean disablePushService) {
        ps.executeCommand("Set-Service -Name dmwappushservice -StartupType "
                + (disablePushService ? "Disable" : "Automatic"));
    }

    private void setWifiSense(boolean isEnabledWifiSense) {
        regEdit.setOrCreateKey("HKLM:\\SOFTWARE\\Microsoft\\WcmSvc\\wifinetworkmanager\\config", "AutoConnectAllowedOEM", (isEnabledWifiSense ? "1" : "0"));
    }

    private void setSendMalewareSamples(boolean isEnabledSendMalewareSamples) {
        regEdit.setOrCreateKey("HKLM:\\Software\\Policies\\Microsoft\\Windows Defender\\Spynet", "SubmitSamplesConsent", (isEnabledSendMalewareSamples ? "1" : "2"));
    }
}
