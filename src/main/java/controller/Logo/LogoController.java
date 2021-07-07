package controller.Logo;

import ch.dragxfly.quantumaccelerator.style.logo.Dash;
import ch.dragxfly.quantumaccelerator.style.logo.Gear;
import ch.dragxfly.quantumaccelerator.style.logo.Needle;
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

    static final double PROP_DASH_HEIGHT = 2.3751617076326;
    static final double PROP_NEEDLE_HEIGHT = 4.340425531914894;
    static final double PROP_GEAR_HEIGHT = 1.830508474576271;
    static final double PROP_SPACING_NEEDLE = 10.8695652173913;
    static final double PROP_MOVE_NEEDLE = 11.92207792207792;

    @FXML
    private StackPane paneLogo;
    private final SVGPath Gear;
    private final SVGPath Needle;
    private final SVGPath Dash;
    private final double height;
    private Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public LogoController(double height) {
        Dash = new Dash(height / PROP_DASH_HEIGHT);
        Needle = new Needle(height / PROP_NEEDLE_HEIGHT);
        Gear = new Gear(height / PROP_GEAR_HEIGHT);
        this.height = height;
    }

    public void setup() {
        paneLogo.setPadding(new Insets(5, 0, 5, 0));
        paneLogo.setPrefHeight(height + 10);
        paneLogo.setMaxHeight(height + 10);
        paneLogo.setMaxWidth(height * 1.5);
        setMargins();
        paneLogo.getChildren().addAll(Dash, Needle, Gear);
    }

    private void setMargins() {
        double halfDashHeight = height / PROP_DASH_HEIGHT / 2;
        double halfGearHeight = height / PROP_GEAR_HEIGHT / 2;
        double halfNeedleHeight = height / PROP_NEEDLE_HEIGHT / 2;
        Needle.setTranslateY(-height / PROP_MOVE_NEEDLE + halfNeedleHeight);
        Dash.setTranslateY(-height / 2 + halfDashHeight);
        Gear.setTranslateY(height / 2 - halfGearHeight);
    }

    public void playLoadingAnimation() {
        Point3D point = new Point3D(0, 100, 0);
        Dash.setRotationAxis(point);
        Gear.setRotationAxis(point);
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.75),
                        new KeyValue(Dash.rotateProperty(), 180),
                        new KeyValue(Gear.rotateProperty(), 0)),
                new KeyFrame(Duration.seconds(1.5),
                        new KeyValue(Dash.rotateProperty(), 180),
                        new KeyValue(Gear.rotateProperty(), 180)
                ),
                new KeyFrame(Duration.seconds(2.25),
                        new KeyValue(Dash.rotateProperty(), 0),
                        new KeyValue(Gear.rotateProperty(), 180)
                ),
                new KeyFrame(Duration.seconds(3),
                        new KeyValue(Dash.rotateProperty(), 0),
                        new KeyValue(Gear.rotateProperty(), 0)
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void setVisible(boolean visible, boolean isLoadingLogo) {
        Gear.setVisible(visible);
        Dash.setVisible(visible);
        if (isLoadingLogo) {
            Needle.setVisible(false);
        } else {
            Needle.setVisible(visible);
        }
    }

    public void setVisible(boolean visible) {
        Gear.setVisible(visible);
        Dash.setVisible(visible);
        Needle.setVisible(visible);
    }

    public void playLoadingFinishedAnimation() {
        timeline.stop();
        reset();
        Needle.setVisible(true);
        Timeline stopAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(Needle.rotateProperty(), -200)),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(Needle.rotateProperty(), 0)),
                new KeyFrame(Duration.seconds(2), new KeyValue(Needle.opacityProperty(), 1),
                        new KeyValue(Gear.opacityProperty(), 1),
                        new KeyValue(Dash.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(3), new KeyValue(Needle.opacityProperty(), 0),
                        new KeyValue(Gear.opacityProperty(), 0),
                        new KeyValue(Dash.opacityProperty(), 0))
        );
        stopAnimation.play();
        stopAnimation.setOnFinished(event -> {
            setVisible(false);
            reset();
        });
    }

    /**
     * Resets the state changed by the animation
     */
    private void reset() {
        Gear.setOpacity(1);
        Dash.setOpacity(1);
        Needle.setOpacity(1);
        Dash.setRotate(0);
        Gear.setRotate(0);
    }
}
