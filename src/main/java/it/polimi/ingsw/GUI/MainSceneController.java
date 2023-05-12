package it.polimi.ingsw.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {
    @FXML
    ImageView commonGoal1;
    @FXML
    ImageView commonGoal2;

    @FXML
    ImageView personalGoal;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initialize the two common goal
        String firstCommonGoalNumber="";
        String secondCommonGoalNumber="";
        Image firstCommonGoalImage = new Image(getClass().getClassLoader().getResourceAsStream("Image/common goal cards/1.jpg"));
        Image secondCommonGoalImage = new Image(getClass().getClassLoader().getResourceAsStream("Image/common goal cards/2.jpg"));
        commonGoal2.setImage(firstCommonGoalImage);
        commonGoal1.setImage(secondCommonGoalImage);

        //Initialize the personal goal
        String personalGoalNumber="";
        Image personalGoalImage = new Image(getClass().getClassLoader().getResourceAsStream("Image/personal goal cards/Personal_Goals5.png"));
        personalGoal.setImage(personalGoalImage);


    }

    public void Selected(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;
        if (button.getBorder() == null || button.getBorder().isEmpty()) {
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

    public void OnCommonGoal1(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(220);
        imageView.setFitWidth(240);
        imageView.setLayoutX(559);
        imageView.setLayoutY(524);
    }
    public void ExitCommonGoal1(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(110);
        imageView.setFitWidth(120);
        imageView.setLayoutX(619);
        imageView.setLayoutY(584);
    }
    public void OnCommonGoal2(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(220);
        imageView.setFitWidth(240);
        imageView.setLayoutX(559);
        imageView.setLayoutY(624);
    }
    public void ExitCommonGoal2(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(110);
        imageView.setFitWidth(120);
        imageView.setLayoutX(619);
        imageView.setLayoutY(684);
    }
    public void OnPersonalGoal(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(360);
        imageView.setFitWidth(240);
        imageView.setLayoutX(697);
        imageView.setLayoutY(400);
    }
    public void ExitPersonalGoal(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(180);
        imageView.setFitWidth(120);
        imageView.setLayoutX(787);
        imageView.setLayoutY(584);
    }
}
