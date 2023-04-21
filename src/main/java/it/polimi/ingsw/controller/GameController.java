package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.view.TileView;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.reflect.TypeToken;

public class GameController implements ViewListener {
    private final Game model;

    //private final UI view;

    public GameController(Game model/*, UI view*/) {
        this.model = model;
        //this.view = view;
    }

    @Override
    public void changeTurn() {
        if (model.getBoard().numberOfTilesToRefill() != 0) {
            this.refillBoard();
        }

        if (model.getActivePlayerIndex() == model.getPlayers().size() - 1) {
            model.setActivePlayerIndex(0);
        } else {
            model.setActivePlayerIndex(model.getActivePlayerIndex() + 1);
        }
    }

    private void refillBoard() {
        Collections.shuffle(model.getBag());

        List<Tile> drawedTiles = model.getBag().subList(0, model.getBoard().numberOfTilesToRefill());
        model.getBoard().addTiles(drawedTiles);
        drawedTiles.clear();
    }

    private boolean checkIfUserInputIsCorrect(Choice choice) {
        List<TileView> choiceChoosenTiles = choice.getChosenTiles();
        int[] choiceTileOrder = choice.getTileOrder();
        int choiceColumn = choice.getChosenColumn();
        List<Coordinates> choiceTileCoordinates = choice.getTileCoordinates();

        if (choiceChoosenTiles.size() == choiceTileOrder.length && choiceTileOrder.length == choiceTileCoordinates.size()) {
            if (choiceColumn >= 0 && choiceColumn < model.getPlayers().get(0).getBookshelf().getNumberOfColumns()) {
                if (checkIfCoordinatesArePlausible(choiceTileCoordinates)) {
                    return true;
                }
            }
        }
        System.err.println("User input data are incorrect");
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
        Board board = model.getBoard();
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
            removeTilesFromBoard(playerChoice.getChosenTiles(), playerChoice.getTileCoordinates());
            addTilesToPlayerBookshelf(playerChoice.getChosenTiles(), playerChoice.getTileOrder(), playerChoice.getChosenColumn());
        } else {
            System.err.println("[ERROR]: User data not correct");
        }
    }

    @Override
    public void sendPrivateMessage(Player receiver, Player sender, String content) {
        String senderNickname = sender.getNickname();
        String receiverNickname = receiver.getNickname();

        //sender.addMessage(new Message(receiverNickname, senderNickname, content));
        //receiver.addMessage(new Message(receiverNickname, senderNickname, content));

    }

    @Override
    public void sendBroadcastMessage(Player sender, String content) {
        String senderNickname = sender.getNickname();

        for (Player player : this.model.getPlayers()) {
            //player.addMessage(new Message(player.getNickname(), senderNickname, content));
        }
    }

    @Override
    public void addPlayer(String nickname, int chosenNumberOfPlayers) {
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
        if (chosenNumberOfPlayers != 0) {
            this.model.setNumberOfPlayers(chosenNumberOfPlayers);
        }
        this.model.addPlayer(newPlayer);
        if (this.model.getPlayers().size() == this.model.getNumberOfPlayers()) {
            startGame();
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
                .ifPresent(jsonBoardPattern -> this.model.getBoard().setTiles(jsonBoardPattern));
        List<Tile> drawnTiles = this.model.getBag().subList(0, this.model.getBoard().numberOfTilesToRefill());
        this.model.getBoard().addTiles(drawnTiles);
        System.out.println(this.model.getBoard());
        System.out.println("Server arrivato a setStarted");
        this.model.setStarted(true);
    }

    private void removeTilesFromBoard(List<TileView> chosenTiles, List<Coordinates> tileCoordinates) {
        Board board = model.getBoard();
        int[] positions = new int[tileCoordinates.size() * 2];
        for (int i = 0; i < tileCoordinates.size() * 2; i++) {
            if (i % 2 == 0) {
                positions[i] = tileCoordinates.get(i / 2).getX();
            } else {
                positions[i] = tileCoordinates.get(i / 2).getY();
            }
        }
        Tile[] tilesToRemove = new Tile[chosenTiles.size()];
        for (int i = 0; i < chosenTiles.size(); i++) {
            tilesToRemove[0] = new Tile(chosenTiles.get(i).getColor());
        }
        board.removeTiles(tilesToRemove, positions);
    }

    private void addTilesToPlayerBookshelf(List<TileView> chosenTiles, int[] positions, int chosenColumn) {
        Bookshelf bookshelf = model.getPlayers().get(model.getActivePlayerIndex()).getBookshelf();
        for (int i = 0; i < chosenTiles.size(); i++) {
            bookshelf.addTile(new Tile(chosenTiles.get(positions[i]).getColor()), chosenColumn);
        }
    }
}
