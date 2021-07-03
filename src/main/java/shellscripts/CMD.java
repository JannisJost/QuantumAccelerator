package shellscripts;

import ch.dragxfly.quantumaccelerator.Executors.errorhandling.ErrorWindow;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author jannis
 */
public class CMD {

    public void executeCmdCommand(String command) {
        try {
            Process p1 = Runtime.getRuntime().exec(command);
            p1.waitFor();
        } catch (InterruptedException | IOException ex) {
            new ErrorWindow().showErrorWindow("Could not execute CMD command " + command);
        }
    }

    public void executeCmdCommands(String[] commands) {
        try {
            ProcessBuilder builder = new ProcessBuilder(commands);
            Process p1 = builder.start();
            p1.waitFor();
        } catch (InterruptedException | IOException ex) {
            new ErrorWindow().showErrorWindow("Could not execute CMD commands");
        }
    }

    public void executeCmdCommandCMDVisible(String command) {
        try {
            String fullCommand = "cmd /B start cmd.exe /K \"" + command + "\"";
            Process p1 = Runtime.getRuntime().exec(fullCommand);
            p1.waitFor();
        } catch (IOException ex) {
        } catch (InterruptedException ex) {
            new ErrorWindow().showErrorWindow("Could not execute CMD command " + command);
        }
    }

    public String execCommandReadOutput(String command) {
        Process p1;
        try {
            String fullCommand = "cmd /B start cmd.exe /K \"" + command + "\"";
            p1 = Runtime.getRuntime().exec(fullCommand);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p1.getInputStream()));
            String line = "";
            line = reader.readLine();
            return line;
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }
}
