package controller.popupwindows.warning;

import ch.dragxfly.quantumaccelerator.views.MultilingualView;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

/**
 *
 * @author jannis
 */
public class InfoDecisionWindow extends MultilingualView {

    public boolean ShowInfoWindow(String infoMessage) {
        Locale locale = new Locale(super.getLanguage());
        ResourceBundle bundle = ResourceBundle.getBundle("languages.warnings.warnings", locale);
        Alert info = new Alert(AlertType.CONFIRMATION);
        info.setTitle(bundle.getString("info"));
        info.setHeaderText(bundle.getString("rUSure"));
        info.setContentText(infoMessage);
        info.setGraphic(new ImageView(this.getClass().getResource("/styles/icons/popup/Info.png").toString()));
        info.getButtonTypes().clear();
        ButtonType ok = new ButtonType(bundle.getString("btnContinue"));
        ButtonType cancel = new ButtonType(bundle.getString("btnCancel"));
        info.getButtonTypes().addAll(ok, cancel);
        Optional<ButtonType> result = info.showAndWait();
        return result.get() == ok;
    }

    @Override
    public void setLanguage(String lang) {
    }
}
