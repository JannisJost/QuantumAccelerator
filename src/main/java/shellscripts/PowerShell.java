package shellscripts;

import ch.dragxfly.quantumaccelerator.Executors.errorhandling.ErrorWindow;
import java.io.IOException;

/**
 *
 * @author janni
 */
public class PowerShell {

    public void runPowershellScript(String nameOfScript) {
        try {
            //Gets current working path
            String pathToScript = "@" + nameOfScript + ".ps1";
            Runtime runtime = Runtime.getRuntime();
            //Runs Powershell script to search activate or create powerplan
            Process processPowershell = runtime.exec("cmd.exe /c powershell.exe " + pathToScript);
            //Waiting for Powershell command to finish
            processPowershell.waitFor();
        } catch (IOException | InterruptedException e) {
            new ErrorWindow().showErrorWindow("Could not run " + nameOfScript);
        }

    }
}
