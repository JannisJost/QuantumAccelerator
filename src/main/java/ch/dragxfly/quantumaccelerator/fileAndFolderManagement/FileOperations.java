package ch.dragxfly.quantumaccelerator.fileAndFolderManagement;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author jannis
 */
public class FileOperations {

    public String getExtension(String path) {
        return path.substring(path.lastIndexOf('.') + 1);
    }

    public boolean fileIsOlderThanDays(String path, int numberOfDays) {
        LocalDate today = LocalDate.now();
        LocalDate daysEarlier = today.minusDays(numberOfDays);
        File file = new File(path);
        Date date = new Date(file.lastModified());
        Date earlier = Date.from(daysEarlier.atStartOfDay(ZoneId.systemDefault()).toInstant());
        if (date.before(earlier)) {
            return true;
        }
        return false;
    }
}
