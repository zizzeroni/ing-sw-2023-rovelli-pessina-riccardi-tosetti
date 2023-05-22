package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.PersonalGoalView;
import it.polimi.ingsw.model.view.PlayerView;
import it.polimi.ingsw.view.GUI;
import javafx.application.Platform;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

public class MainSceneController implements Initializable {
    //Fa schifo
    private GUI mainGui;
    @FXML
    private AnchorPane anchorPane;
    private User sceneData;
    private String tileName;
    private String tileStyle;
    private String personalGoalString;
    private String firstCommonGoalString;
    private String secondCommonGoalString;
    private Scene scene;
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
    private int numberOfPlayer;
    private String[] takenTiles;
    private int numberOfTakenTiles;
    private int numberOfSelectedTiles;
    private String[] playerName;

    //non riesco a capire come utilizzarla
    private String[] nameOfSelectedTiles;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image firstCommonGoalImage = new Image(getClass().getClassLoader().getResourceAsStream("Image/common goal cards/back.jpg"));
        Image secondCommonGoalImage = new Image(getClass().getClassLoader().getResourceAsStream("Image/common goal cards/back.jpg"));
        commonGoal2.setImage(firstCommonGoalImage);
        commonGoal1.setImage(secondCommonGoalImage);

        Image personalGoalImage = new Image(getClass().getClassLoader().getResourceAsStream("Image/personal goal cards/back.jpg"));
        personalGoal.setImage(personalGoalImage);

        numberOfTakenTiles = 0;

//        this.scene=personalGoal.getScene();

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
    public void selected(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;
        if (button.getBorder() == null || button.getBorder().isEmpty()) {
            if (checkIfPickable(button)) {
                this.numberOfSelectedTiles++;
                Border border = new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));
                button.setBorder(border);
                //Altrimenti si potrebbe far comparire per 2 secondi il contorno rosso
            }
        } else {
                this.numberOfSelectedTiles--;
            button.setBorder(Border.EMPTY);
        }
    }

    public void overButton(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof Node node))
            return;

        node.setOpacity(0.5);
    }

    public void notOverButton(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof Node node))
            return;

        node.setOpacity(1);
    }

    public void onCommonGoal1(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(240);
        imageView.setFitWidth(240);
        imageView.setLayoutX(500);
        imageView.setLayoutY(406);
        imageView.setViewOrder(0.0);
    }

    public void exitCommonGoal1(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(110);
        imageView.setFitWidth(110);
        imageView.setLayoutX(559);
        imageView.setLayoutY(471);
        imageView.setViewOrder(1);
    }

    public void onCommonGoal2(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(240);
        imageView.setFitWidth(240);
        imageView.setLayoutX(601);
        imageView.setLayoutY(406);
        imageView.setViewOrder(0.0);
    }

    public void exitCommonGoal2(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(110);
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

    public void setTable() {
        this.numberOfSelectedTiles=0;
        CountDownLatch countDownLatchTable = new CountDownLatch(1);

        Platform.runLater(() -> {
            if (fourthPlayerBookshelf == null)
                return;
            else if (thirdPlayerBookshelf == null)
                return;
            if (numberOfPlayer != 4) {
                Button fouPlayerButtons = (Button) scene.lookup("#boardTile31");
                fouPlayerButtons.setVisible(false);
                fouPlayerButtons = (Button) scene.lookup("#boardTile04");
                fouPlayerButtons.setVisible(false);
                fouPlayerButtons = (Button) scene.lookup("#boardTile40");
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
                if (numberOfPlayer != 3) {
                    Button threePlayerButtons = (Button) scene.lookup("#boardTile03");
                    threePlayerButtons.setVisible(false);
                    threePlayerButtons = (Button) scene.lookup("#boardTile22");
                    threePlayerButtons.setVisible(false);
                    threePlayerButtons = (Button) scene.lookup("#boardTile26");
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
            firstPlayerNickname.setText(playerName[0]);
            countDownLatchTable.countDown();
        });
        try {
            countDownLatchTable.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setBoardTile(int row, int column, int tileId, String tileColor) {
        //Set the name of the button in the tile position
        tileName = "";
        tileName += "#boardTile";
        tileName += row;
        tileName += column;

        //Set the style of the tile
        tileStyle = "";
        tileStyle += tileColor;
        tileStyle += tileId;
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Platform.runLater(() -> {
            //Select the button in the tile position
            Button button = (Button) scene.lookup(tileName);
            if (button != null) {
                //Only for test
                //set tile color
                if (tileStyle.equals("B0")) {
                    button.getStyleClass().add("B1");
                }
                if (tileStyle.equals("B2")) {
                    button.getStyleClass().add("B2");
                }
                if (tileStyle.equals("B3")) {
                    button.getStyleClass().add("B3");
                }
                if (tileStyle.equals("C0")) {
                    button.getStyleClass().add("C1");
                }
                if (tileStyle.equals("C2")) {
                    button.getStyleClass().add("C2");
                }
                if (tileStyle.equals("C3")) {
                    button.getStyleClass().add("C3");
                }
                if (tileStyle.equals("G0")) {
                    button.getStyleClass().add("G1");
                }
                if (tileStyle.equals("G2")) {
                    button.getStyleClass().add("G2");
                }
                if (tileStyle.equals("G3")) {
                    button.getStyleClass().add("G3");
                }
                if (tileStyle.equals("W0")) {
                    button.getStyleClass().add("W1");
                }
                if (tileStyle.equals("W2")) {
                    button.getStyleClass().add("W2");
                }
                if (tileStyle.equals("W3")) {
                    button.getStyleClass().add("W3");
                }
                if (tileStyle.equals("P0")) {
                    button.getStyleClass().add("P1");
                }
                if (tileStyle.equals("P2")) {
                    button.getStyleClass().add("P2");
                }
                if (tileStyle.equals("P3")) {
                    button.getStyleClass().add("P3");
                }
                if (tileStyle.equals("Y0")) {
                    button.getStyleClass().add("Y1");
                }
                if (tileStyle.equals("Y2")) {
                    button.getStyleClass().add("Y2");
                }
                if (tileStyle.equals("Y3")) {
                    button.getStyleClass().add("Y3");
                }
            }
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void disableTile(int row, int column) {
        //Set the name of the button in the tile position
        tileName = "";
        tileName += "#boardTile";
        tileName += row;
        tileName += column;

        CountDownLatch countDownLatchDisable = new CountDownLatch(1);
        Platform.runLater(() -> {
            //Select the button in the tile position
            Button button = (Button) scene.lookup(tileName);
            button.setOnAction(null);
            button.setOnMouseEntered(null);
            button.setOnMouseExited(null);
            button.setOpacity(0.6);
            countDownLatchDisable.countDown();
        });
        try {
            countDownLatchDisable.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void ableTile(int row, int column) {
        //Set the name of the button in the tile position
        tileName = "";
        tileName += "#boardTile";
        tileName += row;
        tileName += column;

        CountDownLatch countDownLatchAble = new CountDownLatch(1);
        Platform.runLater(() -> {
            Button button = (Button) scene.lookup(tileName);
            button.setOnAction(this::selected);
            button.setOnMouseEntered(this::overButton);
            button.setOnMouseExited(this::notOverButton);
            button.setOpacity(1);
            countDownLatchAble.countDown();
        });
        try {
            countDownLatchAble.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void setMainGui(GUI gui) {
        this.mainGui = gui;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setNumberOfPlayer(int numberOfPlayers) {
        this.numberOfPlayer = numberOfPlayers;
        playerName = new String[numberOfPlayers];
    }

    public void setPlayersName(List<PlayerView> players) {
        for (int i = 0; i < numberOfPlayer; i++) {
            playerName[i] = players.get(i).getNickname();
        }
    }

    public void setPersonalGoal(PersonalGoalView personalGoal) {
        personalGoalString = "Image/personal goal cards/";

        //Assegnare il giusto personal goal

        CountDownLatch countDownLatchAble = new CountDownLatch(1);
        Platform.runLater(() -> {
            Image personalGoalImage = new Image(getClass().getClassLoader().getResourceAsStream(personalGoalString));

            this.personalGoal.setImage(personalGoalImage);
            countDownLatchAble.countDown();
        });
        try {
            countDownLatchAble.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCommonGoal(List<CommonGoalView> commonGoals) {
        firstCommonGoalString = "Image/common goal cards/";
        secondCommonGoalString = "Image/common goal cards/";

        //Assegnare i giusti common goal

        CountDownLatch countDownLatchAble = new CountDownLatch(1);
        Platform.runLater(() -> {
            Image firstCommonGoalImage = new Image(getClass().getClassLoader().getResourceAsStream(firstCommonGoalString));
            Image secondCommonGoalImage = new Image(getClass().getClassLoader().getResourceAsStream(secondCommonGoalString));

            commonGoal2.setImage(firstCommonGoalImage);
            commonGoal1.setImage(secondCommonGoalImage);
            countDownLatchAble.countDown();
        });
        try {
            countDownLatchAble.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void SelectTiles(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;
        String style;
        int count=1;
        String selectedName;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                tileName = "#boardTile" + r + c;
                Button buttonTile = (Button) scene.lookup(tileName);
                if (buttonTile != null && buttonTile.getBorder() != null && !buttonTile.getBorder().isEmpty()) {
                    style = buttonTile.getStyleClass().get(1);
                    selectedName="#selected"+count;
                    Button selectedButton = (Button) scene.lookup(selectedName);
                    selectedButton.getStyleClass().add(style);
                    buttonTile.getStyleClass().remove(style);
                    buttonTile.setOnAction(null);
                    buttonTile.setOnMouseEntered(null);
                    buttonTile.setOnMouseExited(null);
                    buttonTile.setBorder(Border.EMPTY);
                    count++;
                }
            }
        }
        numberOfSelectedTiles=0;
    }

    public boolean checkIfPickable(Button button) {
        String name = button.getId();
        int lenght = name.length();
        String column = String.valueOf(name.charAt(lenght-1));
        String row = String.valueOf(name.charAt(lenght-2));

        if(this.numberOfSelectedTiles==0){
            return true;
        }
        if(this.numberOfSelectedTiles==3){
            return false;
        }
        if(numberOfSelectedTiles==1) {
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    tileName = "#boardTile" + r + c;
                    Button buttonTile = (Button) scene.lookup(tileName);
                    if (buttonTile != null && buttonTile.getBorder() != null && !buttonTile.getBorder().isEmpty()) {
                        if ((row.equals(String.valueOf(r + 1)) && column.equals(String.valueOf(c))) ||
                                (row.equals(String.valueOf(r - 1)) && column.equals(String.valueOf(c))) ||
                                (row.equals(String.valueOf(r)) && column.equals(String.valueOf(c + 1))) ||
                                (row.equals(String.valueOf(r)) && column.equals(String.valueOf(c - 1)))) {
                            return true;
                        }
                    }
                }
            }
        }
        if(numberOfSelectedTiles==2){
            //need help
        }
        return false;
    }
}

