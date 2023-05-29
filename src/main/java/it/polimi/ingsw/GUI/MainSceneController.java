package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.commongoal.Direction;
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

import java.awt.desktop.SystemEventListener;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

public class MainSceneController implements Initializable {
    //Fa schifo
    private GUI mainGui;
    @FXML
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
    private String[] playerName;
    private int firstRow;
    private int firstColumn;
    private Direction directionToCheck;
    private String selectedColumn;
    private int[] order;
    private int startOrder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startOrder = 0;
        selectedColumn = "";
        Image firstCommonGoalImage = new Image(getClass().getClassLoader().getResourceAsStream("image/common goal cards/back.jpg"));
        Image secondCommonGoalImage = new Image(getClass().getClassLoader().getResourceAsStream("image/common goal cards/back.jpg"));
        commonGoal2.setImage(firstCommonGoalImage);
        commonGoal1.setImage(secondCommonGoalImage);

        Image personalGoalImage = new Image(getClass().getClassLoader().getResourceAsStream("image/personal goal cards/back.jpg"));
        personalGoal.setImage(personalGoalImage);

    }

    public void selected(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;
        Button buttonTakeTiles = (Button) scene.lookup("#insertTile");
        buttonTakeTiles.setOnAction(this::SelectTiles);
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
        startOrder = 0;
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
            disableFirstPlayerButton();
            countDownLatchTable.countDown();
        });
        try {
            countDownLatchTable.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

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
            }
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
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
        CountDownLatch countDownLatchAble = new CountDownLatch(1);
        Platform.runLater(() -> {
            String nickPlayer;
            int countOtherPlayer = 2;
            int countPlayer = 0;
            for (int i = 0; i < numberOfPlayer; i++) {
                if (!players.get(i).getNickname().equals(this.firstPlayerNickname.getText())) {
                    nickPlayer = "#nickname" + countOtherPlayer;
                    Label playerNickname = (Label) scene.lookup(nickPlayer);
                    playerNickname.setText(players.get(i).getNickname());
                    countOtherPlayer++;
                }
                playerName[countPlayer] = players.get(i).getNickname();
                countPlayer++;
            }
            countDownLatchAble.countDown();
        });
        try {
            countDownLatchAble.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPersonalGoal(PersonalGoalView personalGoal) {
        personalGoalString = "image/personal goal cards/";

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
        int firstCommonGoalID = commonGoals.get(0).getImageID();
        int secondCommonGoalID = commonGoals.get(1).getImageID();

        firstCommonGoalString= "image/common goal cards/"+firstCommonGoalID+".jpg";
        secondCommonGoalString= "image/common goal cards/"+secondCommonGoalID+".jpg";

        CountDownLatch countDownLatchAble = new CountDownLatch(1);
        Platform.runLater(() -> {
            Image firstCommonGoalImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(firstCommonGoalString)));
            Image secondCommonGoalImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(secondCommonGoalString)));

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
                buttonTile.getStyleClass().remove(style);
                buttonTile.setVisible(false);
            }
            order = new int[takenTiles.getChosenTiles().size()];
            for (int r = 0; r < mainGui.getModel().getBoard().getNumberOfRows(); r++) {
                for (int c = 0; c < mainGui.getModel().getBoard().getNumberOfColumns(); c++) {
                    disableTileAfterPick(r, c);
                }
            }
            ableFirstPlayerButton();
            button.setOnAction(null);
        } else {
            System.err.println("seleziona almeno una tile");
        }
    }

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

    public void overColumn(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof Button button))
            return;
        String buttonOfColumnName;
        Button buttonOfColumn;
        String name = button.getId();
        String column = String.valueOf(name.charAt(name.length() - 1));
       Border border = new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));
        PlayerView activePlayer = this.mainGui.getModel().getPlayers().stream().filter(player -> player.getNickname().equals(this.firstPlayerNickname.getText())).toList().get(0);
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

    public void notOverColumn(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof Button button))
            return;
        String buttonOfColumnName;
        Button buttonOfColumn;
        String name = button.getId();
        String column = String.valueOf(name.charAt(name.length() - 1));
        PlayerView activePlayer = this.mainGui.getModel().getPlayers().stream().filter(player -> player.getNickname().equals(this.firstPlayerNickname.getText())).toList().get(0);
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

    public void insertTileIntoBookshelf(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;
        String name = button.getId();
        order[startOrder] = Integer.parseInt(String.valueOf(name.charAt(name.length() - 1))) - 1;
        String style = button.getStyleClass().get(1);

        PlayerView activePlayer = this.mainGui.getModel().getPlayers().stream().filter(player -> player.getNickname().equals(this.firstPlayerNickname.getText())).toList().get(0);
        int row = 5-startOrder-activePlayer.getBookshelf().getNumberOfTilesInColumn(Integer.parseInt(selectedColumn));
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
            System.out.println("END TURN");

//            for (int i = 5-startOrder+activePlayer.getBookshelf().getNumberOfTilesInColumn(Integer.parseInt(selectedColumn)); i >= 0; i--) {
//                String buttonOfColumnName = "#firstPlayerTile" + i + selectedColumn;
//                Button buttonOfColumn = (Button) scene.lookup(buttonOfColumnName);
//                if (buttonOfColumn != null) {
//                    buttonOfColumn.setOpacity(0);
//                    buttonOfColumn.setBorder(null);
//                }
//            }
            mainGui.finishTurn(takenTiles);
        }
    }

    public void setFirstPlayerNickname(String nickname) {
        CountDownLatch countDownLatchAble = new CountDownLatch(1);
        Platform.runLater(() -> {
            this.firstPlayerNickname.setText(nickname);
            countDownLatchAble.countDown();
        });
        try {
            countDownLatchAble.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectColumn(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;

        String selectedButtonName;
        Button selectedButton;
        String name = button.getId();
        selectedColumn = String.valueOf(name.charAt(name.length() - 1));

        PlayerView activePlayer = this.mainGui.getModel().getPlayers().stream().filter(player -> player.getNickname().equals(this.firstPlayerNickname.getText())).toList().get(0);

        if (activePlayer.getBookshelf().getNumberOfEmptyCellsInColumn(Integer.parseInt(selectedColumn))<takenTiles.getChosenTiles().size()) {
            System.err.println("La colonna non è selezionabile");
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

    public void lockAllTiles() {
        for (int row = 0; row < mainGui.getModel().getBoard().getNumberOfRows(); row++) {
            for (int column = 0; column < mainGui.getModel().getBoard().getNumberOfColumns(); column++) {
                this.disableTile(row, column);
            }
        }
    }

//    public void setBookshelf(List<PlayerView> players) {
//        BookshelfView bookshelfFirstPlayer = players.stream().filter(player -> player.getNickname().equals(this.firstPlayerNickname.getText())).toList().get(0).getBookshelf();
//        for(int column = 0; column< bookshelfFirstPlayer.getNumberOfColumns(); column++){
//            for(int row = 0; row<bookshelfFirstPlayer.getNumberOfTilesInColumn(column); row++){
//                tileName = "#firstPlayerTile"+row+column;
//
//                //Add tile color and ID
//                tileStyle = bookshelfFirstPlayer.getTiles()[row][column].getColor().toGUI()
//                        + bookshelfFirstPlayer.getTiles()[row][column].getImageID();
//
//                CountDownLatch countDownLatchFirstPlayer = new CountDownLatch(1);
//
//                Platform.runLater(() -> {
//                    //Select the button in the tile position
//                    Button button = (Button) scene.lookup(tileName);
//                    if (button != null) {
//                        //Only for test
//                        //set tile color
//                        if (tileStyle.equals("B0")) {
//                            button.getStyleClass().add("B1");
//                        }
//                        if (tileStyle.equals("B2")) {
//                            button.getStyleClass().add("B2");
//                        }
//                        if (tileStyle.equals("B3")) {
//                            button.getStyleClass().add("B3");
//                        }
//                        if (tileStyle.equals("C0")) {
//                            button.getStyleClass().add("C1");
//                        }
//                        if (tileStyle.equals("C2")) {
//                            button.getStyleClass().add("C2");
//                        }
//                        if (tileStyle.equals("C3")) {
//                            button.getStyleClass().add("C3");
//                        }
//                        if (tileStyle.equals("G0")) {
//                            button.getStyleClass().add("G1");
//                        }
//                        if (tileStyle.equals("G2")) {
//                            button.getStyleClass().add("G2");
//                        }
//                        if (tileStyle.equals("G3")) {
//                            button.getStyleClass().add("G3");
//                        }
//                        if (tileStyle.equals("W0")) {
//                            button.getStyleClass().add("W1");
//                        }
//                        if (tileStyle.equals("W2")) {
//                            button.getStyleClass().add("W2");
//                        }
//                        if (tileStyle.equals("W3")) {
//                            button.getStyleClass().add("W3");
//                        }
//                        if (tileStyle.equals("P0")) {
//                            button.getStyleClass().add("P1");
//                        }
//                        if (tileStyle.equals("P2")) {
//                            button.getStyleClass().add("P2");
//                        }
//                        if (tileStyle.equals("P3")) {
//                            button.getStyleClass().add("P3");
//                        }
//                        if (tileStyle.equals("Y0")) {
//                            button.getStyleClass().add("Y1");
//                        }
//                        if (tileStyle.equals("Y2")) {
//                            button.getStyleClass().add("Y2");
//                        }
//                        if (tileStyle.equals("Y3")) {
//                            button.getStyleClass().add("Y3");
//                        }
//                    }
//                    countDownLatchFirstPlayer.countDown();
//                });
//                try {
//                    countDownLatchFirstPlayer.await();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }
}

