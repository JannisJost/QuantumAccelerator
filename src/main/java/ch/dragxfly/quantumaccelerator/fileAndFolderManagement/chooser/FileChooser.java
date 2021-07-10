package ch.dragxfly.quantumaccelerator.fileAndFolderManagement.chooser;

import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.FileFilter;
import controller.popupwindows.fileandfolder.FileChooserController;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author jannis
 */
public class FileChooser extends Observable {

    private FileFilter filter = new FileFilter();
    private final Stage stage = new Stage();

    public FileChooser(Observer observer) {
        this.addObserver(observer);
    }

    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/popup/fileandfolder/FileChooser.fxml"));
            Scene scene = new Scene(loader.load());
            FileChooserController controller = loader.getController();
            stage.setTitle("File chooser");
            stage.setAlwaysOnTop(true);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            controller.setFilter(filter);
            controller.setInvocator(this);
            stage.show();
            controller.setTheme();
            controller.refresh();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public void setFileFilter(FileFilter filter) {
        this.filter = filter;
    }

    public void fileHasBeenChoosen(String filePath) {
        this.setChanged();
        this.notifyObservers(filePath);
        stage.close();
    }
}
