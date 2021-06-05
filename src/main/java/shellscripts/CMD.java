package shellscripts;

import ch.dragxfly.quantumaccelerator.Executors.errorhandling.ErrorWindow;
import java.io.IOException;

/**
 *
 * @author janni
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

    public void executeCmdCommandCMDVisible(String command) {
        try {
            Process p1 = Runtime.getRuntime().exec("cmd /B start cmd.exe /K \"" + command + "\"");
            p1.waitFor();
        } catch (IOException ex) {
        } catch (InterruptedException ex) {
            new ErrorWindow().showErrorWindow("Could not execute CMD command " + command);
        }
    }
}
