package shellscripts;

import ch.dragxfly.quantumaccelerator.Executors.errorhandling.ErrorWindow;
import java.io.IOException;

/**
 *
 * @author jannis
 */
public class PowerShell {

    public void runPowershellScript(String nameOfScript) {
        try {
            //Gets current working path
            String pathToScript = "@" + nameOfScript + ".ps1";
            Runtime runtime = Runtime.getRuntime();
            String fullCommand = "cmd.exe /c powershell.exe " + pathToScript;
            //Runs Powershell script to search activate or create powerplan
            Process processPowershell = runtime.exec(fullCommand);
            //Waiting for Powershell command to finish
            processPowershell.waitFor();
        } catch (IOException | InterruptedException e) {
            new ErrorWindow().showErrorWindow("Could not run " + nameOfScript);
        }
    }

    public void executeCommand(String command) {
        String fullCommand = "powershell.exe " + command;
        try {
            Runtime.getRuntime().exec(fullCommand);
        } catch (IOException ex) {
            System.err.println("error while executing powershell command");
        }
    }
}
