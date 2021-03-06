package ch.dragxfly.quantumaccelerator.models;

import ch.dragxfly.quantumaccelerator.executors.TempfilesBlacklistManager;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jannis
 */
public class TempfileModel {
    
    private final LinkedList<String> tempFiles = new LinkedList<>();
    private List<String> accessDenied = new LinkedList<>();
    
    public void setTempFiles(List<String> tempFiles) {
        this.tempFiles.addAll(tempFiles);
        String user = System.getProperty("user.home");
        this.tempFiles.add(user + "\\AppData\\Local\\Temp");
    }

    /**
     *
     * @return list without the blacklisted folders
     */
    public List<String> getTempFilesList() {
        return new TempfilesBlacklistManager().removeBlacklisted(tempFiles);
    }
    /**
     * 
     * @return list of files/folders for which access was denied
     */
    public List<String> getAccessDeniedFolders() {
        return accessDenied;
    }
    
    public void setAccessDeniedFolders(List<String> accessDenied) {
        this.accessDenied = accessDenied;
    }
    public void clear(){
        tempFiles.clear();
        accessDenied.clear();
    }
}
