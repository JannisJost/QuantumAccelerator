package ch.dragxfly.quantumaccelerator.models;

import ch.dragxfly.quantumaccelerator.views.MainView;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

/**
 *
 * @author jannis
 */
public class ViewsModel {

    private final Pane features, storage, delWindowsApps, extras, privacy, settings;
    private final Pair<Pane, MainView> gamebooster;

    public ViewsModel(Pane features, Pane storage, Pair<Pane, MainView> gamebooster, Pane delWindowsApps, Pane extras, Pane privacy, Pane settings) {
        this.features = features;
        this.storage = storage;
        this.gamebooster = gamebooster;
        this.delWindowsApps = delWindowsApps;
        this.extras = extras;
        this.privacy = privacy;
        this.settings = settings;
    }

    public Pane getFeatures() {
        return features;
    }

    public Pane getStorage() {
        return storage;
    }

    public Pane getGamebooster() {
        gamebooster.getValue().onOpen();
        return gamebooster.getKey();
    }

    public Pane getDelWindowsApps() {
        return delWindowsApps;
    }

    public Pane getExtras() {
        return extras;
    }

    public Pane getPrivacy() {
        return privacy;
    }

    public Pane getSettings() {
        return settings;
    }

    public void setTheme(boolean isDarkTheme) {
        gamebooster.getValue().changeTheme(isDarkTheme);
    }
}
