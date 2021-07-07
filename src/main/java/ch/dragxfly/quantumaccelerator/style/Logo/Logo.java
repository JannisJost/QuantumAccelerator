package ch.dragxfly.quantumaccelerator.style.logo;

import controller.Logo.LogoController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * QuantumAccelerator Logo which can also be used for loading animations
 *
 * @author janni
 */
public class Logo extends VBox {

    static final double PROP_DASH_HEIGHT = 2.3751617076326;
    static final double PROP_NEEDLE_HEIGHT = 4.340425531914894;
    static final double PROP_GEAR_HEIGHT = 1.830508474576271;
    private final LogoController controller;

    public Logo(int height) throws IOException {
        super();
        super.setMaxHeight(height);
        super.setAlignment(Pos.CENTER);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Logo/Logo.fxml"));
        controller = new LogoController(height);
        loader.setController(controller);
        super.getChildren().add(loader.load());
        controller.setup();
    }

    public void playLoadingAnimation() {
        controller.playLoadingAnimation();
    }

    public void playLoadingFinishedAnimation() {
        controller.playLoadingFinishedAnimation();
    }

    public void setShowing(boolean visible) {
        controller.setVisible(visible);
    }

    public void setShowing(boolean visible, boolean isLoadingLogo) {
        controller.setVisible(visible, isLoadingLogo);
    }
}
