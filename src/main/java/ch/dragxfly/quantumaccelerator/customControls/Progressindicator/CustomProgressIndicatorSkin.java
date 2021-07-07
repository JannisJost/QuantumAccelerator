package ch.dragxfly.quantumaccelerator.customControls.Progressindicator;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

/**
 *
 * @author janni inspired by torakiki -->
 * https://github.com/torakiki/fx-progress-circle
 */
public class CustomProgressIndicatorSkin implements Skin<CustomProgressIndicator> {

    private final CustomProgressIndicator indicator;
    private final StackPane wrapper = new StackPane();
    private final Label lblPercent = new Label();
    private final Arc progressArc = new Arc();
    private final ImageView img = new ImageView();
    private Timeline timeline = new Timeline();

    public CustomProgressIndicatorSkin(CustomProgressIndicator indicator) {
        this.indicator = indicator;
        initWrapper();
        initImg();
        initProgArc();
        initStyle();
        updateRadius();
        setProgressLabel(indicator.getProgress());
        this.indicator.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setProgressLabel(newValue.intValue());
            }
        });
        this.indicator.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue != oldValue) {
                    animateIndicator(newValue.intValue() * -3.6);
                    setProgressLabel(newValue.intValue());
                }
                updateRadius();
            }
        }
        );
        wrapper.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                progressArc.setCenterY(newValue.intValue() / 2);
            }
        });
        wrapper.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                progressArc.setCenterX(newValue.intValue() / 2);
            }
        });
        this.wrapper.getChildren().addAll(progressArc, img);
    }

    private void updateRadius() {
        progressArc.setRadiusX(indicator.getRadius() - progressArc.getStrokeWidth() * 2);
        progressArc.setRadiusY(indicator.getRadius() - progressArc.getStrokeWidth() * 2);
    }

    private void initProgArc() {
        progressArc.setManaged(false);
        progressArc.setStrokeType(StrokeType.CENTERED);
        progressArc.getStyleClass().add("arc-indicator");
        progressArc.setStartAngle(90);
        progressArc.setCenterY(wrapper.getHeight() / 2);
        progressArc.setCenterX(wrapper.getWidth() / 2);
        progressArc.setLength(indicator.getProgress() * -3.6);
    }

    private void initImg() {
        img.setImage(new Image(indicator.getImagePath()));
        img.setFitHeight(20);
        img.setFitWidth(20);
    }

    private void initWrapper() {
        wrapper.getStylesheets().addAll(indicator.getStylesheets());
        wrapper.getStyleClass().addAll("progindicator-wrapper");
        wrapper.setMaxHeight(indicator.getRadius());
        wrapper.setMaxWidth(indicator.getRadius());
    }

    private void initStyle() {
        lblPercent.getStyleClass().add("progidicator-percentage");
        progressArc.getStyleClass().add("progindicator-indicator");
    }

    private void setProgressLabel(int progress) {
        lblPercent.setText(Integer.toString(progress));
    }

    @Override
    public CustomProgressIndicator getSkinnable() {
        return indicator;
    }

    @Override
    public Node getNode() {
        return wrapper;
    }

    @Override
    public void dispose() {
        if (timeline.getStatus() != Status.STOPPED) {
            timeline.stop();
        }
    }

    private void animateIndicator(double toValue) {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(progressArc.lengthProperty(), toValue))
        );
        timeline.play();
    }
}
