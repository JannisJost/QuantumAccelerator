package shell;

import ch.dragxfly.quantumaccelerator.executors.errorhandling.ErrorWindow;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.ApplicationFilesHelper;
import java.io.IOException;

/**
 *
 * @author jannis
 */
public class PowerShell {

    private final CommandSecurityHelper securityHelper = new CommandSecurityHelper();

    public void runPowershellScript(String nameOfScript) {
        try {
            if (securityHelper.psScriptIsSave(new ApplicationFilesHelper().getInstallationPath() + "Powershell_Scripts\\" + nameOfScript + ".ps1")) {
                //Gets current working path
                String pathToScript = new ApplicationFilesHelper().getInstallationPath() + "Powershell_Scripts\\" + nameOfScript + ".ps1";
                Runtime runtime = Runtime.getRuntime();
                String fullCommand = "cmd.exe /c powershell.exe " + pathToScript;
                if (securityHelper.commandIsSave(fullCommand)) {
                    //Runs Powershell script to search activate or create powerplan
                    Process processPowershell = runtime.exec(fullCommand);
                    //Waiting for Powershell command to finish
                    processPowershell.waitFor();
                } else {
                    throw new InterruptedException("Command is not safe");
                }
            } else {
                throw new InterruptedException("Script is not safe");
            }
        } catch (IOException | InterruptedException e) {
            new ErrorWindow().showErrorWindow("Could not run " + nameOfScript);
        }
    }

    public void executeCommand(String command) {
        String fullCommand = "powershell.exe " + command;

        if (securityHelper.commandIsSave(fullCommand)) {
            try {
                Runtime.getRuntime().exec(fullCommand);
            } catch (IOException ex) {
                System.err.println("error while executing powershell command");
            }
        } else {
            System.out.println("Command " + command + "is not safe");
        }
    }
}
