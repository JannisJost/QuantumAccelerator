package ch.dragxfly.quantumaccelerator.models;

import javafx.scene.layout.Pane;

/**
 *
 * @author janni
 */
public class ViewsModel {

    private final Pane features;
    private final Pane storage;
    private final Pane gamebooster;
    private final Pane delWindowsApps;
    private final Pane extras;
    private final Pane privacy;
    private final Pane settings;

    public ViewsModel(Pane features, Pane storage, Pane gamebooster, Pane delWindowsApps, Pane extras, Pane privacy, Pane settings) {
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
        return gamebooster;
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
}
