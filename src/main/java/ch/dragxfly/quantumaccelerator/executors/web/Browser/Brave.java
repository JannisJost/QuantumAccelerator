package ch.dragxfly.quantumaccelerator.executors.web.Browser;

import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.deleter.FileDeleter;
import java.util.LinkedList;
import java.util.List;
import shell.CMD;

/**
 *
 * @author jannis
 */
public class Brave implements Browser {
    
    private final FileDeleter deleter = new FileDeleter();
    
    @Override
    public void clearCache() {
        clearBaseCache();
        clearJsCache();
    }
    
    @Override
    public void clearHistory() {
        List<String> history = new LinkedList<>();
        String appdataFolder = System.getenv("APPDATA").replace("Roaming", "");
        String historyFile = appdataFolder + "\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\History";
        String historyCache = appdataFolder + "\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\History Provider Cache";
        String historyJournal = appdataFolder + "\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\History-journal";
        String mediaHistory = appdataFolder + "\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Media History";
        String mediaHistoryJournal = appdataFolder + "\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Media History-journal";
        history.add(historyFile);
        history.add(historyCache);
        history.add(historyJournal);
        history.add(mediaHistory);
        history.add(mediaHistoryJournal);
        deleter.deleteFiles(history);
    }
    
    private void clearJsCache() {
        String appdataFolder = System.getenv("APPDATA").replace("Roaming", "");
        String jsCacheFolder = appdataFolder + "\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Code Cache\\js";
        deleter.deleteAllFilesInDirectory(jsCacheFolder);
    }
    
    private void clearBaseCache() {
        String appdataFolder = System.getenv("APPDATA").replace("Roaming", "");
        String cacheFolder = appdataFolder + "\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Cache";
        deleter.deleteAllFilesInDirectory(cacheFolder);
    }
    
    @Override
    public void clearCookies() {
        List<String> cookies = new LinkedList<>();
        String appdataFolder = System.getenv("APPDATA").replace("Roaming", "");
        String cookiesFile = appdataFolder + "\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Cookies";
        String cookiesJournal = appdataFolder + "\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Cookies-journal";
        String extensionCookies = appdataFolder + "\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Extension Cookies";
        String extensionCookiesJournal = appdataFolder + "\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Extension Cookies-journal";
        cookies.add(cookiesFile);
        cookies.add(cookiesJournal);
        cookies.add(extensionCookies);
        cookies.add(extensionCookiesJournal);
        deleter.deleteFiles(cookies);
    }
    
    @Override
    public void kill() {
        new CMD().executeCmdCommand("taskkill /im brave.exe /t /f");
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }
}
