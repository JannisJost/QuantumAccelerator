package ch.dragxfly.quantumaccelerator.fileAndFolderManagement;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 *
 * @author janni
 */
public class FolderChooser extends Observable {

    private final JFileChooser folderChooser;


    public FolderChooser(String title, Observer o) {
        addObserver(o);
        folderChooser = new JFileChooser();
        folderChooser.setDialogTitle("Choose folder to blacklist");
        folderChooser.setVisible(true);
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderChooser.setAcceptAllFileFilterUsed(false);
    }

    public void show() {
        if (folderChooser.showOpenDialog(new JPanel()) == JFileChooser.APPROVE_OPTION) {
            setChanged();
            notifyObservers(folderChooser.getSelectedFile().getPath());
        }
    }
}
