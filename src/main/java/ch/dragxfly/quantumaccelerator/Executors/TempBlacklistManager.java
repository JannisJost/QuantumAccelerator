package ch.dragxfly.quantumaccelerator.Executors;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author janni
 */
public class TempBlacklistManager {

    private static final String PATH_TO_BLACKLISTTXT = "C:\\Program Files\\QuantumAccelerator\\Blacklists\\tempBlacklist.txt";
    private static final String PATH_TO_BLACKLISTFOLDER = "C:\\Program Files\\QuantumAccelerator\\Blacklists\\";

    private FileReader reader;
    private FileWriter writer;

    public TempBlacklistManager() {
        try {
            reader = new FileReader(PATH_TO_BLACKLISTTXT);
            writer = new FileWriter(PATH_TO_BLACKLISTTXT);
        } catch (IOException ex) {
        }
    }

    public void writeToBlacklistFile(List<String> blacklist) throws IOException {
        createBlacklistFilesAndFolders();
        writer.write("");
        for (String path : blacklist) {
            writer.append(path);
        }
    }

    private void createBlacklistFilesAndFolders() throws IOException {
        File blacklistFolder = new File(PATH_TO_BLACKLISTFOLDER);
        File blacklistTxt = new File(PATH_TO_BLACKLISTTXT);
        if (!blacklistFolder.exists()) {
            blacklistFolder.mkdirs();
        }
        if (!blacklistTxt.exists()) {
            blacklistTxt.createNewFile();
        }
    }

    public List<String> getBlacklist() {
        return null;
    }
}
