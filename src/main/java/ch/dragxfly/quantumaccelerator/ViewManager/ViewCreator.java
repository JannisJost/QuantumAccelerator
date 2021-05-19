package ch.dragxfly.quantumaccelerator.ViewManager;

import controller.main.ApplicationSettingsController;
import java.io.IOException;
import java.util.prefs.Preferences;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 *
 * @author janni
 */
public class ViewCreator {

    private final Preferences pref = Preferences.userRoot();
    private static final String ANIMATIONSACTIVE = "playanimations";

    public Pane getViewFeatures() throws IOException {
        return new FXMLLoader(getClass().getResource("/fxml/Main/Features.fxml")).load();
    }

    public Pane getViewGamebooster() throws IOException {
        return new FXMLLoader(getClass().getResource("/fxml/Main/Gamebooster.fxml")).load();

    }

    public Pane getViewDelWindowsApps() throws IOException {
        return new FXMLLoader(getClass().getResource("/fxml/Main/DelWindowsApps.fxml")).load();
    }

    public Pane getViewStorage() throws IOException {
        return new FXMLLoader(getClass().getResource("/fxml/Main/Storage.fxml")).load();
    }

    public Pane getViewExtras() throws IOException {
        return new FXMLLoader(getClass().getResource("/fxml/Main/Extras.fxml")).load();
    }

    public Pane getViewPrivacy() throws IOException {
        return new FXMLLoader(getClass().getResource("/fxml/Main/Privacy.fxml")).load();
    }

    public Pane getViewSettings() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main/ApplicationSettings.fxml"));
        Pane p = loader.load();
        ApplicationSettingsController controller = loader.getController();
        //Sets the play Animation checkbox to the value before closing application
        controller.setPlayAnimations(pref.getBoolean(ANIMATIONSACTIVE, true));
        return p;
    }
}
