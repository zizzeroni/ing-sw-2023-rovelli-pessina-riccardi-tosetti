package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.model.*;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.reflect.TypeToken;

public class GameController implements ViewListener {
    private final Game model;
    private ControllerState state;
    private List<PersonalGoal> personalGoalsDeck;
    private List<JsonBoardPattern> boardPatterns;

    public GameController(Game model) {
        this.model = model;
        this.state = new CreationState(this);
        this.personalGoalsDeck = new ArrayList<>();
        this.boardPatterns = new ArrayList<>();

        Gson gson = new Gson();
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get("src/main/java/it/polimi/ingsw/storage/personal-goals.json"));
            this.personalGoalsDeck = gson.fromJson(reader, new TypeToken<ArrayList<PersonalGoal>>() { }.getType());
            reader.close();

            reader = Files.newBufferedReader(Paths.get("src/main/java/it/polimi/ingsw/storage/boards.json"));
            this.boardPatterns = gson.fromJson(reader, new TypeToken<ArrayList<JsonBoardPattern>>() { }.getType());
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void changeState(ControllerState state) {
        this.state = state;
    }

    public ControllerState getState() {
        return state;
    }

    @Override
    public void changeTurn() {
        state.changeTurn();
    }

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) {
        state.insertUserInputIntoModel(playerChoice);
    }

    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) {
        state.sendPrivateMessage(receiver, sender, content);
    }

    @Override
    public void sendBroadcastMessage(String sender, String content) {
        state.sendBroadcastMessage(sender, content);
    }

    @Override
    public void addPlayer(String nickname) {
        state.addPlayer(nickname);
    }

    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) {
        state.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayers);
    }

    //------------------------------------UTILITY METHODS------------------------------------
    public Game getModel() {
        return this.model;
    }

    public int getNumberOfPlayersCurrentlyInGame() {
        return this.model.getPlayers().size();
    }

    public PersonalGoal getPersonalGoal(int index) {
        Collections.shuffle(personalGoalsDeck);
        return personalGoalsDeck.remove(index);
    }

    public int getNumberOfPersonalGoals() {
        return personalGoalsDeck.size();
    }

    public List<JsonBoardPattern> getBoardPatterns() {
        return this.boardPatterns;
    }

    //------------------------------------CHANGE TURN RELATED METHODS------------------------------------
    /*
    @Override
    public void changeTurn() {
        if (this.model.getBoard().numberOfTilesToRefill() != 0) {
            this.refillBoard();
        }
        if(checkIfGameEnded()) {

        }
        ChangeActivePlayer();
    }
    private void refillBoard() {
        Collections.shuffle(this.model.getBag());

        List<Tile> drawedTiles = this.model.getBag().subList(0, this.model.getBoard().numberOfTilesToRefill());
        this.model.getBoard().addTiles(drawedTiles);
        drawedTiles.clear();
    }
    private void ChangeActivePlayer() {
        if (this.model.getActivePlayerIndex() == this.model.getPlayers().size() - 1) {
            this.model.setActivePlayerIndex(0);
        } else {
            this.model.setActivePlayerIndex(this.model.getActivePlayerIndex() + 1);
        }
    }
    private boolean checkIfGameEnded() {
        return this.model.getPlayers().stream().map(Player::getBookshelf).filter(Bookshelf::isFull).count()==1;
    }
    private boolean checkIfUserInputIsCorrect(Choice choice) {
        List<TileView> choiceChoosenTiles = choice.getChosenTiles();
        int[] choiceTileOrder = choice.getTileOrder();
        int choiceColumn = choice.getChosenColumn();
        List<Coordinates> choiceTileCoordinates = choice.getTileCoordinates();

        Bookshelf currentPlayerBookshelf = this.model.getPlayers().get(this.model.getActivePlayerIndex()).getBookshelf();

        if (choiceChoosenTiles.size() == choiceTileOrder.length && choiceTileOrder.length == choiceTileCoordinates.size()) {
            if (choiceColumn >= 0 && choiceColumn < currentPlayerBookshelf.getNumberOfColumns() && currentPlayerBookshelf.getNumberOfEmptyCellsInColumn(choiceColumn)>=choiceChoosenTiles.size()) {
                if (checkIfCoordinatesArePlausible(choiceTileCoordinates)) {
                    return true;
                }
            }
        }
        System.err.println("[INPUT:ERROR] User input data are incorrect");
        return false;
    }

    private boolean checkIfCoordinatesArePlausible(List<Coordinates> coordinates) {
        for (Coordinates coordinate : coordinates) {
            if (!checkIfPickable(coordinate.getX(), coordinate.getY())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkIfPickable(int x, int y) {
        Board board = this.model.getBoard();
        Tile[][] boardMatrix = board.getTiles();

        return (boardMatrix[x][y] != null || boardMatrix[x][y].getColor() != null) && (
                (x != 0 && (boardMatrix[x - 1][y] == null || boardMatrix[x - 1][y].getColor() == null)) ||
                        (x != board.getNumberOfRows() && (boardMatrix[x + 1][y] == null || boardMatrix[x + 1][y].getColor() == null)) ||
                        (y != board.getNumberOfColumns() && (boardMatrix[x][y + 1] == null || boardMatrix[x][y + 1].getColor() == null)) ||
                        (y != 0 && (boardMatrix[x][y - 1] == null || boardMatrix[x][y - 1].getColor() == null)));
    }

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) {
        if (checkIfUserInputIsCorrect(playerChoice)) {
            removeTilesFromBoard(playerChoice.getTileCoordinates());
            addTilesToPlayerBookshelf(playerChoice.getChosenTiles(), playerChoice.getTileOrder(), playerChoice.getChosenColumn());
        } else {
            System.err.println("[INPUT:ERROR]: User data not correct");
        }
    }

    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) {
        //sender.addMessage(new Message(receiverNickname, senderNickname, content));
        //receiver.addMessage(new Message(receiverNickname, senderNickname, content));

    }

    @Override
    public void sendBroadcastMessage(String sender, String content) {

        for (Player player : this.model.getPlayers()) {
            //player.addMessage(new Message(player.getNickname(), senderNickname, content));
        }
    }

    @Override
    public void addPlayer(String nickname) {
        ArrayList<PersonalGoal> personalGoals = new ArrayList<PersonalGoal>();
        Gson gson = new Gson();
        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/java/it/polimi/ingsw/storage/personal-goals.json"));
            personalGoals = gson.fromJson(reader, new TypeToken<ArrayList<PersonalGoal>>() {
            }.getType());

            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Collections.shuffle(personalGoals);
        Player newPlayer = new Player(nickname, true, personalGoals.get(0), new ArrayList<ScoreTile>(), new Bookshelf());

        this.model.addPlayer(newPlayer);
        if (this.model.getPlayers().size() == this.model.getNumberOfPlayers()) {
            startGame();
        }
    }

    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) {
        if (chosenNumberOfPlayers >= 2 && chosenNumberOfPlayers <= 4) {
            if (this.model.getNumberOfPlayers() == 0) {
                this.model.setNumberOfPlayers(chosenNumberOfPlayers);
                if (this.model.getPlayers().size() == this.model.getNumberOfPlayers()) {
                    startGame();
                }
            }
        } else {
            System.err.println("Unexpected value for number of lobby's players");
        }
    }

    private void startGame() {
        Random rand = new Random();
        this.model.setActivePlayerIndex(rand.nextInt(this.model.getNumberOfPlayers()));


        List<JsonBoardPattern> boardPatterns = new ArrayList<JsonBoardPattern>();
        Gson gson = new Gson();
        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/java/it/polimi/ingsw/storage/boards.json"));
            boardPatterns = gson.fromJson(reader, new TypeToken<ArrayList<JsonBoardPattern>>() {
            }.getType());
            reader.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        boardPatterns.stream()
                .filter(boardPattern -> boardPattern.numberOfPlayers() == this.model.getPlayers().size())
                .findFirst()
                .ifPresent(jsonBoardPattern -> this.model.getBoard().setPattern(jsonBoardPattern));

        List<Tile> drawnTiles = this.model.getBag().subList(0, this.model.getBoard().numberOfTilesToRefill());
        this.model.getBoard().addTiles(drawnTiles);
        this.model.setStarted(true);
    }

    private void removeTilesFromBoard(List<Coordinates> tileCoordinates) {
        Board board = this.model.getBoard();
        board.removeTiles(tileCoordinates);
    }

    private void addTilesToPlayerBookshelf(List<TileView> chosenTiles, int[] positions, int chosenColumn) {
        Bookshelf bookshelf = this.model.getPlayers().get(this.model.getActivePlayerIndex()).getBookshelf();
        for (int i = 0; i < chosenTiles.size(); i++) {
            bookshelf.addTile(new Tile(chosenTiles.get(positions[i]).getColor()), chosenColumn);
        }
    }
     */
}
