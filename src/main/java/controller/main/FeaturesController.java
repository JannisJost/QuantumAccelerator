
package controller.main;

import animatefx.animation.RubberBand;
import ch.dragxfly.quantumaccelerator.ViewManager.Web.WebsiteOpener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class FeaturesController implements Initializable {

    @FXML
    private ImageView imgLogo;
    @FXML
    private Button btnPatchnotes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new RubberBand(imgLogo).play();
    }

    @FXML
    private void triggerAnimationLogo(MouseEvent event) {
        new RubberBand(imgLogo).play();
    }

    @FXML
    private void openPatchnotes(ActionEvent event) {
        new WebsiteOpener().openWebsite("https://github.com/JannisJost/QuantumAccelerator/releases");
    }

}
