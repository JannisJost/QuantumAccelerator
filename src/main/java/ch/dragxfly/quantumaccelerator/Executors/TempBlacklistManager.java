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

    /**
     * Sets the reader and writer on invocation
     */
    public TempBlacklistManager() {
        try {
            reader = new FileReader(PATH_TO_BLACKLISTTXT);
            writer = new FileWriter(PATH_TO_BLACKLISTTXT);
        } catch (IOException ex) {
        }
    }

    /**
     * Writes the blacklisted paths to a txt file (currently always overwrites
     * it will be fixed later)
     *
     * @param blacklist List of paths to the blacklisted folders
     * @throws IOException
     */
    public void writeToBlacklistFile(List<String> blacklist) throws IOException {
        createBlacklistFilesAndFolders();
        writer.write("");
        for (String path : blacklist) {
            writer.append(path);
        }
        writer.close();
    }

    /**
     * Reads the blacklisted paths from the txt file
     *
     * @return list of blacklisted path
     * @throws IOException
     */
    public List<String> getBlacklist() throws IOException {
        reader.read();
        return null;
    }
/**
 * Creates the blacklisted file and its parent folders 
 * @throws IOException 
 */
    private void createBlacklistFilesAndFolders() throws IOException {
        File blacklistFolder = new File(PATH_TO_BLACKLISTFOLDER);
        File blacklistTxt = new File(PATH_TO_BLACKLISTTXT);
        if (!blacklistFolder.exists()) {
            blacklistFolder.mkdirs();
        } else if (!blacklistTxt.exists()) {
            blacklistTxt.createNewFile();
        }
    }
}
