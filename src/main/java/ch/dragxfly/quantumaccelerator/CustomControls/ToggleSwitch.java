package ch.dragxfly.quantumaccelerator.CustomControls;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author janni
 */
public class ToggleSwitch extends Parent {

    private final double animationDurationInSecs = 0.25;
    private BooleanProperty switchedOn = new SimpleBooleanProperty(false);
    private TranslateTransition switchAnimation = new TranslateTransition(Duration.seconds(animationDurationInSecs));
    private FillTransition colorTransition = new FillTransition(Duration.seconds(animationDurationInSecs));
    private ParallelTransition animation = new ParallelTransition(switchAnimation, colorTransition);

    public ToggleSwitch() {
        //configures the background
        Rectangle background = new Rectangle(50, 25);
        background.setFill(Color.web("#7D7D7D"));
        background.setArcWidth(25);
        background.setArcHeight(50);
        //configures the thumb of the toggle switch
        Circle thumb = new Circle(12.5);
        thumb.setCenterX(12.5);
        thumb.setCenterY(12.5);
        thumb.setFill(Color.LIGHTGRAY);
        thumb.setStroke(Color.GRAY);
        switchAnimation.setNode(thumb);
        colorTransition.setShape(background);
        getChildren().addAll(background, thumb);
        switchedOn.addListener((obs, oldState, newState) -> {
            boolean isOn = newState;
            switchAnimation.setToX(isOn ? 50 - 25 : 0);
            colorTransition.setFromValue(isOn ? Color.web("#7D7D7D") : Color.web("#2EA664"));
            colorTransition.setToValue(isOn ? Color.web("#2EA664") : Color.web("#7D7D7D"));
            animation.play();
        });
        setOnMouseClicked(event -> {
            switchedOn.set(!switchedOn.getValue());
        });
    }

    public boolean getIsActivated() {
        return switchedOn.getValue();
    }

    public void setSwitchedOn(boolean isOn) {
        this.switchedOn.set(isOn);
    }

    public BooleanProperty getPropertySwitchedOn() {
        return switchedOn;
    }
}