package ch.dragxfly.quantumaccelerator.customControls;

import java.util.function.Function;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 *
 * @author jannis
 * @param <T>
 */
public class ButtonTableCell<T> extends TableCell<T, Button> {

    private final Button btn;

    public ButtonTableCell(String btnText, Function< T, T> function) {
        ImageView imgButton = new ImageView("/styles/icons/file/showMe.png");
        imgButton.setFitHeight(24);
        this.btn = new Button(btnText, imgButton);
        this.btn.setContentDisplay(ContentDisplay.LEFT);
        btn.setMaxWidth(Double.MAX_VALUE);
        this.btn.setOnAction((ActionEvent e) -> {
            function.apply((T) getTableView().getItems().get(getIndex()));
        });
    }

    public static <S> Callback<TableColumn<S, Button>, TableCell<S, Button>> forTableColumn(String btnText, Function< S, S> function) {
        return param -> new ButtonTableCell<>(btnText, function);
    }

    @Override
    public void updateItem(Button item, boolean isEmpty) {
        super.updateItem(item, isEmpty);
        if (isEmpty) {
            setGraphic(null);
        } else {
            setGraphic(btn);
        }
    }

}
