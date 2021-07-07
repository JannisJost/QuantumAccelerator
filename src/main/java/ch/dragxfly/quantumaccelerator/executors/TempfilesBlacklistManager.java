package ch.dragxfly.quantumaccelerator.executors;

import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.ApplicationFilesHelper;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.creator.FolderCreator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jannis
 */
public class TempfilesBlacklistManager {

    private ApplicationFilesHelper fileHelper = new ApplicationFilesHelper();
    private String pathToBlacklistTxt = "";
    private String pathToBlacklistFolder = "C:\\Program Files\\QuantumAccelerator\\Blacklists\\";

    private FileWriter writer;
    private FileReader fr;
    private Scanner scanner;

    /**
     * Sets the reader and writer on invocation
     *
     */
    public TempfilesBlacklistManager() {
        pathToBlacklistFolder = fileHelper.getInstallationPath() + "\\Blacklists\\";
        pathToBlacklistTxt = fileHelper.getInstallationPath() + "\\Blacklists\\tempBlacklist.txt";
        try {
            createBlacklistFilesAndFolders();
            fr = new FileReader(pathToBlacklistTxt);
            scanner = new Scanner(fr);
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
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
        writer = new FileWriter(pathToBlacklistTxt);
        for (String path : blacklist) {
            writer.append(path + "\n");
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
        createBlacklistFilesAndFolders();
        List<String> blacklistedPaths = new LinkedList<>();
        while (scanner.hasNextLine()) {
            blacklistedPaths.add(scanner.nextLine());
        }
        return blacklistedPaths;
    }

    /**
     * Creates the blacklisted file and its parent folders
     *
     * @throws IOException
     */
    private void createBlacklistFilesAndFolders() throws IOException {
        File blacklistTxt = new File(pathToBlacklistTxt);
        new FolderCreator().createFolder(pathToBlacklistFolder);
        if (!blacklistTxt.exists()) {
            blacklistTxt.createNewFile();
        }
    }

    /**
     *
     * @param listFolders unfiltered list of folders including blacklisted
     * folders
     * @return list without blacklisted folders
     */
    public LinkedList<String> removeBlacklisted(LinkedList<String> listFolders) {
        try {
            listFolders.removeAll(getBlacklist());
        } catch (IOException ex) {
            System.err.print("Could not remove blacklisted folders " + ex);
        }
        return listFolders;
    }
}
