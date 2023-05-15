package it.polimi.ingsw.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {
    private User sceneData;
    @FXML
    private ImageView commonGoal1;
    @FXML
    private ImageView commonGoal2;
    @FXML
    private ImageView personalGoal;
    @FXML
    private Pane secondPlayerBookshelf;
    @FXML
    private Pane thirdPlayerBookshelf;
    @FXML
    private Pane fourthPlayerBookshelf;
    @FXML
    private Label firstPlayerNickname;
    private String textFirstPlayerNickname;
    private String numberOfPlayer;
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
    public void setScene(){
        Stage stage = (Stage) firstPlayerNickname.getScene().getWindow();
        User user = (User) stage.getUserData();
        numberOfPlayer=user.getNumberOfPlayer();
        textFirstPlayerNickname=user.getFirstPlayerNickname();
        System.out.println("Tu ti chiami: "+ textFirstPlayerNickname+", la partita è fatta da: "+ numberOfPlayer+" Giocatori");
        this.setTable();
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
        imageView.setLayoutX(500);
        imageView.setLayoutY(406);
        imageView.setViewOrder(0.0);
    }
    public void ExitCommonGoal1(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(100);
        imageView.setFitWidth(110);
        imageView.setLayoutX(559);
        imageView.setLayoutY(471);
        imageView.setViewOrder(1);
    }
    public void OnCommonGoal2(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(220);
        imageView.setFitWidth(240);
        imageView.setLayoutX(601);
        imageView.setLayoutY(406);
        imageView.setViewOrder(0.0);
    }
    public void ExitCommonGoal2(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(100);
        imageView.setFitWidth(110);
        imageView.setLayoutX(676);
        imageView.setLayoutY(471);
        imageView.setViewOrder(1);
    }
    public void OnPersonalGoal(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(315);
        imageView.setFitWidth(429);
        imageView.setLayoutX(844);
        imageView.setLayoutY(450);
    }
    public void ExitPersonalGoal(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(210);
        imageView.setFitWidth(286);
        imageView.setLayoutX(915);
        imageView.setLayoutY(560);
    }
    public void setTable(){
        commonGoal1.setVisible(false);
        if (fourthPlayerBookshelf == null)
            return;
        else if (thirdPlayerBookshelf == null)
            return;
        if(!numberOfPlayer.equals("4")){
            fourthPlayerBookshelf.setVisible(false);
            if (!numberOfPlayer.equals("3")) {
                thirdPlayerBookshelf.setVisible(false);
            }
        }
        firstPlayerNickname.setText(textFirstPlayerNickname);
    }
}
