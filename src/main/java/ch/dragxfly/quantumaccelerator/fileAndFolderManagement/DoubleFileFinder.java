package ch.dragxfly.quantumaccelerator.fileAndFolderManagement;

import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.SearchEngine.FolderScanner.FileDuplicatePair;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.SearchEngine.FolderScanner.SearchEngine;
import controller.popupwindows.DoubleFileFinderController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import javafx.concurrent.Task;
import org.codehaus.plexus.util.FileUtils;

/**
 *
 * @author jannis
 */
public class DoubleFileFinder {

    private final DoubleFileFinderController invocator;
    private final SearchEngine engine;
    private Thread workingThread;
    private boolean equalSize;
    private boolean equalContent;
    private boolean equalLastModified;
    private final List<FileDuplicatePair> alreadyMatched = new LinkedList<>();

    public DoubleFileFinder(DoubleFileFinderController invocator) {
        this.invocator = invocator;
        engine = new SearchEngine();
    }

    public void findDoubleFiles(String startPath, boolean equalSize, boolean equalContent, boolean equalLastModified) {
        this.equalSize = equalSize;
        this.equalContent = equalContent;
        this.equalLastModified = equalLastModified;
        Task t = getTaskSearchDuplicates(startPath);
        workingThread = new Thread(t);
        workingThread.start();
    }

    private Task getTaskSearchDuplicates(String startPath) {
        Task<Void> task = new Task<Void>() {
            SearchEngine searchEngine = new SearchEngine();

            @Override
            protected Void call() throws Exception {
                List<String> allFiles = engine.getAllFilesFrom(startPath);
                for (String search : allFiles) {
                    File toSearchFor = new File(search);
                    for (String possibleMatch : allFiles) {
                        if (new File(possibleMatch).getName().equals(toSearchFor.getName()) && !possibleMatch.equals(toSearchFor.getAbsolutePath())) {
                            if (canBeAdded(toSearchFor, new File(possibleMatch))) {
                                alreadyMatched.add(new FileDuplicatePair(toSearchFor.getAbsolutePath(), possibleMatch));
                                invocator.addDuplicate(new FileDuplicatePair(toSearchFor.getAbsolutePath(), possibleMatch));
                            }
                        }
                    }
                }
                alreadyMatched.clear();
                invocator.scanDone();
                return null;
            }
        };
        return task;
    }

    private boolean canBeAdded(File f1, File f2) {
        if (equalSize && notEqualSize(f1, f2)) {
            return false;
        }
        if (isAlreadyMatched(f1, f2)) {
            return false;
        }
        if (equalLastModified && !isEqualLastModified(f1, f2)) {
            return false;
        }
        return (equalContent && isSameContent(f1, f2));
    }

    private boolean isEqualLastModified(File f1, File f2) {
        try {
            BasicFileAttributes attrF1 = Files.readAttributes(f1.toPath(), BasicFileAttributes.class);
            BasicFileAttributes attrF2 = Files.readAttributes(f2.toPath(), BasicFileAttributes.class);
            return attrF1.lastModifiedTime().equals(attrF2.lastModifiedTime());
        } catch (IOException ex) {
            return false;
        }

    }

    private boolean notEqualSize(File f1, File f2) {
        return f1.length() != f2.length();
    }

    private boolean isAlreadyMatched(File f1, File f2) {
        FileDuplicatePair currentPair = new FileDuplicatePair(f1.getAbsolutePath(), f2.getAbsolutePath());
        for (FileDuplicatePair pair : alreadyMatched) {
            if (isSamePair(currentPair, pair)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSamePair(FileDuplicatePair p1, FileDuplicatePair p2) {
        if (p1.getFile2().equals(p2.getFile1()) && p1.getFile1().equals(p2.getFile2())) {
            return true;
        }
        return p1.getFile1().equals(p2.getFile1()) && p1.getFile2().equals(p2.getFile2());
    }

    private boolean isSameContent(File f1, File f2) {
        try {
            return FileUtils.contentEquals(f1, f2);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return false;
    }

    public void cancelScan() {
        if (workingThread != null) {
            if (workingThread.isAlive()) {
                workingThread.stop();
            }
        }
        invocator.scanDone();
    }
}
