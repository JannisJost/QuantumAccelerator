package shellscripts;

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
            System.out.println("Error while executing CMD");
        }

    }

    public void executeCmdCommandCMDVisible(String command) {
        try {
            Process p1 = Runtime.getRuntime().exec("cmd /B start cmd.exe /K \"" + command + "\"");
            p1.waitFor();
        } catch (IOException ex) {
        } catch (InterruptedException ex) {
            System.err.println("Error while running CMD");
        }
    }
}
