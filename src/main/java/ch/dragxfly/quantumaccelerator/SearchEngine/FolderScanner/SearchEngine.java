package ch.dragxfly.quantumaccelerator.SearchEngine.FolderScanner;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author janni
 */
public class SearchEngine {

    private boolean stillSearching = true;
    private final List<String> foldersBefore = new LinkedList<>();
    private final List<String> accessDenied = new LinkedList<>();
    private final List<String> requestedFiles = new LinkedList<>();
    private final List<String> children = new LinkedList<>();
    private List<String> foldersOnly = new LinkedList<>();

    public void searchFoldersContaining(String startDirectory, String toSearchFor) {
        foldersBefore.add(startDirectory);
        do {
            children.clear();
            foldersOnly.clear();
            for (String path : foldersBefore) {
                children.addAll(getChildren(path));
            }
            foldersBefore.clear();
            foldersOnly = getFoldersOnly(children);
            stillSearching();
            foldersBefore.addAll(foldersOnly);
            requestedFiles.addAll(getEndingWith(foldersOnly, toSearchFor));
        } while (stillSearching);
    }

    public void searchFoldersContaining(String startDirectory, String[] toSearchFor) {
        foldersBefore.add(startDirectory);
        do {
            children.clear();
            foldersOnly.clear();
            for (String path : foldersBefore) {
                children.addAll(getChildren(path));
            }
            foldersBefore.clear();
            foldersOnly = getFoldersOnly(children);
            stillSearching();
            foldersBefore.addAll(foldersOnly);
            requestedFiles.addAll(getEndingWith(foldersOnly, toSearchFor));
        } while (stillSearching);
    }

    public void searchForFilesContaining(String startDirectory, String[] toSearchFor) {
        foldersBefore.add(startDirectory);
        do {
            children.clear();
            foldersOnly.clear();
            for (String path : foldersBefore) {
                children.addAll(getChildren(path));
            }
            foldersOnly = getFoldersOnly(children);
            foldersBefore.clear();
            children.removeAll(foldersOnly);
            List<String> filesOnly = children;
            stillSearching();
            foldersBefore.addAll(foldersOnly);
            requestedFiles.addAll(getFilesContaining(filesOnly, toSearchFor));
        } while (stillSearching);
    }

    /**
     *
     * @param startDirectory defines the directory from which to start searching
     * to bottom of the file system
     * @param extension file extention to search for
     */
    public void searchForFilesWithExtension(String startDirectory, String extension) {
        foldersBefore.add(startDirectory);
        do {
            children.clear();
            foldersOnly.clear();
            for (String path : foldersBefore) {
                children.addAll(getChildren(path));
            }
            foldersOnly = getFoldersOnly(children);
            foldersBefore.clear();
            children.removeAll(foldersOnly);
            List<String> filesOnly = children;
            stillSearching();
            foldersBefore.addAll(foldersOnly);
            requestedFiles.addAll(getFileWithExtension(filesOnly, extension));
        } while (stillSearching);
    }

    private List<String> getChildren(String path) {
        //Get child folders and files of a given directory
        List<File> files = new ArrayList<>();
        try {
            files = Files.list(Paths.get(path)).map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            //System.out.println(e);
            accessDenied.add(path);
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

    private List<String> getEndingWith(List<String> toSearch, String ending) {
        List<String> endWith = new LinkedList<>();
        for (String path : toSearch) {
            if (path.toLowerCase().endsWith(ending)) {
                endWith.add(path);
            }
        }
        return endWith;
    }

    private List<String> getEndingWith(List<String> toSearch, String[] endings) {
        List<String> endWith = new LinkedList<>();
        for (String path : toSearch) {
            for (int i = 0; i <= endings.length - 1; i++) {
                if (path.toLowerCase().endsWith(endings[i])) {
                    endWith.add(path);
                }
            }
        }
        return endWith;
    }

    /**
     *
     * @return Files which where searched before
     */
    public List<String> getRequested() {
        return requestedFiles;
    }

    public List<String> getAccessDenied() {
        return accessDenied;
    }

    private void stillSearching() {
        if (foldersOnly.isEmpty()) {
            stillSearching = false;
        }
    }

    private List<String> getFileWithExtension(List<String> toSearch, String extension) {
        String fileExtension;
        List<String> wantedFiles = new ArrayList<>();
        for (String file : toSearch) {
            fileExtension = file.substring(file.lastIndexOf(".") + 1);
            if (fileExtension.equals(extension)) {
                wantedFiles.add(file);
            }
        }
        return wantedFiles;
    }

    private List<String> getFilesContaining(List<String> toSearch, String[] toSearchFor) {
        List<String> wantedFiles = new ArrayList<>();
        File file;
        for (String path : toSearch) {
            file = new File(path);
            for (int i = 0; i <= toSearchFor.length - 1; i++) {
                if (file.getName().toLowerCase().contains(toSearchFor[i])) {
                    wantedFiles.add(path);
                }
            }
        }
        return wantedFiles;
    }
}
