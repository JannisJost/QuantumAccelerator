package shell;

import ch.dragxfly.quantumaccelerator.executors.errorhandling.ErrorWindow;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author jannis
 */
public class CMD {

    private final CommandSecurityHelper securityHelper = new CommandSecurityHelper();

    public void executeCommand(String command) {
        try {
            if (securityHelper.commandIsSave(command)) {
                Process p1 = Runtime.getRuntime().exec(command);
                p1.waitFor();
            } else {
                System.out.println("command " + command + " is not save");
            }

        } catch (InterruptedException | IOException ex) {
            new ErrorWindow().showErrorWindow("Could not execute CMD command " + command);
        }
    }

    public void executeCommands(String[] commands) {
        try {
            if (securityHelper.commandsAreSave(commands)) {
                ProcessBuilder builder = new ProcessBuilder(commands);
                Process p1 = builder.start();
                p1.waitFor();
            } else {
                throw new InterruptedException("Commands are not save");
            }
        } catch (InterruptedException | IOException ex) {
            new ErrorWindow().showErrorWindow("Could not execute CMD commands");
        }
    }

    public String execCommandReadOutput(String command) {
        Process p1;
        try {
            if (securityHelper.commandIsSave(command)) {
                p1 = Runtime.getRuntime().exec(command);
                BufferedReader reader = new BufferedReader(new InputStreamReader(p1.getInputStream()));
                String output = "";
                String line;
                while ((line = reader.readLine()) != null) {
                    output += (line + "\n");
                }
                reader.close();
                return output;
            } else {
                System.out.println("Command " + command + " is not save");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }
}
