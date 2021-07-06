package ch.dragxfly.quantumaccelerator.executors.web.Browser;

import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.deleter.FileDeleter;
import shell.CMD;

/**
 *
 * @author jannis
 */
public class Opera implements Browser {

    private final FileDeleter deleter = new FileDeleter();

    @Override
    public void clearCache() {
        String appdataFolder = System.getenv("APPDATA").replace("Roaming", "");
        String cacheFolder = appdataFolder + "\\Local\\Opera Software\\Opera Stable\\Cache";
        deleter.deleteAllFilesInDirectory(cacheFolder);
    }

    @Override
    public void clearHistory() {
    }

    @Override
    public void clearCookies() {
    }

    @Override
    public void kill() {
        new CMD().executeCmdCommand("taskkill /im opera.exe /t /f");
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }

}
