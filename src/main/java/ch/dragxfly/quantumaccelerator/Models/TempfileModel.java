package ch.dragxfly.quantumaccelerator.Models;

import ch.dragxfly.quantumaccelerator.Executors.TempfilesBlacklistManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author janni
 */
public class TempfileModel {

    private final List<String> tempFiles = new ArrayList<>();
    private List<String> accessDenied = new ArrayList<>();

    public void setTempFiles(List<String> tempFiles) {
        for (String path : tempFiles) {
            this.tempFiles.add(path);
        }
        String user = System.getProperty("user.home");
        this.tempFiles.add(user + "\\AppData\\Local\\Temp");
    }

    /**
     *
     * @return list without the blacklisted folders
     */
    public List<String> getTempFilesList() {
        return new TempfilesBlacklistManager().removeBlacklisted((ArrayList<String>) tempFiles);
    }

    public List<String> getAccessDenied() {
        return accessDenied;
    }

    public void setAccessDenied(List<String> accessDenied) {
        this.accessDenied = accessDenied;
    }
}
