package ch.dragxfly.quantumaccelerator.executors.web.Browser;

import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.deleter.FileDeleter;

/**
 *
 * @author jannis
 */
public class Firefox implements Browser {

    private final FileDeleter deleter = new FileDeleter();

    @Override
    public void clearCache() {
        clearBaseCache();
    }

    @Override
    public void clearHistory() {
    }

    private void clearBaseCache() {
        String appdataFolder = System.getenv("APPDATA").replace("Roaming", "");
        String cacheFolder = appdataFolder + "\\Local\\Mozilla\\Firefox\\Profiles\\";
        deleter.deleteAllFilesInDirectory(cacheFolder);
    }

    @Override
    public void clearCookies() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void kill() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
