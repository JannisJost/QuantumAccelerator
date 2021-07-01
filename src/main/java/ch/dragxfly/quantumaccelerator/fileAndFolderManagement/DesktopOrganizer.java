package ch.dragxfly.quantumaccelerator.fileAndFolderManagement;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author jannis
 */
public class DesktopOrganizer {

    private String desktopPath;
    private final boolean moveTexts;
    private final boolean moveImages;
    private final boolean moveExecutables;
    private final String textsFolderName;
    private final String imagesFolderName;
    private final String executablesFolderName;

    public DesktopOrganizer(boolean moveTexts, String textsFolderName,
            boolean moveImages, String imagesFolderName,
            boolean moveExecutables, String executablesFolderName) {
        this.moveTexts = moveTexts;
        this.moveImages = moveImages;
        this.moveExecutables = moveExecutables;
        this.textsFolderName = textsFolderName;
        this.imagesFolderName = imagesFolderName;
        this.executablesFolderName = executablesFolderName;
    }

    public void organizeDesktop() {
        getDesktopPath();
        String[] files = getDesktopFiles(desktopPath);
        moveFilesToFolders(files);
    }

    private void getDesktopPath() {
        String oneDriveDesktop = System.getProperty("user.home") + "\\OneDrive\\Desktop";
        if (new File(oneDriveDesktop).exists()) {
            desktopPath = oneDriveDesktop;
        } else {
            desktopPath = System.getProperty("user.home") + "\\Desktop";
        }
    }

    private String[] getDesktopFiles(String desktopPath) {
        return new File(desktopPath).list();
    }

    private void moveFilesToFolders(String[] files) {
        if (moveImages) {
            String[] imgEndings = {"png", "jpg", "jpeg", "svg"};
            for (String path : files) {
                if (fileEndsWith(path, imgEndings)) {
                    File fileToMove = new FileUtils().getFile(desktopPath + "\\" + path);
                    File target = FileUtils.getFile(desktopPath + "\\" + imagesFolderName + "\\");
                    try {
                        FileUtils.moveFileToDirectory(fileToMove, target, true);
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
            }
        }
        if (moveTexts) {
            String[] txtEndings = {"txt", "docx"};
            for (String path : files) {
                if (fileEndsWith(path, txtEndings)) {
                    File fileToMove = new FileUtils().getFile(desktopPath + "\\" + path);
                    File target = FileUtils.getFile(desktopPath + "\\" + textsFolderName + "\\");
                    try {
                        FileUtils.moveFileToDirectory(fileToMove, target, true);
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
            }
        }
                if (moveExecutables) {
            String[] txtEndings = {"exe", "jar"};
            for (String path : files) {
                if (fileEndsWith(path, txtEndings)) {
                    File fileToMove = new FileUtils().getFile(desktopPath + "\\" + path);
                    File target = FileUtils.getFile(desktopPath + "\\" + executablesFolderName + "\\");
                    try {
                        FileUtils.moveFileToDirectory(fileToMove, target, true);
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
            }
        }
    }

    private boolean fileEndsWith(String pathToFile, String[] endings) {
        FileOperations operator = new FileOperations();
        for (int i = 0; i < endings.length; i++) {
            if (operator.getExtension(pathToFile).equalsIgnoreCase(endings[i])) {
                return true;
            }
        }
        return false;
    }
}
