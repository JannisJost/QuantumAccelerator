package shell;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author jannis
 */
public class CommandSecurityHelper {

    public boolean commandIsSave(String command) {
        if (command.toLowerCase().matches("(^)del(\\s)") || command.toLowerCase().matches("(\\s)del(\\s)")) {
            return false;
        } else if (command.toLowerCase().matches("(\\s)rd(\\s)") || command.toLowerCase().matches("(\\s)format(\\s)")) {
            return false;
        } else if (command.toLowerCase().matches("(^)ren(\\s)") || command.toLowerCase().matches("(\\s)ren(\\s)")) {
            return false;
        } else if (command.toLowerCase().matches("(^)remove-item(\\s)") || command.toLowerCase().matches("(\\s)remove-item(\\s)")) {
            return false;
        } else {
            return !(command.toLowerCase().matches("(^)new-partition(\\s)") || command.toLowerCase().matches("new-partition(\\s)"));
        }
    }

    public boolean commandsAreSave(String[] commands) {
        boolean isSave;
        for (int i = 0; i <= commands.length - 1; i++) {
            isSave = commandIsSave(commands[i]);
            if (!isSave) {
                return false;
            }
        }
        return true;
    }

    public boolean psScriptIsSave(String pathToScript) {
        try {
            Scanner reader = new Scanner(new File(pathToScript));
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.toLowerCase().matches("(^)invoke-webrequest(\\s)") || line.toLowerCase().matches("(\\s)invoke-webrequest(\\s)")) {
                    return false;
                }
            }
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println(ex + pathToScript);
        }
        return false;
    }
}
