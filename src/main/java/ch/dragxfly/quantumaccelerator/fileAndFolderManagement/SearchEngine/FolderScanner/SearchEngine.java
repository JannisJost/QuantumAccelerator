package ch.dragxfly.quantumaccelerator.fileAndFolderManagement.SearchEngine.FolderScanner;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jannis
 */
public class SearchEngine {

    private final List<String> accessDenied = new LinkedList<>();

    /**
     * searches for folders containing a specific String in name
     *
     * @param startDirectory directory to start searching to buttom of file
     * system from
     * @param toSearchFor lowercase String to search foldernames containing it
     * @return list of all folders which names contain toSearchFor
     */
    public LinkedList<String> searchFoldersContaining(String startDirectory, String toSearchFor) {
        return searchFolders(startDirectory, toSearchFor);
    }

    public LinkedList<String> searchFoldersContaining(String startDirectory, String[] toSearchFor) {
        return searchFolders(startDirectory, toSearchFor);
    }

    public LinkedList<String> searchFolders(String startDirectory, String toSearchFor) {
        LinkedList<String> requested = new LinkedList<>();
        if (!getChildren(startDirectory).isEmpty()) {
            for (String path : getFoldersOnly(getChildren(startDirectory))) {
                if (path.toLowerCase().endsWith(toSearchFor)) {
                    requested.add(path);
                } else {
                    requested.addAll(searchFolders(path, toSearchFor));
                }
            }
        }
        return requested;
    }

    public LinkedList<String> searchFolders(String startDirectory, String[] toSearchFor) {
        LinkedList<String> requested = new LinkedList<>();
        if (!getChildren(startDirectory).isEmpty()) {
            for (String path : getFoldersOnly(getChildren(startDirectory))) {
                for (int i = 0; i < toSearchFor.length - 1; i++) {
                    if (path.toLowerCase().endsWith(toSearchFor[i])) {
                        requested.add(path);
                        break;
                    }
                }
                requested.addAll(searchFolders(path, toSearchFor));
            }
        }
        return requested;
    }

    public LinkedList<String> searchForFilesContaining(String startDirectory, String[] toSearchFor) {
        return searchFiles(startDirectory, toSearchFor);
    }

    public LinkedList<String> searchFiles(String startDirectory, String[] toSearchFor) {
        LinkedList<String> requested = new LinkedList<>();
        if (!getChildren(startDirectory).isEmpty()) {
            for (String path : getChildren(startDirectory)) {
                for (int i = 0; i < toSearchFor.length - 1; i++) {
                    if (new File(path).isFile()) {
                        if (path.toLowerCase().contains(toSearchFor[i])) {
                            requested.add(path);
                        }
                    }
                }
                requested.addAll(searchFolders(path, toSearchFor));
            }
        }
        return requested;
    }

    public LinkedList<String> searchFiles(String startDirectory, String toSearchFor) {
        LinkedList<String> requested = new LinkedList<>();
        if (!getChildren(startDirectory).isEmpty()) {
            for (String path : getChildren(startDirectory)) {
                if (new File(path).isFile()) {
                    if (path.toLowerCase().contains(toSearchFor)) {
                        requested.add(path);
                    }
                }
                requested.addAll(searchFolders(path, toSearchFor));
            }
        }
        return requested;
    }

    public LinkedList<String> getAllFilesFrom(String startDirectory) {
        startDirectory = startDirectory.replace("\\\\", "\\");
        LinkedList<String> requested = new LinkedList<>();
        if (!getChildren(startDirectory).isEmpty()) {
            for (String path : getChildren(startDirectory)) {
                if (new File(path).isFile()) {
                    requested.add(path);
                }
                requested.addAll(getAllFilesFrom(path));
            }
        }
        return requested;
    }

    /**
     *
     * @param startDirectory defines the directory from which to start searching
     * to bottom of the file system
     * @param extension file extention to search for
     * @return files with specified extensioJn
     */
    public LinkedList<String> searchForFilesWithExtension(String startDirectory, String extension) {
        return searchFiles(startDirectory, extension);
    }

    /**
     *
     * @param parentPath path of the directory you need children from
     * @return child folders and files of the given directory
     */
    private List<String> getChildren(String parentPath) {
        //Get child folders and files of a given directory
        try {
            List<String> fileNames = Arrays.asList(new File(parentPath).list());
            List<String> files = new LinkedList<>();
            for (String f : fileNames) {
                files.add(parentPath + "\\" + f);
            }
            return files;
        } catch (Exception e) {
            System.out.println(e);
            return new LinkedList<>();
        }
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
