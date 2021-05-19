package controller.main;

import ch.dragxfly.quantumaccelerator.booster.Debloater;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class DelWindowsAppsController implements Initializable {

    @FXML
    private CheckBox chkDelCoartana;
    @FXML
    private CheckBox chkDelCandyCrush;
    @FXML
    private Button btnApply;
    @FXML
    private CheckBox chkDelMaps;
    @FXML
    private CheckBox chkDelFeedbackHub;
    @FXML
    private CheckBox chkDelCamera;
    @FXML
    private Button btnRevokeRights;
    @FXML
    private Group groupCheckboxes;
    @FXML
    private CheckBox chkSelectAll;
    @FXML
    private CheckBox chkDeleteNews;
    @FXML
    private CheckBox chkDelSolitaire;
    @FXML
    private CheckBox chkDelWeather;
    @FXML
    private VBox vboxCheckboxes;
    @FXML
    private CheckBox chkDelDefaultMail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void selectAll(ActionEvent event) {
        boolean checked = chkSelectAll.isSelected();
        //Selects all checkboxes in vbox
        vboxCheckboxes.getChildren().stream().filter(item -> item instanceof CheckBox).map(item -> (CheckBox) item).filter(item -> item.isSelected() != checked)
                .forEach(item -> item.setSelected(checked));
    }

    @FXML
    private void applyDelApps(ActionEvent event) {
        List<String> toDelete = new LinkedList<>();
        vboxCheckboxes.getChildren().stream()
                .filter(item -> item instanceof CheckBox)
                .map(item -> (CheckBox) item)
                .filter(item -> item.isSelected() == true && item.getAccessibleText() != null)
                .forEach(item -> toDelete.add(item.getAccessibleText()));
        Debloater debloater = new Debloater(toDelete);
        debloater.startDebloating();
    }

}
