package ch.dragxfly.quantumaccelerator.Executors.errorhandling;

import controller.popupwindows.warning.ErrorController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author janni
 */
public class ErrorWindow {

    public void showErrorWindow(String errorMessage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popup/Error.fxml"));
        ErrorController controller = new ErrorController();
        loader.setController(controller);
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            controller.setErrorMessage(errorMessage);
            controller.setTheme();
        } catch (IOException ex) {
        }

    }
}
