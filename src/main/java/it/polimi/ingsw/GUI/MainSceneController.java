package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Board;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private Board board;
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
    @FXML
    private Button selected1;
    @FXML
    private Button selected2;
    @FXML
    private Button selected3;
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

//        boardTile15.getStyleClass().add("cat1");
//        boardTile33.getStyleClass().add("cat1");
//        boardTile25.getStyleClass().add("cat1");


//        for(int row=0; row< 9; row++) {
//            for (int column = 0; column < 9; column++) {
//                if (getBoardButton(row, column).isDefaultButton()) {
//                    System.out.println(row+"  " +column);
//                    getBoardButton(row,column).setStyle("");
//                }
//            }
//        }
        
    }
//    public void Take(){
//        String taken = boardTile15.getStyleClass().get(1);
//        selected1.setOpacity(1);
//        boardTile84.getStyleClass().add(taken);
//        selected1.getStyleClass().add(taken);
//        boardTile15.getStyleClass().remove(1);
//    }
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
    public void exitCommonGoal1(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(100);
        imageView.setFitWidth(110);
        imageView.setLayoutX(559);
        imageView.setLayoutY(471);
        imageView.setViewOrder(1);
    }
    public void onCommonGoal2(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(220);
        imageView.setFitWidth(240);
        imageView.setLayoutX(601);
        imageView.setLayoutY(406);
        imageView.setViewOrder(0.0);
    }
    public void exitCommonGoal2(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(100);
        imageView.setFitWidth(110);
        imageView.setLayoutX(676);
        imageView.setLayoutY(471);
        imageView.setViewOrder(1);
    }
    public void onPersonalGoal(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(315);
        imageView.setFitWidth(429);
        imageView.setLayoutX(844);
        imageView.setLayoutY(450);
    }
    public void exitPersonalGoal(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(210);
        imageView.setFitWidth(286);
        imageView.setLayoutX(915);
        imageView.setLayoutY(560);
    }
    public void setTable(){
        String name="";
        Scene scene=selected1.getScene();
        for(int row=0; row<9; row++){
            for(int column=0; column<9; column++){
                name="";
                name+="#boardTile";
                name+=row;
                name+=column;

                Button button = (Button) scene.lookup(name);
                if (button != null) {
                    System.out.println("Bottone "+ row + column);

                    button.getStyleClass().add("cat1");
                }

            }
        }
        if (fourthPlayerBookshelf == null)
            return;
        else if (thirdPlayerBookshelf == null)
            return;
        if(!numberOfPlayer.equals("4")){
            Button fouPlayerButtons = (Button) scene.lookup("#boardTile31");
            fouPlayerButtons.setVisible(false);
            fouPlayerButtons = (Button) scene.lookup("#boardTile40");
            fouPlayerButtons.setVisible(false);
            fouPlayerButtons = (Button) scene.lookup("#boardTile62");
            fouPlayerButtons.setVisible(false);
            fouPlayerButtons = (Button) scene.lookup("#boardTile73");
            fouPlayerButtons.setVisible(false);
            fouPlayerButtons = (Button) scene.lookup("#boardTile84");
            fouPlayerButtons.setVisible(false);
            fouPlayerButtons = (Button) scene.lookup("#boardTile57");
            fouPlayerButtons.setVisible(false);
            fouPlayerButtons = (Button) scene.lookup("#boardTile48");
            fouPlayerButtons.setVisible(false);
            fouPlayerButtons = (Button) scene.lookup("#boardTile15");
            fouPlayerButtons.setVisible(false);
            fourthPlayerBookshelf.setVisible(false);
            if (!numberOfPlayer.equals("3")) {
                Button threePlayerButtons = (Button) scene.lookup("#boardTile03");
                threePlayerButtons.setVisible(false);
                threePlayerButtons = (Button) scene.lookup("#boardTile22");
                threePlayerButtons.setVisible(false);
                threePlayerButtons = (Button) scene.lookup("#boardTile50");
                threePlayerButtons.setVisible(false);
                threePlayerButtons = (Button) scene.lookup("#boardTile62");
                threePlayerButtons.setVisible(false);
                threePlayerButtons = (Button) scene.lookup("#boardTile85");
                threePlayerButtons.setVisible(false);
                threePlayerButtons = (Button) scene.lookup("#boardTile66");
                threePlayerButtons.setVisible(false);
                threePlayerButtons = (Button) scene.lookup("#boardTile38");
                threePlayerButtons.setVisible(false);
                thirdPlayerBookshelf.setVisible(false);
            }
        }
        firstPlayerNickname.setText(textFirstPlayerNickname);
    }

    public void update(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;
        Stage stage = (Stage) button.getScene().getWindow();
        User user = (User) stage.getUserData();
        numberOfPlayer=user.getNumberOfPlayer();
        textFirstPlayerNickname=user.getFirstPlayerNickname();
        System.out.println("Tu ti chiami: "+ textFirstPlayerNickname+", la partita è fatta da: "+ numberOfPlayer+" Giocatori");
        this.setTable();

//        this.controlIfPickable();
    }
//    public void controlIfPickable(){
//        for(int row=0; row< 9; row++){
//            for(int column=0; column <9; column++){
//            if(getBoardButton(row, column)!=null&&!getBoardButton(row,column).isDefaultButton()){
//                if(     getBoardButton(row+1, column)==null||getBoardButton(row+1, column).isDefaultButton() ||
//                        getBoardButton(row, column+1)==null||getBoardButton(row, column+1).isDefaultButton() ||
//                        getBoardButton(row-1, column)==null||getBoardButton(row-1, column).isDefaultButton() ||
//                        getBoardButton(row, column-1)==null||getBoardButton(row, column-1).isDefaultButton()){
//                    getBoardButton(row, column).setOnAction(null);
//                    getBoardButton(row,column).setOpacity(0.5);
//                    getBoardButton(row, column).setOnMouseExited(null);
//                    getBoardButton(row, column).setOnMouseEntered(null);
//                }
//            }
//            }
//        }
//    }
//    public Button getBoardButton(int row, int column){
//        Button button = new Button();
//        button.setId("boardTile"+row+column);
//        return button;
//    }

//    public Button getButton(int playerNumber, int rowNumber, int columnNumber){
//        String paneName="";
//        if(playerNumber=='1'){
//            paneName+="first";
//        } else if (playerNumber=='2') {
//            paneName+="first";
//        } else if (playerNumber=='3') {
//            paneName+="third";
//        } else if (playerNumber=='4') {
//            paneName+="fourth";
//        }
//        paneName+="PlayerBookshelf";
//        paneName+=rowNumber;
//        paneName+=columnNumber;
//
//        Button button = new Button();
//        button.setId(paneName);
//        return button;
//    }

    public void InsertTile(ActionEvent actionEvent) {
    }
}
