package ch.dragxfly.quantumaccelerator.executors.errorhandling;

import controller.popupwindows.warning.ErrorController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author jannis
 */
public class ErrorWindow {

    private final Stage stage = new Stage();

    public void showErrorWindow(String errorMessage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popup/Error.fxml"));
        ErrorController controller = new ErrorController();
        loader.setController(controller);
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            controller.setErrorMessage(errorMessage);
            controller.setTheme();
        } catch (IOException ex) {
            System.err.print(ex);
        }

    }
}
