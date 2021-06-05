package controller.popupwindows.warning;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

/**
 *
 * @author janni
 */
public class InfoWindow {

    public boolean ShowInfoWindow(String infoMessage) {
        Alert info = new Alert(AlertType.CONFIRMATION);
        info.setTitle("Info");
        info.setHeaderText("Are you sure?");
        info.setContentText(infoMessage);
        info.setGraphic(new ImageView(this.getClass().getResource("/styles/icons/popup/Info.png").toString()));
        info.getButtonTypes().clear();
        ButtonType ok = new ButtonType("Continue");
        ButtonType cancel = new ButtonType("Cancel");
        info.getButtonTypes().addAll(ok, cancel);
        Optional<ButtonType> result = info.showAndWait();
        return result.get() == ok;
    }
}
