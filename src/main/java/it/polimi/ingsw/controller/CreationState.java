package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CreationState extends ControllerState {

    public CreationState(GameController controller) {
        super(controller);
    }

    @Override
    public void changeTurn() {
        //Game is in creation phase, so do nothing...
    }

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) {
        //Game is in creation phase, so do nothing...
    }

    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) {
        //Game is in creation phase, so do nothing...
    }

    @Override
    public void sendBroadcastMessage(String sender, String content) {
        //Game is in creation phase, so do nothing...
    }

    @Override
    public void addPlayer(String nickname) {
        Random rand = new Random();
        PersonalGoal randomPersonalGoal = this.controller.getPersonalGoal(rand.nextInt(this.controller.getNumberOfPersonalGoals()));

        Player newPlayer;
        if(this.controller.getModel().getPlayers().size()==0) {
            //REMINDER: Only for test purposes (i need a almost full bookshelf for testing the ending of the game), remember to delete
            Tile[][] temp = {
                    {null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                    {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                    {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                    {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                    {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                    {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
            newPlayer = new Player(nickname, true, randomPersonalGoal, new ArrayList<ScoreTile>(), new Bookshelf(temp));
        } else {
            newPlayer = new Player(nickname, true, randomPersonalGoal, new ArrayList<ScoreTile>(), new Bookshelf());
        }
        this.controller.getModel().addPlayer(newPlayer);

        if (this.controller.getNumberOfPlayersCurrentlyInGame() == this.controller.getModel().getNumberOfPlayersToStartGame()) {
            startGame();
        }
    }

    private void startGame() {
        //One way to randomize starting player
        //Random rand = new Random();
        //this.controller.getModel().setActivePlayerIndex(rand.nextInt(this.controller.getModel().getNumberOfPlayersToStartGame()));

        //Second way to randomize starting player, in this way we can keep track of which player started (the player in position 0)
        Collections.shuffle(this.controller.getModel().getPlayers());
        this.controller.getModel().setActivePlayerIndex(0);

        this.controller.getBoardPatterns().stream()
                .filter(boardPattern -> boardPattern.numberOfPlayers() == this.controller.getModel().getPlayers().size())
                .findFirst()
                .ifPresent(jsonBoardPattern -> this.controller.getModel().getBoard().setTiles(jsonBoardPattern));

        List<Tile> drawnTiles = this.controller.getModel().getBag().subList(0, this.controller.getModel().getBoard().numberOfTilesToRefill());
        this.controller.getModel().getBoard().addTiles(drawnTiles);

        this.controller.changeState(new OnGoingState(this.controller));
        this.controller.getModel().setGameState(OnGoingState.toEnum());
    }

    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) {
        if (chosenNumberOfPlayers >= 2 && chosenNumberOfPlayers <= 4) {
            if (this.controller.getModel().getNumberOfPlayersToStartGame() == 0) {
                this.controller.getModel().setNumberOfPlayersToStartGame(chosenNumberOfPlayers);
                if (this.controller.getModel().getPlayers().size() == this.controller.getModel().getNumberOfPlayersToStartGame()) {
                    startGame();
                }
            }
        } else {
            System.err.println("Unexpected value for number of lobby's players");
        }
    }

    public static GameState toEnum() {
        return GameState.IN_CREATION;
    }
}
