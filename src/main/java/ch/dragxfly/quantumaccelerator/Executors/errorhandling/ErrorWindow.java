package ch.dragxfly.quantumaccelerator.Executors.errorhandling;

import controller.popupwindows.warning.ErrorController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author janni
 */
public class ErrorWindow {

    public void showErrorWindow(String errorMessage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Error.fxml"));
        ErrorController controller = new ErrorController();
        loader.setController(controller);
        controller.setErrorMessage(errorMessage);
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
        }

    }
}
