package ch.dragxfly.quantumaccelerator.CustomControls.progressindicator;

import com.sun.javafx.css.converters.SizeConverter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableDoubleProperty;
import javafx.css.StyleableProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 *
 * @author janni
 */
public class CustomProgressIndicator extends Control {

    private final ReadOnlyIntegerWrapper progress = new ReadOnlyIntegerWrapper(0);
    private final String imagePath;

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CustomProgressIndicatorSkin(this);
    }

    public CustomProgressIndicator(String imagePath) {
        this.imagePath = imagePath;
        this.getStyleClass().add("customProgIndicator");
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setStyleSheet(String pathToCSS) {
        try {
            this.getSkin().dispose();
        } catch (Exception e) {
            //do nothing
        }
        this.getStylesheets().clear();
        this.getStylesheets().add(CustomProgressIndicator.class.getResource(pathToCSS).toExternalForm());
    }

    public void setProgress(int progress) {
        this.progress.set(progress);
    }

    public ReadOnlyIntegerProperty progressProperty() {
        return progress.getReadOnlyProperty();
    }

    public int getProgress() {
        return this.progress.get();
    }

    public void setRadius(int radius) {
        radiusProperty().set(radius);
    }

    public DoubleProperty radiusProperty() {
        return radius;
    }

    public double getRadius() {
        return radius.get();
    }
    private final DoubleProperty radius = new StyleableDoubleProperty(38) {
        @Override
        public Object getBean() {
            return CustomProgressIndicator.this;
        }

        @Override
        public String getName() {
            return "radius";
        }

        @Override
        public CssMetaData<CustomProgressIndicator, Number> getCssMetaData() {
            return StyleableProperties.RADIUS;
        }
    };

    private static class StyleableProperties {

        private static final CssMetaData<CustomProgressIndicator, Number> RADIUS = new CssMetaData<CustomProgressIndicator, Number>(
                "-fx-radius", SizeConverter.getInstance(), 38) {

            @Override
            public boolean isSettable(CustomProgressIndicator n) {
                return n.radiusProperty() == null || !n.radiusProperty().isBound();
            }

            @Override
            public StyleableProperty<Number> getStyleableProperty(CustomProgressIndicator n) {
                return (StyleableProperty<Number>) n.radiusProperty();
            }
        };

        public static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(Control.getClassCssMetaData());
            styleables.add(RADIUS);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

}
