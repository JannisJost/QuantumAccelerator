package winbooster.SearchEngine.FolderScanner;

import java.util.Arrays;

/**
 *
 * @author janni
 */
public class IsFileChecker {

    public boolean isFile(String path) {
        String[] filestypes = {".log",".pdb",".ico",".cmd",".MAP",".dll",".cache",".bat",".svclog",".exe",".html",".xsl",".pdb", ".ses", ".temp", ".tmp", ".ngllogcontrolconfig", ".pom", ".xml", ".diagsession", ".etl", ".pdf", ".zip", ".ini", ".ico", ".png", ".jpg", ".jpeg", ".chk", ".prv", ".mp3", ".mp4", ".etl", ".json", ".txt", ".jfm", ".ses", ".node", ".dat"};
        return Arrays.stream(filestypes).anyMatch(path::contains);
    }
}
