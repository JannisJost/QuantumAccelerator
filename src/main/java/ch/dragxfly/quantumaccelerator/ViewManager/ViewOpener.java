package ch.dragxfly.quantumaccelerator.ViewManager;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author janni
 */
public class ViewOpener {

    private Stage splash;

    public void openView(String path, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(path));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Failed to open new Window");
        }
    }

    public void openThemeableView(String path, String title, boolean alwaysOnTop) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.setLocation(getClass().getResource(path));
            Scene scene = new Scene(fxmlLoader.load());
            ThemeableWindow themeable = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
            stage.setAlwaysOnTop(alwaysOnTop);
            themeable.setTheme();
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Failed to open new Window");
        }
    }

    public Scene getScene(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(path));
            Scene scene = new Scene(fxmlLoader.load());
            return scene;
        } catch (Exception e) {
            return null;
        }
    }

}
