package controller.popupwindows.warning;

import ch.dragxfly.quantumaccelerator.views.MultilingualView;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

/**
 *
 * @author jannis
 */
public class InfoWindow extends MultilingualView {

    public void ShowInfoWindow(String infoMessage) {
        Locale locale = new Locale(super.getLanguage());
        ResourceBundle bundle = ResourceBundle.getBundle("languages.warnings.warnings", locale);
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle(bundle.getString("info"));
        info.setHeaderText(bundle.getString("info"));
        info.setContentText(infoMessage);
        info.setGraphic(new ImageView(this.getClass().getResource("/styles/icons/popup/Info.png").toString()));
        info.showAndWait();
    }

    @Override
    public void setLanguage(String lang) {
    }
}
