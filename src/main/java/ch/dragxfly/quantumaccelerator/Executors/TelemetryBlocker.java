package ch.dragxfly.quantumaccelerator.Executors;

import com.profesorfalken.jpowershell.PowerShell;
import javafx.concurrent.Task;

/**
 *
 * @author janni
 */
public class TelemetryBlocker {

    private Task telemetryBlockerTask;
    private PowerShell ps;

    public void blockTelemetry(boolean disableMRT, boolean disableCEIP, boolean disableTrackingService, boolean disablePushService) {

    }

    private void setMRT(boolean disableMRT) {
        ps.executeCommand("New-ItemProperty -Path \"HKLM:\\Software\\Policies\\Microsoft\\MRT\" -Name DontReportInfectionInformation -Value " + (disableMRT ? "0" : "1") + " -Force");
    }

    private void setCEIP(boolean disableCEIP) {
        ps.executeCommand("Get-ScheduledTask -TaskPath \"\\Microsoft\\Windows\\Customer Experience Improvement Program\\\" | " + (disableCEIP ? "Disable-ScheduledTask" : "Enable-ScheduledTask"));
    }

    private void setTrackingService(boolean disableTrackingService) {
        ps.executeCommand("Stop-Service -Name DiagTrack");
        ps.executeCommand("Set-Service -Name DiagTrack -StartupType " + (disableTrackingService ? "Disable" : "Automatic"));
    }

    private void setPushService(boolean disablePushService) {
        ps.executeCommand("Stop-Service -Name dmwappushservice");
        ps.executeCommand("Set-Service -Name dmwappushservice -StartupType " + (disablePushService ? "Disable" : "Automatic"));
    }

}
