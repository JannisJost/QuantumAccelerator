
package controller.main;

import animatefx.animation.RubberBand;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

}
