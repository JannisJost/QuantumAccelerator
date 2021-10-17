package shell;

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
            String pathToScript = new ApplicationFilesHelper().getInstallationPath() + "Powershell_Scripts\\" + nameOfScript + ".ps1";
            if (securityHelper.psScriptIsSave(pathToScript)) {
                //Gets current working path
                Runtime runtime = Runtime.getRuntime();
                String completeCommand = "powershell.exe -File " + "\"" + pathToScript + "\"";
                System.out.println(completeCommand);
                if (securityHelper.commandIsSave(completeCommand)) {
                    //Runs Powershell script to search activate or create powerplan
                    Process processPowershell = runtime.exec(completeCommand);
                    //Waiting for Powershell command to finish
                    processPowershell.waitFor();
                } else {
                    throw new InterruptedException("Command is not safe");
                }
            } else {
                throw new InterruptedException("Script is not safe");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Could not run " + nameOfScript);
        }
    }

    public void executeCommand(String command) {
        String completeCommand = "powershell.exe " + command;

        if (securityHelper.commandIsSave(completeCommand)) {
            try {
                Runtime.getRuntime().exec(completeCommand);
            } catch (IOException ex) {
                System.err.println("error while executing powershell command");
            }
        } else {
            System.out.println("Command " + command + "is not safe");
        }
    }
}
