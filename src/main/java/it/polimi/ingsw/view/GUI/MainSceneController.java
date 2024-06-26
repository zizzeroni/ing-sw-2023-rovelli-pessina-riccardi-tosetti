package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Message;
import it.polimi.ingsw.model.commongoal.Direction;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.view.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

/**
 * The controller associated with the GUI's main scene and employed in its management.
 */
public class MainSceneController implements Initializable {
    private GraphicalUI mainGraphicalUI;
    @FXML
    private String tileName;
    private String tileStyle;
    private String personalGoalString;
    private String firstCommonGoalString;
    private String secondCommonGoalString;
    private Scene scene;
    @FXML
    private ImageView victoryPoint;
    @FXML
    private ImageView pointsItem1;
    @FXML
    private ImageView pointsItem2;
    @FXML
    private ImageView commonGoal1;
    @FXML
    private ImageView commonGoal2;
    @FXML
    private ImageView personalGoal;
    @FXML
    private Pane censure;
    @FXML
    private Pane secondPlayerBookshelf;
    @FXML
    private Pane thirdPlayerBookshelf;
    @FXML
    private Pane fourthPlayerBookshelf;
    @FXML
    private Label firstPlayerNickname;
    @FXML
    private VBox VBoxMessage;
    @FXML
    private TextField chatMessage;
    @FXML
    private ChoiceBox<String> playerChatChoice;
    @FXML
    private Label countdownLabel;
    private int numberOfPlayer;
    private Choice takenTiles;
    private String[] playerName;
    private int firstRow;
    private int firstColumn;
    private Direction directionToCheck;
    private String selectedColumn;
    private int[] order;
    private int startOrder;
    @FXML
    private Label pointsLabel;
    private int turn;
    private Image pointsImage1;
    private Image pointsImage2;
    private boolean gameOn;
    private int inCensure = 0;

    private Thread printChatThread;

    /**
     * Getter of the inCensure "state"
     *
     * @return return the inCensure attribute value
     */
    public int getInCensure() {
        return inCensure;
    }

    /**
     * Initialize the resources for the main scene.
     *
     * @param url            the given url
     * @param resourceBundle the graphical bundle used in GUI's scene
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gameOn = true;
        this.turn = 0;
        startOrder = 0;
        selectedColumn = "";
        Image firstCommonGoalImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/common goal cards/back.jpg")));
        Image secondCommonGoalImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/common goal cards/back.jpg")));
        commonGoal1.setImage(firstCommonGoalImage);
        commonGoal2.setImage(secondCommonGoalImage);

        Image personalGoalImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/personal goal cards/back.jpg")));
        personalGoal.setImage(personalGoalImage);

        Image victoryImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/scoring tokens/end game.jpg")));
        victoryPoint.setImage(victoryImage);

        Image pointsImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/scoring tokens/scoring_8.jpg")));
        pointsItem1.setImage(pointsImage);
        pointsItem2.setImage(pointsImage);

        this.printChatThread = printChat();
        this.printChatThread.start();
    }

    /**
     * Effectuates the selection of a tile on the board
     *
     * @param actionEvent the selection of a tile.
     */
    public void selected(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;
        Button buttonTakeTiles = (Button) scene.lookup("#insertTile");
        buttonTakeTiles.setOnAction(this::SelectTiles);
        String name = button.getId();
        int column = Integer.parseInt(String.valueOf(name.charAt(name.length() - 1)));
        int row = Integer.parseInt(String.valueOf(name.charAt(name.length() - 2)));

        int maxNumberOfCellsFreeInBookshelf;
        //---------------------------------SCELTA COORDINATE TESSERE---------------------------------
        maxNumberOfCellsFreeInBookshelf = this.mainGraphicalUI.genericUILogic.getModel().getPlayers().get(this.mainGraphicalUI.genericUILogic.getModel().getActivePlayerIndex()).getBookshelf().getMaxNumberOfCellsFreeInBookshelf();


        if (button.getBorder() == null || button.getBorder().isEmpty()) {
            if (checkIfPickable(row, column)) {
                switch (takenTiles.getChosenTiles().size()) {
                    case 0 -> {
                        TileView tileView = mainGraphicalUI.genericUILogic.getModel().getBoard().getTiles()[row][column];
                        takenTiles.addTile(tileView);
                        takenTiles.addCoordinates(new Coordinates(row, column));

                        Border border = new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));
                        button.setBorder(border);
                        if (maxNumberOfCellsFreeInBookshelf == 1) {
                            this.endSelectionTiles();
                        }
                    }
                    case 1 -> {
                        Direction res = checkIfInLine(row, column, firstRow, firstColumn);
                        if (res != null) {
                            directionToCheck = res;
                            TileView tileView = mainGraphicalUI.genericUILogic.getModel().getBoard().getTiles()[row][column];
                            takenTiles.addTile(tileView);
                            takenTiles.addCoordinates(new Coordinates(row, column));

                            Border border = new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));
                            button.setBorder(border);
                            if (maxNumberOfCellsFreeInBookshelf == 2) {
                                this.endSelectionTiles();
                            }
                        }

                    }
                    case 2 -> {
                        if (checkIfInLine(row, column, takenTiles.getTileCoordinates(), directionToCheck)) {
                            TileView tileView = mainGraphicalUI.genericUILogic.getModel().getBoard().getTiles()[row][column];
                            takenTiles.addTile(tileView);
                            takenTiles.addCoordinates(new Coordinates(row, column));

                            Border border = new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));
                            button.setBorder(border);
                            this.endSelectionTiles();

                        }
                    }
                }
                firstRow = takenTiles.getTileCoordinates().get(0).getX();
                firstColumn = takenTiles.getTileCoordinates().get(0).getY();
            }

        } else {
            TileView tileView = mainGraphicalUI.genericUILogic.getModel().getBoard().getTiles()[row][column];
            takenTiles.removeTile(tileView);

            button.setBorder(Border.EMPTY);
        }
    }

    /**
     * Handles the correspondent mouse event described in the method name.
     *
     * @param mouseEvent the given mouse event.
     */
    public void overButton(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof Node node))
            return;

        node.setOpacity(0.5);
    }

    /**
     * Handles the correspondent mouse event described in the method name.
     *
     * @param mouseEvent the given mouse event.
     */
    public void notOverButton(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof Node node))
            return;

        node.setOpacity(1);
    }

    /**
     * Handles the correspondent mouse event described in the method name.
     *
     * @param mouseEvent the given mouse event.
     */
    public void onCommonGoal1(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(240);
        imageView.setFitWidth(240);
        imageView.setLayoutX(500);
        imageView.setLayoutY(406);
        imageView.setViewOrder(0.0);
        pointsItem2.setViewOrder(1);
        pointsItem1.setViewOrder(1);
        commonGoal2.setViewOrder(1);
        thirdPlayerBookshelf.setViewOrder(1);
    }

    /**
     * Handles the correspondent mouse event described in the method name.
     *
     * @param mouseEvent the given mouse event.
     */
    public void exitCommonGoal1(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(110);
        imageView.setFitWidth(110);
        imageView.setLayoutX(529);
        imageView.setLayoutY(454);
        imageView.setViewOrder(1);
        pointsItem2.setViewOrder(0.0);
        pointsItem1.setViewOrder(0.0);
        commonGoal2.setViewOrder(0.0);
        thirdPlayerBookshelf.setViewOrder(0.0);
    }

    /**
     * Handles the correspondent mouse event described in the method name.
     * Exits the first common goal image.
     *
     * @param mouseEvent the given mouse event.
     */
    public void onCommonGoal2(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(240);
        imageView.setFitWidth(240);
        imageView.setLayoutX(601);
        imageView.setLayoutY(406);
        imageView.setViewOrder(0.0);
        pointsItem2.setViewOrder(1);
        pointsItem1.setViewOrder(1);
        commonGoal1.setViewOrder(1);
        thirdPlayerBookshelf.setViewOrder(1);
    }

    /**
     * Handles the correspondent mouse event described in the method name.
     * Exits the second common goal.
     *
     * @param mouseEvent the given mouse event.
     */
    public void exitCommonGoal2(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(110);
        imageView.setFitWidth(110);
        imageView.setLayoutX(646);
        imageView.setLayoutY(454);
        imageView.setViewOrder(1);
        pointsItem2.setViewOrder(0.0);
        pointsItem1.setViewOrder(0.0);
        commonGoal1.setViewOrder(0.0);
        thirdPlayerBookshelf.setViewOrder(0.0);
    }

    /**
     * Handles the correspondent mouse event described in the method name.
     * Exits the second common goal.
     *
     * @param mouseEvent the given mouse event.
     */
    public void onPersonalGoal(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;
        imageView.setFitHeight(315);
        imageView.setFitWidth(429);
        imageView.setLayoutX(844);
        imageView.setLayoutY(450);
    }

    /**
     * Handles the correspondent mouse event described in the method name.
     * Exits the second common goal.
     *
     * @param mouseEvent the given mouse event.
     */
    public void exitPersonalGoal(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(210);
        imageView.setFitWidth(286);
        imageView.setLayoutX(885);
        imageView.setLayoutY(510);
    }

    /**
     * Sets the disposal of the various scene elements shown in the GUI,
     * considering their positioning, names, and other characteristics.
     */
    public void setTable() {
        startOrder = 0;
        firstColumn = 0;
        firstRow = 0;
        directionToCheck = null;
        takenTiles = new Choice();
        CountDownLatch countDownLatchTable = new CountDownLatch(1);
        PlayerView activePlayer = this.mainGraphicalUI.genericUILogic.getModel().getPlayers().stream().filter(player -> player.getNickname().equals(this.firstPlayerNickname.getText())).toList().get(0);
        int points = activePlayer.score();

        Platform.runLater(() -> {
            for (int c = 6; c >= 0; c--) {
                for (int r = 5; r >= 0; r--) {
                    String nome = "#firstPlayerTile" + r + c;
                    Button button = (Button) scene.lookup(nome);
                    if (button != null) {
                        if (activePlayer.getBookshelf().getTiles()[r][c] == null) {
                            button.setOpacity(0);
                            button.setBorder(null);
                        }
                    }
                }
            }


            for (int column = 0; column < activePlayer.getBookshelf().getNumberOfColumns(); column++) {
                for (int row = 5; row > 5 - activePlayer.getBookshelf().getNumberOfTilesInColumn(column); row--) {
                    tileStyle = activePlayer.getBookshelf().getTiles()[row][column].getColor().toGUI()
                            + activePlayer.getBookshelf().getTiles()[row][column].getId();
                    String nome = "#firstPlayerTile" + (row) + (column);
                    Button button = (Button) scene.lookup(nome);
                    if (button != null) {
                        if (button.getStyleClass().size() > 1) {
                            button.getStyleClass().remove(1);
                        }
                        button.setOpacity(1);
                        //set tile color
                        if (tileStyle.equals("B0")) {
                            button.getStyleClass().add("B1");
                        }
                        if (tileStyle.equals("B1")) {
                            button.getStyleClass().add("B2");
                        }
                        if (tileStyle.equals("B2")) {
                            button.getStyleClass().add("B3");
                        }
                        if (tileStyle.equals("C0")) {
                            button.getStyleClass().add("C1");
                        }
                        if (tileStyle.equals("C1")) {
                            button.getStyleClass().add("C2");
                        }
                        if (tileStyle.equals("C2")) {
                            button.getStyleClass().add("C3");
                        }
                        if (tileStyle.equals("G0")) {
                            button.getStyleClass().add("G1");
                        }
                        if (tileStyle.equals("G1")) {
                            button.getStyleClass().add("G2");
                        }
                        if (tileStyle.equals("G2")) {
                            button.getStyleClass().add("G3");
                        }
                        if (tileStyle.equals("W0")) {
                            button.getStyleClass().add("W1");
                        }
                        if (tileStyle.equals("W1")) {
                            button.getStyleClass().add("W2");
                        }
                        if (tileStyle.equals("W2")) {
                            button.getStyleClass().add("W3");
                        }
                        if (tileStyle.equals("P0")) {
                            button.getStyleClass().add("P1");
                        }
                        if (tileStyle.equals("P1")) {
                            button.getStyleClass().add("P2");
                        }
                        if (tileStyle.equals("P2")) {
                            button.getStyleClass().add("P3");
                        }
                        if (tileStyle.equals("Y0")) {
                            button.getStyleClass().add("Y1");
                        }
                        if (tileStyle.equals("Y1")) {
                            button.getStyleClass().add("Y2");
                        }
                        if (tileStyle.equals("Y2")) {
                            button.getStyleClass().add("Y3");
                        }
                    }

                }
            }
            pointsLabel.setText(String.valueOf(points));

            if (!this.firstPlayerNickname.getText().equals(this.mainGraphicalUI.genericUILogic.getModel().getPlayers().get(0).getNickname())) {
                ImageView imageView = (ImageView) scene.lookup("#chair1");
                imageView.setVisible(false);
            }

            if (turn == 0) {
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
                turn++;
            }
            disableFirstPlayerButton();
            countDownLatchTable.countDown();
        });
        try {
            countDownLatchTable.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cancels the actual tile's disposal on the game's board
     *
     * @param row    the selected row.
     * @param column the selected column.
     * @see it.polimi.ingsw.model.Game
     * @see it.polimi.ingsw.model.Board
     */
    public void cancelBoardTile(int row, int column) {
        tileName = "";
        tileName += "#boardTile";
        tileName += row;
        tileName += column;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            //Select the button in the tile position
            Button button = (Button) scene.lookup(tileName);
            if (button != null) {
                button.setVisible(false);
                if (button.getStyleClass().size() > 1) {
                    button.getStyleClass().remove(1);
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

    /**
     * Sets the tile's disposal on the GUI for the current game's board.
     *
     * @param row       the selected row.
     * @param column    the selected column.
     * @param tileId    the tile identifier.
     * @param tileColor the color of the considered tile.
     */
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
                button.setVisible(true);
                //set tile color
                if (tileStyle.equals("B0")) {
                    button.getStyleClass().add("B1");
                }
                if (tileStyle.equals("B1")) {
                    button.getStyleClass().add("B2");
                }
                if (tileStyle.equals("B2")) {
                    button.getStyleClass().add("B3");
                }
                if (tileStyle.equals("C0")) {
                    button.getStyleClass().add("C1");
                }
                if (tileStyle.equals("C1")) {
                    button.getStyleClass().add("C2");
                }
                if (tileStyle.equals("C2")) {
                    button.getStyleClass().add("C3");
                }
                if (tileStyle.equals("G0")) {
                    button.getStyleClass().add("G1");
                }
                if (tileStyle.equals("G1")) {
                    button.getStyleClass().add("G2");
                }
                if (tileStyle.equals("G2")) {
                    button.getStyleClass().add("G3");
                }
                if (tileStyle.equals("W0")) {
                    button.getStyleClass().add("W1");
                }
                if (tileStyle.equals("W1")) {
                    button.getStyleClass().add("W2");
                }
                if (tileStyle.equals("W2")) {
                    button.getStyleClass().add("W3");
                }
                if (tileStyle.equals("P0")) {
                    button.getStyleClass().add("P1");
                }
                if (tileStyle.equals("P1")) {
                    button.getStyleClass().add("P2");
                }
                if (tileStyle.equals("P2")) {
                    button.getStyleClass().add("P3");
                }
                if (tileStyle.equals("Y0")) {
                    button.getStyleClass().add("Y1");
                }
                if (tileStyle.equals("Y1")) {
                    button.getStyleClass().add("Y2");
                }
                if (tileStyle.equals("Y2")) {
                    button.getStyleClass().add("Y3");
                }
            }
            assert button != null;
            if (button.getStyleClass().size() > 2) {
                button.getStyleClass().remove(2);
            }
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Locks the selection of the tile in given position (for game's set up).
     *
     * @param row    the selected row.
     * @param column the selected column.
     */
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
            if (button != null) {
                button.setOnAction(null);
                button.setOnMouseEntered(null);
                button.setOnMouseExited(null);
                button.setOpacity(0.6);
            }
            countDownLatchDisable.countDown();
        });
        try {
            countDownLatchDisable.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Locks the selection of the tile in given position (for player's moves during the game's development
     * when already pick the tile).
     *
     * @param row    the selected row.
     * @param column the selected column.
     */
    public void disableTileAfterPick(int row, int column) {
        //Set the name of the button in the tile position
        tileName = "";
        tileName += "#boardTile";
        tileName += row;
        tileName += column;
        Button button = (Button) scene.lookup(tileName);
        if (button != null) {
            button.setOnAction(null);
            button.setOnMouseEntered(null);
            button.setOnMouseExited(null);
            button.setOpacity(0.6);
        }
    }

    /**
     * Permits to access a tile again after a previous call to some access restriction related methods.
     *
     * @param row    the selected row.
     * @param column the selected column.
     */
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

    /**
     * Sets the GUI for the Main Scene.
     *
     * @param graphicalUI the GUI to be set.
     */
    public void setMainGui(GraphicalUI graphicalUI) {
        this.mainGraphicalUI = graphicalUI;
    }

    /**
     * Sets the main scene
     *
     * @param scene the scene being set.
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Sets the number of player at the starting of the game.
     *
     * @param numberOfPlayers the number of players for the current game.
     * @see it.polimi.ingsw.model.Game
     */
    public void setNumberOfPlayer(int numberOfPlayers) {
        this.numberOfPlayer = numberOfPlayers;
        playerName = new String[numberOfPlayers + 1];
    }

    /**
     * Sets the nicknames for the given list of players.
     *
     * @param players the players list.
     */
    public void setPlayersName(List<PlayerView> players) {
        CountDownLatch countDownLatchAble = new CountDownLatch(1);
        Platform.runLater(() -> {
            String nickPlayer;
            String chair;
            int countOtherPlayer = 2;
            int countPlayer = 0;
            for (int i = 0; i < numberOfPlayer; i++) {
                if (!players.get(i).getNickname().equals(this.firstPlayerNickname.getText())) {
                    chair = "#chair" + countOtherPlayer;
                    nickPlayer = "#nickname" + countOtherPlayer;
                    Label playerNickname = (Label) scene.lookup(nickPlayer);
                    playerNickname.setText(players.get(i).getNickname());
                    //Controllare se il player è quello che ha iniziato
                    if (!players.get(i).getNickname().equals(this.mainGraphicalUI.genericUILogic.getModel().getPlayers().get(0).getNickname())) {
                        ImageView imageView = (ImageView) scene.lookup(chair);
                        imageView.setVisible(false);
                    }
                    countOtherPlayer++;
                }
                playerName[countPlayer] = players.get(i).getNickname();
                countPlayer++;
            }
            playerName[countPlayer] = "All";

            this.playerChatChoice.getItems().setAll(playerName);
            this.playerChatChoice.setValue("All");
            countDownLatchAble.countDown();
        });
        try {
            countDownLatchAble.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to set the personal goals linked to the main scene.
     *
     * @param personalGoal the given personal goal.
     */
    public void setPersonalGoal(PersonalGoalView personalGoal) {

        personalGoalString = "image/personal goal cards/Personal_Goals" + personalGoal.getId() + ".png";

        //Assegnare il giusto personal goal

        CountDownLatch countDownLatchAble = new CountDownLatch(1);
        Platform.runLater(() -> {
            Image personalGoalImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(personalGoalString)));

            this.personalGoal.setImage(personalGoalImage);
            countDownLatchAble.countDown();
        });
        try {
            countDownLatchAble.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to set the common goals list linked to the main scene.
     *
     * @param commonGoals the list of common goals.
     */
    public void setCommonGoal(List<CommonGoalView> commonGoals) {
        int firstCommonGoalID = commonGoals.get(0).getId();
        int secondCommonGoalID = commonGoals.get(1).getId();

        firstCommonGoalString = "image/common goal cards/" + firstCommonGoalID + ".jpg";
        secondCommonGoalString = "image/common goal cards/" + secondCommonGoalID + ".jpg";

        CountDownLatch countDownLatchAble = new CountDownLatch(1);
        Platform.runLater(() -> {
            Image firstCommonGoalImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(firstCommonGoalString)));
            Image secondCommonGoalImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(secondCommonGoalString)));

            commonGoal1.setImage(firstCommonGoalImage);
            commonGoal2.setImage(secondCommonGoalImage);
            countDownLatchAble.countDown();
        });
        try {
            countDownLatchAble.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Allows tiles selection in the main scene context.
     *
     * @param actionEvent the action event associated to tiles selection.
     */
    public void SelectTiles(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;

        if (takenTiles.getChosenTiles().size() != 0) {
            String style;
            String selectedName;
            int row;
            int column;
            int count;
            for (int i = 0; i < takenTiles.getChosenTiles().size(); i++) {
                row = takenTiles.getTileCoordinates().get(i).getX();
                column = takenTiles.getTileCoordinates().get(i).getY();

                tileName = "#boardTile" + row + column;
                Button buttonTile = (Button) scene.lookup(tileName);
                style = buttonTile.getStyleClass().get(1);
                count = i + 1;
                selectedName = "#selected" + count;
                Button selectedButton = (Button) scene.lookup(selectedName);
                selectedButton.getStyleClass().add(style);
                buttonTile.setBorder(null);
                buttonTile.getStyleClass().remove(1);
                buttonTile.setVisible(false);
            }
            order = new int[takenTiles.getChosenTiles().size()];
            for (int r = 0; r < mainGraphicalUI.genericUILogic.getModel().getBoard().getNumberOfRows(); r++) {
                for (int c = 0; c < mainGraphicalUI.genericUILogic.getModel().getBoard().getNumberOfColumns(); c++) {
                    disableTileAfterPick(r, c);
                }
            }
            ableFirstPlayerButton();
            button.setOnAction(null);
        }
    }

    /**
     * Used to indicate the end of the tiles selection phase.
     *
     * @see it.polimi.ingsw.model.tile.Tile
     */
    public void endSelectionTiles() {
        String style;
        String selectedName;
        int row;
        int column;
        int count;
        for (int i = 0; i < takenTiles.getChosenTiles().size(); i++) {
            row = takenTiles.getTileCoordinates().get(i).getX();
            column = takenTiles.getTileCoordinates().get(i).getY();

            tileName = "#boardTile" + row + column;
            Button buttonTile = (Button) scene.lookup(tileName);
            style = buttonTile.getStyleClass().get(1);
            count = i + 1;
            selectedName = "#selected" + count;
            Button selectedButton = (Button) scene.lookup(selectedName);
            selectedButton.getStyleClass().add(style);
            buttonTile.setBorder(null);
            buttonTile.getStyleClass().remove(1);
            buttonTile.setVisible(false);
        }
        order = new int[takenTiles.getChosenTiles().size()];
        for (int r = 0; r < mainGraphicalUI.genericUILogic.getModel().getBoard().getNumberOfRows(); r++) {
            for (int c = 0; c < mainGraphicalUI.genericUILogic.getModel().getBoard().getNumberOfColumns(); c++) {
                disableTileAfterPick(r, c);
            }
        }
        ableFirstPlayerButton();

        Button button = (Button) scene.lookup("#insertTile");
        button.setOnAction(null);

    }

    /**
     * Checks if the first two tiles chosen by the {@code Player} are present in the same direction.
     * The HORIZONTAL label is referred to the presence of the second {@code Tile}
     * of the chosen set from in row.
     * The VERTICAL label is referred to the presence of the second {@code Tile}
     * of the chosen set from in column.
     * Verify if the {@code Player} commit mistakes during selection.
     *
     * @param row         the row in selection.
     * @param column      the column in selection.
     * @param firstRow    the inspection starting row.
     * @param firstColumn the inspection starting column.
     * @return the direction of the two current tiles chosen.
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.tile.Tile
     */
    private Direction checkIfInLine(int row, int column, int firstRow, int firstColumn) {
        if (row == firstRow && column == firstColumn) {
            return null;
        }
        if ((row == firstRow) && (column - 1 == firstColumn || column + 1 == firstColumn)) {
            return Direction.HORIZONTAL;
        }
        if ((column == firstColumn) && (row - 1 == firstRow || row + 1 == firstRow)) {
            return Direction.VERTICAL;
        }
        return null;
    }

    /**
     * Checks if the first two tiles chosen by the {@code Player} are present in the same direction.
     * The HORIZONTAL label is referred to the presence of the second {@code Tile}
     * of the chosen set from in row.
     * The VERTICAL label is referred to the presence of the second {@code Tile}
     * of the chosen set from in column.
     * Verify if the {@code Player} commit mistakes during selection.
     *
     * @param row                  the row in selection.
     * @param column               the column in selection.
     * @param prevTilesCoordinates the coordinates of the previously chosen tiles.
     * @param directionToCheck     the direction of the two current tiles chosen.
     * @return {@code true} iff the check is successful, {@code false} otherwise.
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.tile.Tile
     * @see it.polimi.ingsw.model.Game
     */
    private boolean checkIfInLine(int row, int column, List<Coordinates> prevTilesCoordinates, Direction
            directionToCheck) {
        if (prevTilesCoordinates.contains(new Coordinates(row, column))) {
            return false;
        }
        switch (directionToCheck) {
            case HORIZONTAL -> {
                if (row != prevTilesCoordinates.get(0).getX()) {
                    return false;
                } else {
                    for (Coordinates coordinates : prevTilesCoordinates) {
                        if (coordinates.getY() == column + 1 || coordinates.getY() == column - 1) {
                            return true;
                        }
                    }
                }
                return false;
            }
            case VERTICAL -> {
                if (column != prevTilesCoordinates.get(0).getY()) {
                    return false;
                } else {
                    for (Coordinates coordinates : prevTilesCoordinates) {
                        if (coordinates.getX() == row + 1 || coordinates.getX() == row - 1) {
                            return true;
                        }
                    }
                }
                return false;
            }
            default -> {
                return false;
            }
        }
    }

    /**
     * Used to check if at the given coordinates it is possible to pick up a {@code Tile}.
     *
     * @param row    is the row of the checked {@code Tile}.
     * @param column is the column of the checked {@code Tile}.
     * @return {@code true} if and only if the {@code Tile}'s can be picked,
     * {@code false} otherwise.
     * @see Tile
     */
    private boolean checkIfPickable(int row, int column) {
        BoardView board = mainGraphicalUI.genericUILogic.getModel().getBoard();
        TileView[][] boardMatrix = board.getTiles();

        if (boardMatrix[row][column] != null && boardMatrix[row][column].getColor() != null) {
            return row == board.getNumberOfRows() - 1 || column == board.getNumberOfColumns() - 1 || (row != 0 && (boardMatrix[row - 1][column] == null || boardMatrix[row - 1][column].getColor() == null)) ||
                    (row != board.getNumberOfRows() && (boardMatrix[row + 1][column] == null || boardMatrix[row + 1][column].getColor() == null)) ||
                    (column != board.getNumberOfColumns() && (boardMatrix[row][column + 1] == null || boardMatrix[row][column + 1].getColor() == null)) ||
                    (column != 0 && (boardMatrix[row][column - 1] == null || boardMatrix[row][column - 1].getColor() == null));
        }
        return false;
    }

    /**
     * Identifies the presence of the mouse cursor over a column, then highlight all the free space in the column.
     *
     * @param mouseEvent the associated mouse event.
     */
    public void overColumn(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof Button button))
            return;
        String buttonOfColumnName;
        Button buttonOfColumn;
        String name = button.getId();
        String column = String.valueOf(name.charAt(name.length() - 1));
        Border border = new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));
        PlayerView activePlayer = this.mainGraphicalUI.genericUILogic.getModel().getPlayers().stream().filter(player -> player.getNickname().equals(this.firstPlayerNickname.getText())).toList().get(0);
        for (int i = 5; i >= 0; i--) {
            buttonOfColumnName = "#firstPlayerTile" + i + column;
            buttonOfColumn = (Button) scene.lookup(buttonOfColumnName);
            if (buttonOfColumn != null) {
                if (activePlayer.getBookshelf().getTiles()[i][Integer.parseInt(column)] == null) {
                    buttonOfColumn.setBorder(border);
                    buttonOfColumn.setOpacity(0.3);
                }
            }
        }
    }

    /**
     * Identifies the absence of the mouse cursor over a column, then cancel highlight all the free space in the column.
     *
     * @param mouseEvent the associated mouse event.
     */
    public void notOverColumn(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof Button button))
            return;
        String buttonOfColumnName;
        Button buttonOfColumn;
        String name = button.getId();
        String column = String.valueOf(name.charAt(name.length() - 1));
        PlayerView activePlayer = this.mainGraphicalUI.genericUILogic.getModel().getPlayers().stream().filter(player -> player.getNickname().equals(this.firstPlayerNickname.getText())).toList().get(0);
        for (int i = 5; i >= 0; i--) {
            buttonOfColumnName = "#firstPlayerTile" + i + column;
            buttonOfColumn = (Button) scene.lookup(buttonOfColumnName);
            if (buttonOfColumn != null) {
                if (activePlayer.getBookshelf().getTiles()[i][Integer.parseInt(column)] == null) {
                    buttonOfColumn.setOpacity(0);
                    buttonOfColumn.setBorder(null);
                }
            }
        }
    }

    /**
     * Allows to insert the selected tile in a bookshelf slot.
     *
     * @param actionEvent the event associated to tiles insertion.
     * @see it.polimi.ingsw.model.Bookshelf
     * @see Tile
     */
    public void insertTileIntoBookshelf(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;
        String name = button.getId();
        order[startOrder] = Integer.parseInt(String.valueOf(name.charAt(name.length() - 1))) - 1;
        String style = button.getStyleClass().get(1);

        PlayerView activePlayer = this.mainGraphicalUI.genericUILogic.getModel().getPlayers().stream().filter(player -> player.getNickname().equals(this.firstPlayerNickname.getText())).toList().get(0);
        int row = 5 - startOrder - activePlayer.getBookshelf().getNumberOfTilesInColumn(Integer.parseInt(selectedColumn));
        String firstPlayerTile = "#firstPlayerTile" + row + selectedColumn;
        Button firstPlayerButton = (Button) scene.lookup(firstPlayerTile);
        if (firstPlayerButton != null) {
            firstPlayerButton.getStyleClass().add(style);
            firstPlayerButton.setOpacity(1);
            firstPlayerButton.setBorder(null);
        }
        button.getStyleClass().remove(style);
        button.setOnAction(null);
        button.setOnMouseExited(null);
        button.setOnMouseEntered(null);
        button.setOpacity(1);
        startOrder++;

        if (startOrder == takenTiles.getChosenTiles().size()) {
            takenTiles.setChosenColumn(Integer.parseInt(selectedColumn));
            takenTiles.setTileOrder(order);
            mainGraphicalUI.finishTurn(takenTiles);
        }
    }

    /**
     * Sets the nickname of the first player joining the game's lobby.
     *
     * @param nickname the chosen nickname.
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     */
    public void setFirstPlayerNickname(String nickname) {
        CountDownLatch countDownLatchAble = new CountDownLatch(1);
        Platform.runLater(() -> {
            this.firstPlayerNickname.setText(nickname);
            //Se il firstPlayer è anche il primo giocatore lascio la sedia

            countDownLatchAble.countDown();
        });
        try {
            countDownLatchAble.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Allows column selection in the main scene context.
     *
     * @param actionEvent the action of column selection.
     */
    public void selectColumn(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;

        String selectedButtonName;
        Button selectedButton;
        String name = button.getId();
        selectedColumn = String.valueOf(name.charAt(name.length() - 1));

        PlayerView activePlayer = this.mainGraphicalUI.genericUILogic.getModel().getPlayers().stream().filter(player -> player.getNickname().equals(this.firstPlayerNickname.getText())).toList().get(0);

        if (activePlayer.getBookshelf().getNumberOfEmptyCellsInColumn(Integer.parseInt(selectedColumn)) < takenTiles.getChosenTiles().size()) {
        } else {
            for (int i = 1; i <= takenTiles.getChosenTiles().size(); i++) {
                selectedButtonName = "#selected" + i;
                selectedButton = (Button) scene.lookup(selectedButtonName);
                selectedButton.setOnAction(this::insertTileIntoBookshelf);
                selectedButton.setOnMouseEntered(this::overButton);
                selectedButton.setOnMouseExited(this::notOverButton);
            }
            disableFirstPlayerButton();
        }
    }

    /**
     * Disables the button selection related actions of the first player.
     *
     * @see it.polimi.ingsw.model.Player
     */
    private void disableFirstPlayerButton() {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                String buttonName = "#firstPlayerTile" + r + c;
                Button buttonDisable = (Button) scene.lookup(buttonName);
                if (buttonDisable != null && !buttonDisable.getStyleClass().get(0).isEmpty()) {
                    buttonDisable.setOnMouseEntered(null);
                    buttonDisable.setOnMouseExited(null);
                    buttonDisable.setOnAction(null);
                }
            }
        }
    }

    /**
     * Enables the button selection related actions of the first player.
     *
     * @see it.polimi.ingsw.model.Player
     */
    private void ableFirstPlayerButton() {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                String buttonName = "#firstPlayerTile" + r + c;
                Button buttonDisable = (Button) scene.lookup(buttonName);
                if (buttonDisable != null) {
                    buttonDisable.setOnMouseEntered(this::overColumn);
                    buttonDisable.setOnMouseExited(this::notOverColumn);
                    buttonDisable.setOnAction(this::selectColumn);
                }
            }
        }
    }

    /**
     * Locks the selection of all the tiles.
     *
     * @see Tile
     */
    public void lockAllTiles() {
        for (int r = 0; r < mainGraphicalUI.genericUILogic.getModel().getBoard().getNumberOfRows(); r++) {
            for (int c = 0; c < mainGraphicalUI.genericUILogic.getModel().getBoard().getNumberOfColumns(); c++) {
                this.disableTile(r, c);
            }
        }
    }

    /**
     * Locks the selection of all the tiles after the pick of the player.
     *
     * @see Tile
     */
    public void lockAllTilesAfterPick() {
        CountDownLatch countDownLatchAble = new CountDownLatch(1);
        Platform.runLater(() -> {
            for (int r = 0; r < mainGraphicalUI.genericUILogic.getModel().getBoard().getNumberOfRows(); r++) {
                for (int c = 0; c < mainGraphicalUI.genericUILogic.getModel().getBoard().getNumberOfColumns(); c++) {
                    this.disableTileAfterPick(r, c);
                }
            }
            countDownLatchAble.countDown();
        });
        try {
            countDownLatchAble.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the disposal of the player's bookshelf.
     *
     * @param players the list of players.
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Bookshelf
     */
    public void setBookshelf(List<PlayerView> players) {
        for (int i = 0; i < players.size() - 1; i++) {
            int playerNumber = i + 2;
            String nickPlayer = "#nickname" + playerNumber;
            Label playerNickname = (Label) scene.lookup(nickPlayer);
            BookshelfView bookshelfSecondPlayer = players.stream().filter(player -> player.getNickname().equals(playerNickname.getText())).toList().get(0).getBookshelf();
            for (int column = 0; column < bookshelfSecondPlayer.getNumberOfColumns(); column++) {
                for (int row = 5; row > 5 - bookshelfSecondPlayer.getNumberOfTilesInColumn(column); row--) {
                    if (playerNumber == 2) {
                        tileName = "#secondPlayerTile" + row + column;
                    } else if (playerNumber == 3) {
                        tileName = "#thirdPlayerTile" + row + column;
                    } else {
                        tileName = "#fourthPlayerTile" + row + column;
                    }
                    //Add tile color and ID
                    tileStyle = bookshelfSecondPlayer.getTiles()[row][column].getColor().toGUI()
                            + bookshelfSecondPlayer.getTiles()[row][column].getId();

                    CountDownLatch countDownLatchPlayer = new CountDownLatch(1);
                    Platform.runLater(() -> {
                        //Select the button in the tile position
                        Button button = (Button) scene.lookup(tileName);
                        if (button != null) {
                            button.setVisible(true);
                            button.setOpacity(1);
                            //set tile color
                            if (tileStyle.equals("B0")) {
                                button.getStyleClass().add("B1");
                            }
                            if (tileStyle.equals("B1")) {
                                button.getStyleClass().add("B2");
                            }
                            if (tileStyle.equals("B2")) {
                                button.getStyleClass().add("B3");
                            }
                            if (tileStyle.equals("C0")) {
                                button.getStyleClass().add("C1");
                            }
                            if (tileStyle.equals("C1")) {
                                button.getStyleClass().add("C2");
                            }
                            if (tileStyle.equals("C2")) {
                                button.getStyleClass().add("C3");
                            }
                            if (tileStyle.equals("G0")) {
                                button.getStyleClass().add("G1");
                            }
                            if (tileStyle.equals("G1")) {
                                button.getStyleClass().add("G2");
                            }
                            if (tileStyle.equals("G2")) {
                                button.getStyleClass().add("G3");
                            }
                            if (tileStyle.equals("W0")) {
                                button.getStyleClass().add("W1");
                            }
                            if (tileStyle.equals("W1")) {
                                button.getStyleClass().add("W2");
                            }
                            if (tileStyle.equals("W2")) {
                                button.getStyleClass().add("W3");
                            }
                            if (tileStyle.equals("P0")) {
                                button.getStyleClass().add("P1");
                            }
                            if (tileStyle.equals("P1")) {
                                button.getStyleClass().add("P2");
                            }
                            if (tileStyle.equals("P2")) {
                                button.getStyleClass().add("P3");
                            }
                            if (tileStyle.equals("Y0")) {
                                button.getStyleClass().add("Y1");
                            }
                            if (tileStyle.equals("Y1")) {
                                button.getStyleClass().add("Y2");
                            }
                            if (tileStyle.equals("Y2")) {
                                button.getStyleClass().add("Y3");
                            }
                        }
                        countDownLatchPlayer.countDown();
                    });
                    try {
                        countDownLatchPlayer.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }


    /**
     * Sets the points associated to the common goals.
     *
     * @param commonGoals the list of given common goals.
     * @see it.polimi.ingsw.model.commongoal.CommonGoal
     */
    public void setCommonGoalPoints(List<CommonGoalView> commonGoals) {
        //I take the number of score tiles remaining for both common goals
        int numberOfScoreTiles1 = commonGoals.get(0).getScoreTiles().size();
        int numberOfScoreTiles2 = commonGoals.get(1).getScoreTiles().size();
        if (numberOfScoreTiles1 != 0) {
            //I take the value of the first score tile of the first commonGoal
            int firstScoringTile = commonGoals.get(0).getScoreTiles().get(0).getValue();
            switch (firstScoringTile) {
                case 2 ->
                        pointsImage1 = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/scoring tokens/scoring_2.jpg")));
                case 4 ->
                        pointsImage1 = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/scoring tokens/scoring_4.jpg")));
                case 6 ->
                        pointsImage1 = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/scoring tokens/scoring_6.jpg")));
                case 8 ->
                        pointsImage1 = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/scoring tokens/scoring_8.jpg")));
                default ->
                        pointsImage1 = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/scoring tokens/scoring.jpg")));
            }
        } else {
            pointsImage1 = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/scoring tokens/scoring.jpg")));
        }
        if (numberOfScoreTiles2 != 0) {
            int firstScoringTile = commonGoals.get(1).getScoreTiles().get(0).getValue();
            switch (firstScoringTile) {
                case 2 ->
                        pointsImage2 = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/scoring tokens/scoring_2.jpg")));
                case 4 ->
                        pointsImage2 = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/scoring tokens/scoring_4.jpg")));
                case 6 ->
                        pointsImage2 = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/scoring tokens/scoring_6.jpg")));
                case 8 ->
                        pointsImage2 = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/scoring tokens/scoring_8.jpg")));
                default ->
                        pointsImage2 = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/scoring tokens/scoring.jpg")));
            }
        } else {
            pointsImage2 = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("image/scoring tokens/scoring.jpg")));
        }

        CountDownLatch countDownLatchCommonGoal = new CountDownLatch(1);
        Platform.runLater(() -> {
            pointsItem1.setImage(pointsImage1);
            pointsItem2.setImage(pointsImage2);
            countDownLatchCommonGoal.countDown();
        });
        try {
            countDownLatchCommonGoal.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Deletes the previous mouse event.
     *
     * @param mouseEvent the event to be deleted.
     */
    public void DeletePrevious(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof TextField node))
            return;

        node.setText("");
    }

    /**
     * Identifies the refresh point for the board and other graphical elements on the GUI.
     *
     * @see GraphicalUI
     */
    public void refreshPoint() {
        CountDownLatch countDownLatchCommonGoal = new CountDownLatch(1);
        Platform.runLater(() -> {
            PlayerView activePlayer = this.mainGraphicalUI.genericUILogic.getModel().getPlayers().stream().filter(player -> player.getNickname().equals(this.firstPlayerNickname.getText())).toList().get(0);
            int points = activePlayer.score();
            pointsLabel.setText(String.valueOf(points));
            countDownLatchCommonGoal.countDown();
        });
        try {
            countDownLatchCommonGoal.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Allows to send a message through the GUI's chat.
     *
     * @param actionEvent the event linked to the message sending.
     * @see GraphicalUI
     */
    public void sendMessage(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;

        String sender = this.firstPlayerNickname.getText();
        String message = this.chatMessage.getText();
        String receiver = (playerChatChoice.getValue());

        var th = new Thread(() -> {
            Platform.runLater(() -> {
                if (!message.isEmpty()) {
                    if (receiver.equals("All")) {
                        this.mainGraphicalUI.genericUILogic.getController().sendBroadcastMessage(sender, message);
                    } else {
                        this.mainGraphicalUI.genericUILogic.getController().sendPrivateMessage(sender, receiver, message);
                    }
                }
            });
        });
        th.setUncaughtExceptionHandler((t, e) -> {
            System.err.println(e);
            e.printStackTrace();
        });
        th.start();
        chatMessage.setText("");

    }

    /**
     * Signals that the current game is on going.
     *
     * @param gameOn the boolean used to change the current game's state.
     * @see it.polimi.ingsw.model.Game
     */
    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }


    /**
     * Start the censure of the game
     */
    public void startCensure() {
        if (inCensure == 0) {
            this.inCensure = this.inCensure + 1;
            //this.setTable();
            //onCountdown = true;
            double wi = this.mainGraphicalUI.getPrimaryStage().getWidth();
            double he = this.mainGraphicalUI.getPrimaryStage().getHeight();

            censure.setMaxSize(wi, he);
            censure.setOpacity(0.5);
            commonGoal1.setOnMouseEntered(null);
            commonGoal2.setOnMouseEntered(null);
            commonGoal1.setOnMouseExited(null);
            commonGoal2.setOnMouseExited(null);
            personalGoal.setOnMouseEntered(null);
            personalGoal.setOnMouseExited(null);
            if(wi < 1600) {
                countdownLabel.setLayoutX(wi * 0.55);
                countdownLabel.setLayoutY(he * 0.70);
            } else if(wi < 2000){
                countdownLabel.setLayoutX(wi * 0.45);
                countdownLabel.setLayoutY(he * 0.60);
            } else {
                countdownLabel.setLayoutX(wi * 0.25);
                countdownLabel.setLayoutY(he * 0.40);
            }

            personalGoal.setVisible(false);
            CountDownLatch countDownLatch = new CountDownLatch(1);
            Platform.runLater(() -> {
                countdownLabel.setText("Only 1 player remaining, \n" +
                        "waiting for other player to reconnect \n" +
                        mainGraphicalUI.genericUILogic.getCountdown() + " seconds to game end");
                countDownLatch.countDown();
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //this.threadCounter();
    }

    /**
     * End the censure of the game
     */
    public void endCensure() {
        if (this.inCensure == 1) {
            this.inCensure = 0;
        }
        censure.setMaxSize(1, 1);
        censure.setOpacity(0.0);
        commonGoal1.setOnMouseEntered(this::onCommonGoal1);
        commonGoal2.setOnMouseEntered(this::onCommonGoal2);
        commonGoal1.setOnMouseExited(this::exitCommonGoal1);
        commonGoal2.setOnMouseExited(this::exitCommonGoal2);
        personalGoal.setOnMouseEntered(this::onPersonalGoal);
        personalGoal.setOnMouseExited(this::exitPersonalGoal);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            countdownLabel.setText("");
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        personalGoal.setVisible(true);
        //}
    }

    /**
     * @return the Thread for print the chat message on the chat VBox
     */
    private Thread printChat() {
        return new Thread(() -> {
            while (true) {
                synchronized (this.mainGraphicalUI.genericUILogic.getLockState()) {
                    try {
                        this.mainGraphicalUI.genericUILogic.getLockState().wait();

                        List<Message> fullChat = this.mainGraphicalUI.genericUILogic.getModel().getPlayerViewFromNickname(this.firstPlayerNickname.getText()).getChat();
                        Platform.runLater(() -> {
                            VBoxMessage.getChildren().clear();
                            if (fullChat.size() != 0) {
                                int chatSize = Math.min(fullChat.size(), 30);
                                for (int i = fullChat.size() - 1; i >= fullChat.size() - chatSize; i--) {
                                    Text text = new Text(fullChat.get(i).toString());
                                    Font font = new Font(14);
                                    text.setFont(font);
                                    VBoxMessage.getChildren().add(0, text); // add on top
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}

