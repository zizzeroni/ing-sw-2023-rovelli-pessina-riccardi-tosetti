package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.commongoal.Direction;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.view.*;
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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

/**
 *
 *
 *
 *
 */
public class MainSceneController implements Initializable {
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
    private Choice takenTiles;
    private int numberOfTakenTiles;
    private int numberOfSelectedTiles;
    private String[] playerName;
    private int firstRow;
    private int firstColumn;
    private Direction directionToCheck;

    /**
     * This method initializes the gui setting the images of the various {@code CommonGoal} cards,
     * the one of the {@code PersonalGoal}, those of the {@code Tile}s and the images of the rest of the
     * elements employed for the graphics of the scenes.
     *
     * @param url of the images to be set.
     * @param resourceBundle the bundle containing the graphical resources.
     *
     * @see it.polimi.ingsw.model.commongoal.CommonGoal
     * @see it.polimi.ingsw.model.PersonalGoal
     * @see it.polimi.ingsw.model.tile.Tile
     */
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

    /**
     * Permits the selection of a {@code Button} in a scene of the GUI
     *
     * @param actionEvent is the event associated to the button selection.
     *
     * @see Button
     */
    public void selected(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;
        String name = button.getId();
        int column = Integer.parseInt(String.valueOf(name.charAt(name.length() - 1)));
        int row = Integer.parseInt(String.valueOf(name.charAt(name.length() - 2)));

        if (button.getBorder() == null || button.getBorder().isEmpty()) {
            if (checkIfPickable(row, column)) {
                switch (takenTiles.getChosenTiles().size()) {
                    case 0 -> {
                        TileView tileView = mainGui.getModel().getBoard().getTiles()[row][column];
                        takenTiles.addTile(tileView);
                        takenTiles.addCoordinates(new Coordinates(row, column));

                        Border border = new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));
                        button.setBorder(border);

                    }
                    case 1 -> {
                        Direction res = checkIfInLine(row, column, firstRow, firstColumn);
                        if (res != null) {
                            directionToCheck = res;
                            TileView tileView = mainGui.getModel().getBoard().getTiles()[row][column];
                            takenTiles.addTile(tileView);
                            takenTiles.addCoordinates(new Coordinates(row, column));

                            Border border = new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));
                            button.setBorder(border);
                        }

                    }
                    case 2 -> {
                        if (checkIfInLine(row, column, takenTiles.getTileCoordinates(), directionToCheck)) {
                            TileView tileView = mainGui.getModel().getBoard().getTiles()[row][column];
                            takenTiles.addTile(tileView);
                            takenTiles.addCoordinates(new Coordinates(row, column));

                            Border border = new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));
                            button.setBorder(border);
                        }
                    }
                    case 3 -> {
                        if (button.getBorder() == null || button.getBorder().isEmpty()) {
                            System.err.println("Numero massimo di tiles scelto");
                        } else {
                            TileView tileView = mainGui.getModel().getBoard().getTiles()[row][column];
                            takenTiles.removeTile(tileView);
                        }
                    }
                }
                firstRow = takenTiles.getTileCoordinates().get(0).getX();
                firstColumn = takenTiles.getTileCoordinates().get(0).getY();
            }

        } else {
            TileView tileView = mainGui.getModel().getBoard().getTiles()[row][column];
            takenTiles.removeTile(tileView);

            button.setBorder(Border.EMPTY);
        }
    }

    /** Modifies the opacity of a {@code Button} when under the mouse cursor.
     *
     * @param mouseEvent the event linked to the mouse cursor.
     *
     * @see Button
     */
    public void overButton(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof Node node))
            return;

        node.setOpacity(0.5);
    }

    /** Adjusts the opacity of a {@code Button} when it is not under the mouse cursor,
     * resetting it to its original value.
     *
     * @param mouseEvent the event linked to the mouse cursor.
     *
     * @see Button
     */
    public void notOverButton(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof Node node))
            return;

        node.setOpacity(1);
    }

    /**
     * Used to signal if the mouse cursor is present over the
     * first {@code CommonGoal} of a {@code Player} and adapt
     * common goal visualization.
     *
     * @param mouseEvent is the event linked to mouse cursor movement over the first common goal.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.commongoal.CommonGoal
     */
    public void onCommonGoal1(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(240);
        imageView.setFitWidth(240);
        imageView.setLayoutX(500);
        imageView.setLayoutY(406);
        imageView.setViewOrder(0.0);
    }

    /**
     * Used to exit the detailed view of the
     * first {@code CommonGoal} of a {@code Player}.
     *
     * @param mouseEvent is the event linked to mouse cursor movement over the first common goal.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.commongoal.CommonGoal
     */
    public void exitCommonGoal1(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(110);
        imageView.setFitWidth(110);
        imageView.setLayoutX(559);
        imageView.setLayoutY(471);
        imageView.setViewOrder(1);
    }

    /**
     * Used to signal if the mouse cursor is present over the
     * second {@code CommonGoal} of a {@code Player} and adapt
     * common goal visualization.
     *
     * @param mouseEvent is the event linked to mouse cursor movement over the second common goal.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.commongoal.CommonGoal
     */
    public void onCommonGoal2(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(240);
        imageView.setFitWidth(240);
        imageView.setLayoutX(601);
        imageView.setLayoutY(406);
        imageView.setViewOrder(0.0);
    }

    /**
     * Used to exit the detailed view of the
     * second {@code CommonGoal} of a {@code Player}.
     *
     * @param mouseEvent is the event linked to mouse cursor movement over the second common goal.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.commongoal.CommonGoal
     */
    public void exitCommonGoal2(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(110);
        imageView.setFitWidth(110);
        imageView.setLayoutX(676);
        imageView.setLayoutY(471);
        imageView.setViewOrder(1);
    }

    /**
     * Presents the detailed view for the {@code PersonalGoal} when mouse
     * cursor passes over it.
     *
     * @param mouseEvent is the event linked to mouse cursor movement over a personal goal.
     *
     * @see it.polimi.ingsw.model.PersonalGoal
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
     * Exits the detailed view for the {@code PersonalGoal} when mouse
     * cursor is no more over it.
     *
     * @param mouseEvent is the event linked to mouse cursor movement over a personal goal.
     *
     * @see it.polimi.ingsw.model.PersonalGoal
     */
    public void exitPersonalGoal(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView imageView))
            return;

        imageView.setFitHeight(210);
        imageView.setFitWidth(286);
        imageView.setLayoutX(915);
        imageView.setLayoutY(560);
    }

    /**
     * Sets the {@code Button}s linking them to the various elements
     * displayed by the {@code GUI} on the table.
     *
     * @see Button
     * @see GUI
     */
    public void setTable() {
        firstColumn = 0;
        firstRow = 0;
        directionToCheck = null;
        takenTiles = new Choice();
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

    /**
     * Used to set the name of the {@code Button} in the correspondent {@code Tile} position.
     * It is also employed for setting the different style class of every tile.
     *
     * @param row the first coordinate of the considered tile.
     * @param column the second coordinate of the current tile.
     * @param tileId the identifier of the considered tile.
     * @param tileColor the color of the current tile.
     *
     * @see it.polimi.ingsw.model.tile.Tile
     * @see Button
     * @see Node#getStyleClass()
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

    /**
     * Blocks the selection a {@code Tile} that cannot be part of the
     * {@code Board} cause of the current settings of the {@code Game}
     * and the actual number of {@code Player}s in the lobby.
     *
     * @param row the first coordinate of the tile to be 'disabled'.
     * @param column the second coordinate of the tile to be 'disabled'.
     *
     * @see it.polimi.ingsw.model.tile.Tile
     * @see it.polimi.ingsw.model.Board
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
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
    /**
     * Blocks the selection a {@code Tile} removed after its picking.
     *
     * @param row the first coordinate of the tile to be 'disabled'.
     * @param column the second coordinate of the tile to be 'disabled'.
     *
     * @see it.polimi.ingsw.model.tile.Tile
     */
    public void disableTileAfterPick(int row, int column) {
        //Set the name of the button in the tile position
        tileName = "";
        tileName += "#boardTile";
        tileName += row;
        tileName += column;
        Button button = (Button) scene.lookup(tileName);
        if(button!=null) {
            button.setOnAction(null);
            button.setOnMouseEntered(null);
            button.setOnMouseExited(null);
            button.setOpacity(0.6);
        }

    }
    /**
     * Enable the selection of a given {@code Tile} after it has
     * been disabled (for {@code Game} settings od pinking).
     *
     * @param row the first coordinate of the tile to be 'disabled'.
     * @param column the second coordinate of the tile to be 'disabled'.
     *
     * @see it.polimi.ingsw.model.tile.Tile
     * @see it.polimi.ingsw.model.Game
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
     * Setter used for the MainGui
     *
     * @param gui the gui linked to the mainGui UI.
     */
    public void setMainGui(GUI gui) {
        this.mainGui = gui;
    }

    /**
     * Sets the current scene to the value of the given parameter.
     *
     * @param scene the scene to be set.
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Sets the number of {@code Player}s in the lobby to the given parameter
     * initialized during {@code Game} creation through a {@code GUI} request.
     * It also sets the {@code playerName} dimension to make it correspond to
     * the {@code numberOfPlayers}.
     *
     * @param numberOfPlayers the selected number of players.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     * @see GUI
     * @see MainSceneController#playerName
     */
    public void setNumberOfPlayer(int numberOfPlayers) {
        this.numberOfPlayer = numberOfPlayers;
        playerName = new String[numberOfPlayers];
    }

    /**
     * Sets each member of the playerName array to its
     * correspondent value.
     *
     * @param players is the list of names of the players participating
     *                the active {@code Game}.
     *
     * @see it.polimi.ingsw.model.Game
     * @see it.polimi.ingsw.model.Player
     * @see MainSceneController#playerName
     */
    public void setPlayersName(List<PlayerView> players) {
        for (int i = 0; i < numberOfPlayer; i++) {
            playerName[i] = players.get(i).getNickname();
        }
    }

    /**
     * Sets the right {@code PersonalGoal} for each active
     * {@code PLayer} in the current {@code Game}'s lobby.
     * It also links every personal goal to the respective image
     * from the graphics resources.
     *
     * @param personalGoal is the personal goal to be set.
     *
     * @see it.polimi.ingsw.model.PersonalGoal
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     */
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

    /**
     * Sets the right {@code CommonGoal}s for each active
     * {@code PLayer} in the current {@code Game}'s lobby.
     * It also links every goal to the respective image
     * from the graphics resources.
     *
     * @param commonGoals is the list of common goals to be set.
     *
     * @see it.polimi.ingsw.model.PersonalGoal
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     */
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

    /**
     * This method links the selection {@code Tile} method to the representation
     * of the tile's selection in the GUI.
     *
     * @param actionEvent the event linked to the selection of a tile.
     *
     * @see it.polimi.ingsw.model.tile.Tile
     * @see Choice#getTileCoordinates()
     */
    public void SelectTiles(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;
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
            buttonTile.getStyleClass().remove(style);
            buttonTile.setOpacity(0);
            buttonTile.setOnAction(null);
            buttonTile.setOnMouseEntered(null);
            buttonTile.setOnMouseExited(null);
            buttonTile.setBorder(Border.EMPTY);
        }
        for (int r = 0; r < mainGui.getModel().getBoard().getNumberOfRows(); r++) {
            for (int c = 0; c < mainGui.getModel().getBoard().getNumberOfColumns(); c++) {
                disableTileAfterPick(r, c);
            }
        }
    }

    /**
     * Checks if the first two tiles chosen by the {@code Player} are present in the same direction.
     * The HORIZONTAL label is referred to the presence of the second {@code Tile}
     * of the chosen set from in row.
     * The VERTICAL label is referred to the presence of the second {@code Tile}
     * of the chosen set from in column.
     * Verify if the {@code Player} commits mistakes during selection signaling them
     * by printing a series of control errors.
     *
     * @param row the row in selection.
     * @param column the column in selection.
     * @param firstRow the inspection starting row.
     * @param firstColumn the inspection starting column.
     * @return the direction of the two current tiles chosen.
     */
    private Direction checkIfInLine(int row, int column, int firstRow, int firstColumn) {
        if (row == firstRow && column == firstColumn) {
            System.err.println("Non puoi scegliere di nuovo una tessera già scelta, riprova!");
            return null;
        }
        if ((row == firstRow) && (column - 1 == firstColumn || column + 1 == firstColumn)) {
            return Direction.HORIZONTAL;
        }
        if ((column == firstColumn) && (row - 1 == firstRow || row + 1 == firstRow)) {
            return Direction.VERTICAL;
        }
        System.err.println("Le tessere selezionate devono formare una linea retta ed essere adiacenti, riprova!");
        return null;
    }

    /**
     * Check if the {@code Tile} chosen by the {@code Player} is already
     * present in the current selection at the given coordinates and
     * if the {@code Player} has properly inserted all the tiles in the {@code Board}.
     *
     * @param row the row in selection.
     * @param column the column in selection.
     * @param prevTilesCoordinates the coordinates of the Tiles which have already been placed on the {@code Board}.
     * @param directionToCheck the direction (on row/column) which is selected for inspection.
     * @return {@code true} if and only if the {@code Tile}
     *          has already been placed in a previous turn.
     */
    private boolean checkIfInLine(int row, int column, List<Coordinates> prevTilesCoordinates, Direction
            directionToCheck) {
        if (prevTilesCoordinates.contains(new Coordinates(row, column))) {
            System.err.println("Non puoi scegliere di nuovo una tessera già scelta, riprova!");
            return false;
        }
        switch (directionToCheck) {
            case HORIZONTAL -> {
                if (row != prevTilesCoordinates.get(0).getX()) {
                    System.err.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
                    return false;
                } else {
                    for (Coordinates coordinates : prevTilesCoordinates) {
                        if (coordinates.getY() == column + 1 || coordinates.getY() == column - 1) {
                            return true;
                        }
                    }
                    System.err.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
                }
                return false;
            }
            case VERTICAL -> {
                if (column != prevTilesCoordinates.get(0).getY()) {
                    System.err.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
                    return false;
                } else {
                    for (Coordinates coordinates : prevTilesCoordinates) {
                        if (coordinates.getX() == row + 1 || coordinates.getX() == row - 1) {
                            return true;
                        }
                    }
                    System.err.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
                }
                return false;
            }
            default -> {
                System.err.println("Something went wrong, i didn't expected this value");
                return false;
            }
        }
    }

    /**
     * Used to check if at the given coordinates it is possible to pick up a {@code Tile}.
     * Verify if the {@code Player} commits mistakes during selection signaling them
     * by printing a series of control errors.
     *
     * @param row is the row of the checked {@code Tile}.
     * @param column is the column of the checked {@code Tile}.
     * @return {@code true} if and only if the {@code Tile}'s can be picked,
     *         {@code false} otherwise.
     *
     * @see Tile
     */
    private boolean checkIfPickable(int row, int column) {
        BoardView board = mainGui.getModel().getBoard();
        TileView[][] boardMatrix = board.getTiles();

        if (boardMatrix[row][column] != null && boardMatrix[row][column].getColor() != null) {
            if ((row != 0 && (boardMatrix[row - 1][column] == null || boardMatrix[row - 1][column].getColor() == null)) ||
                    (row != board.getNumberOfRows() && (boardMatrix[row + 1][column] == null || boardMatrix[row + 1][column].getColor() == null)) ||
                    (column != board.getNumberOfColumns() && (boardMatrix[row][column + 1] == null || boardMatrix[row][column + 1].getColor() == null)) ||
                    (column != 0 && (boardMatrix[row][column - 1] == null || boardMatrix[row][column - 1].getColor() == null))) {
                return true;
            } else {
                System.err.println("Impossibile prendere la tessera (Ha tutti i lati occupati), riprova!");
            }
        } else {
            System.err.println("Non è presente nessuna tessera nella cella selezionata, riprova!");
        }
        return false;
    }

    /**
     * Signals if a MouseEvent occurs over a column.
     *
     * @param mouseEvent is the event linked to mouse cursor movement.
     */
    public void overColumn(MouseEvent mouseEvent) {


    }

    /**
     * Signals if there is no MouseEvent actually
     * occurring over a column.
     *
     * @param mouseEvent is the event linked to mouse cursor movement.
     */
    public void notOverColumn(MouseEvent mouseEvent) {
    }
}

