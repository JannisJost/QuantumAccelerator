package shellscripts;

import java.io.IOException;

/**
 *
 * @author janni
 */
public class PowerShell {

    public void runPowershellScript(String nameOfScript) {
        try {
            //Gets current working path
            String powershellScriptDirectorie = "@" + nameOfScript + ".ps1";
            Runtime runtime = Runtime.getRuntime();
            //Runs Powershell script to search activate or create powerplan
            Process processPowershell = runtime.exec("cmd.exe /c powershell.exe " + powershellScriptDirectorie);
            //Waiting for Powershell command to finish
            processPowershell.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Not able to run PowerShellScript " + nameOfScript);
        }

    }
}
