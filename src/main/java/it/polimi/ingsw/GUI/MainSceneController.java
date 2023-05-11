package it.polimi.ingsw.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MainSceneController {
    @FXML
    private Button button03;
    @FXML
    private Button button04;
    @FXML
    private Button button13;
    @FXML
    private Button button14;


    public void Selected(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;
        if (button.getBorder()==null||button.getBorder().isEmpty()) {
            Border border = new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));
            button.setBorder(border);
        } else {
            button.setBorder(Border.EMPTY);
        }
    }

    public void OverButton(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof Node node))
            return;

        node.setOpacity(0.5);
    }

    public void NotOverButton(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof Node node))
            return;

        node.setOpacity(1);
    }
}
