package ch.dragxfly.quantumaccelerator.Executors;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author janni
 */
public class TempBlacklistManager {

    public void writeToBlacklistFile(List<String> blacklist) throws IOException {
        String pathToBlacklistFile = "C:\\Program Files\\QuantumAccelerator\\Blacklists\\tempBlacklist.txt";
        FileWriter writer = new FileWriter(pathToBlacklistFile);
        writer.
    }
}
