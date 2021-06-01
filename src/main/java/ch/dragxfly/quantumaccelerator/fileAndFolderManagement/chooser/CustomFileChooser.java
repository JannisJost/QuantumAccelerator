package ch.dragxfly.quantumaccelerator.fileAndFolderManagement.chooser;

import java.awt.Container;
import javax.swing.JFileChooser;

public class CustomFileChooser extends JFileChooser {

    Container parent;

    @Override
    public void show() {
        rescanCurrentDirectory();
        revalidate();
        repaint();
    }
}
