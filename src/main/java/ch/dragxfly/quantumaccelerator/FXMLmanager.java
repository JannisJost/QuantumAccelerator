package ch.dragxfly.quantumaccelerator;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 *
 * @author janni
 */
public class FXMLmanager {

    private Pane view;

    public Pane getPage(String fileName) {
        try {
            URL fileUrl = MainApp.class.getResource("/winbooster/" + fileName + ".fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML file not found");
            }
            view = FXMLLoader.load(fileUrl);
        } catch (IOException e) {
            System.err.println("Could not load/find page");
        }
        return view;
    }
}
