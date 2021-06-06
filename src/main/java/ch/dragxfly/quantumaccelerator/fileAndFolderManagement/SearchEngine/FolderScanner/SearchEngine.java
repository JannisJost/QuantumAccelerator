package ch.dragxfly.quantumaccelerator.fileAndFolderManagement.SearchEngine.FolderScanner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author janni
 */
public class SearchEngine {

    private final List<String> accessDenied = new LinkedList<>();
    LinkedList<String> requested = new LinkedList<>();

    /**
     * searches for folders containing a specific String in name
     *
     * @param startDirectory directory to start searching to buttom of file
     * system from
     * @param toSearchFor lowercase String to search foldernames containing it
     * @return list of all folders which names contain toSearchFor
     */
    public LinkedList<String> searchFoldersContaining(String startDirectory, String toSearchFor) {
        requested.clear();
        searchFolders(startDirectory, toSearchFor);
        return requested;
    }

    public LinkedList<String> searchFoldersContaining(String startDirectory, String[] toSearchFor) {
        requested.clear();
        searchFolders(startDirectory, toSearchFor);
        return requested;
    }

    public void searchFolders(String startDirectory, String toSearchFor) {
        if (!getChildren(startDirectory).isEmpty()) {
            for (String path : getFoldersOnly(getChildren(startDirectory))) {
                if (path.toLowerCase().endsWith(toSearchFor)) {
                    requested.add(path);
                }
                searchFolders(path, toSearchFor);
            }
        }
    }

    public void searchFolders(String startDirectory, String[] toSearchFor) {
        if (!getChildren(startDirectory).isEmpty()) {
            for (String path : getFoldersOnly(getChildren(startDirectory))) {
                for (int i = 0; i < toSearchFor.length - 1; i++) {
                    if (path.toLowerCase().endsWith(toSearchFor[i])) {
                        requested.add(path);
                        break;
                    }
                }
                searchFolders(path, toSearchFor);
            }
        }
    }

    public LinkedList<String> searchForFilesContaining(String startDirectory, String[] toSearchFor) {
        searchFiles(startDirectory, toSearchFor);
        return requested;
    }

    public void searchFiles(String startDirectory, String[] toSearchFor) {
        if (!getChildren(startDirectory).isEmpty()) {
            for (String path : getChildren(startDirectory)) {
                for (int i = 0; i < toSearchFor.length - 1; i++) {
                    if (path.contains(toSearchFor[i])) {
                        requested.add(path);
                    }
                }
                searchFolders(path, toSearchFor);
            }
        }
    }

    public void searchFiles(String startDirectory, String toSearchFor) {
        if (!getChildren(startDirectory).isEmpty()) {
            for (String path : getChildren(startDirectory)) {
                if (path.contains(toSearchFor)) {
                    requested.add(path);
                }
                searchFolders(path, toSearchFor);
            }
        }
    }

    /**
     *
     * @param startDirectory defines the directory from which to start searching
     * to bottom of the file system
     * @param extension file extention to search for
     * @return files with specified extension
     */
    public LinkedList<String> searchForFilesWithExtension(String startDirectory, String extension) {
        requested.clear();
        searchFiles(startDirectory, extension);
        return requested;
    }

    /**
     *
     * @param parentPath path of the directory you need children from
     * @return child folders and files of the given directory
     */
    private List<String> getChildren(String parentPath) {
        //Get child folders and files of a given directory
        List<File> files = new ArrayList<>();
        try {
            files = Files.list(Paths.get(parentPath)).map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            accessDenied.add(parentPath);
        }
        return FilesToString(files);
    }

    private List<String> FilesToString(List<File> files) {
        //Converts an array to a list
        List<String> listString = new LinkedList<>();
        for (File f : files) {
            listString.add(f.getAbsolutePath());
        }
        return listString;
    }

    /**
     *
     * @param toSort list of paths still containing files
     * @return toSort without the files
     */
    private List<String> getFoldersOnly(List<String> toSort) {
        //Returns a list without files (folders only)
        List<String> foldersOnly = new LinkedList<>();
        for (String path : toSort) {
            if (new File(path).isDirectory()) {
                foldersOnly.add(path);
            }
        }
        return foldersOnly;
    }

    public List<String> getAccessDenied() {
        return accessDenied;
    }
}
