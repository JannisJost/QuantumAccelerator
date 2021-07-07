package ch.dragxfly.quantumaccelerator.style.logo;

import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

/**
 *
 * @author janni
 */
public class Needle extends SVGPath {

    public Needle(double height) {
        this.setVisible(false);
        setContent("M858.68,947.43c-.7-50.92,28.75-99.83,67-114.35,61.46-23.33,107-13.93,149,37.49,85.62,104.88,169.44,211.25,252.67,318,5.79,7.43,5.35,29.13-.6,34.08-8.56,7.11-27.6,10.6-37.4,5.84-120-58.34-239.29-118.3-358.55-178.23C887.39,1028.51,861.9,993.85,858.68,947.43Z");
        setFill(Color.rgb(0, 87, 109));
        setSize(height);
    }

    private void setSize(double height) {
        double originalWidth = prefWidth(-1);
        double originalHeight = prefHeight(originalWidth);
        double proportion = originalWidth / originalHeight;
        double scaleX = height / originalWidth * proportion;
        double scaleY = height / originalHeight;
        setScaleX(scaleX);
        setScaleY(scaleY);
    }
}
