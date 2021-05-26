package controller.Logo;

import ch.dragxfly.quantumaccelerator.Style.Logo.Dash;
import ch.dragxfly.quantumaccelerator.Style.Logo.Gear;
import ch.dragxfly.quantumaccelerator.Style.Logo.Needle;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class LogoController implements Initializable {

    private SVGPath svgGear;
    private SVGPath svgNeedle;
    private SVGPath svgDash;
    @FXML
    private StackPane paneLogo;
    private final double height;
    static final double PROP_DASH_HEIGHT = 2.3751617076326;
    static final double PROP_NEEDLE_HEIGHT = 4.340425531914894;
    static final double PROP_GEAR_HEIGHT = 1.830508474576271;
    static final double PROP_SPACING_NEEDLE = 10.8695652173913;
    static final double PROP_MOVE_NEEDLE = 11.92207792207792;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public LogoController(double height) {
        svgDash = new Dash(height / PROP_DASH_HEIGHT);
        svgNeedle = new Needle(height / PROP_NEEDLE_HEIGHT);
        svgGear = new Gear(height / PROP_GEAR_HEIGHT);
        this.height = height;
    }

    public void setup() {
        paneLogo.setPadding(new Insets(5, 0, 5, 0));
        paneLogo.setPrefHeight(height + 10);
        paneLogo.setMaxHeight(height + 10);
        paneLogo.setMaxWidth(height * 1.5);
        setMargins();
        paneLogo.getChildren().addAll(svgDash, svgNeedle, svgGear);
    }

    private void setMargins() {
        double halfDash = height / PROP_DASH_HEIGHT / 2;
        double halfGear = height / PROP_GEAR_HEIGHT / 2;
        double halfNeedle = height / PROP_NEEDLE_HEIGHT / 2;
        svgNeedle.setTranslateY(-height / PROP_MOVE_NEEDLE + halfNeedle);
        svgDash.setTranslateY(-height / 2 + halfDash);
        svgGear.setTranslateY(height / 2 - halfGear);
    }

    public void playLoadingAnimation() {
        Point3D point = new Point3D(0, 100, 0);
        svgDash.setRotationAxis(point);
        svgGear.setRotationAxis(point);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.75),
                        new KeyValue(svgDash.rotateProperty(), 180),
                        new KeyValue(svgGear.rotateProperty(), 0)),
                new KeyFrame(Duration.seconds(1.5),
                        new KeyValue(svgDash.rotateProperty(), 180),
                        new KeyValue(svgGear.rotateProperty(), 180)
                ),
                new KeyFrame(Duration.seconds(2.25),
                        new KeyValue(svgDash.rotateProperty(), 0),
                        new KeyValue(svgGear.rotateProperty(), 180)
                ),
                new KeyFrame(Duration.seconds(3),
                        new KeyValue(svgDash.rotateProperty(), 0),
                        new KeyValue(svgGear.rotateProperty(), 0)
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void setVisible(boolean visible, boolean isLoadingLogo) {
        svgGear.setVisible(visible);
        svgDash.setVisible(visible);
        if (isLoadingLogo) {
            svgNeedle.setVisible(false);
        } else {
            svgNeedle.setVisible(visible);
        }
    }

    public void setVisible(boolean visible) {
        svgGear.setVisible(visible);
        svgDash.setVisible(visible);
        svgNeedle.setVisible(visible);
    }

    public void stopLoadingAnimation() {
        svgNeedle.setVisible(true);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(4), new KeyValue(svgNeedle.rotateProperty(), 10)),
                new KeyFrame(Duration.seconds(4), new KeyValue(svgNeedle.rotateProperty(), -70)),
                new KeyFrame(Duration.seconds(4), new KeyValue(svgNeedle.rotateProperty(), 0))
        );
        timeline.play();
        setVisible(false);
    }
}
