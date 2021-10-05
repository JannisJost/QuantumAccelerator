package ch.dragxfly.quantumaccelerator.fileAndFolderManagement;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jannis
 */
public class FileListOperations {

    public List<String> getFilesOlderThanDays(List<String> files, int numberOfDays) {
        List<String> filesOlderThanDays = new LinkedList<>();
        FileOperations fileOp = new FileOperations();
        for (int i = 0; i < files.size() - 1; i++) {
            if (fileOp.fileIsOlderThanDays(files.get(i), 5)) {
                filesOlderThanDays.add(files.get(i));
            }
        }
        return filesOlderThanDays;
    }
}
