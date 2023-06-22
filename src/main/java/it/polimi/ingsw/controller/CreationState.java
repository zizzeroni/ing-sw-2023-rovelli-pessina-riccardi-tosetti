package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commongoal.*;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;

import java.util.*;

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
        Message message = new Message(MessageType.PRIVATE, receiver, sender, content);
        for (Player player : this.controller.getModel().getPlayers()) {
            if (player.getNickname().equals(receiver)) {
                player.addMessage(message);
            }
        }

    }

    @Override
    public void sendBroadcastMessage(String sender, String content) {
        for (Player player : this.controller.getModel().getPlayers()) {
            Message message = new Message(MessageType.BROADCAST, player.getNickname(), sender, content);
            player.addMessage(message);
        }

    }

    @Override
    public void addPlayer(String nickname) {
        Random randomizer = new Random();
        PersonalGoal randomPersonalGoal = this.controller.getPersonalGoal(randomizer.nextInt(this.controller.getNumberOfPersonalGoals()));

        Player newPlayer;
        /*if (this.controller.getModel().getPlayers().size() == 0) {
            //REMINDER: Only for test purposes (i need a almost full bookshelf for testing the ending of the game), remember to delete
            Tile[][] temp = {
                    {null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                    {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                    {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                    {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                    {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                    {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
            newPlayer = new Player(nickname, true, randomPersonalGoal, new ArrayList<ScoreTile>(), new Bookshelf(temp));
        } else {*/
        newPlayer = new Player(nickname, true, randomPersonalGoal, new ArrayList<>(), new Bookshelf());
        //}
        this.controller.getModel().addPlayer(newPlayer);
    }

    @Override
    public void tryToResumeGame() {
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
    }

    @Override
    public void startGame() {
        if (this.controller.getNumberOfPlayersCurrentlyInGame() == this.controller.getModel().getNumberOfPlayersToStartGame()) {
            Collections.shuffle(this.controller.getModel().getPlayers());

            this.controller.getBoardPatterns().stream()
                    .filter(boardPattern -> boardPattern.numberOfPlayers() == this.controller.getModel().getPlayers().size())
                    .findFirst()
                    .ifPresent(jsonBoardPattern -> this.controller.getModel().getBoard().setPattern(jsonBoardPattern));

            List<Tile> drawnTiles = this.controller.getModel().getBag().subList(0, this.controller.getModel().getBoard().numberOfTilesToRefill());
            this.controller.getModel().getBoard().addTiles(drawnTiles);


        /*REMINDER: I moved this piece of code from Game constructor without parameters because the scoreTile list initialization requires the number of player to play the game
                    Ask if it is ok or find an alternative way*/
            CommonGoal newCommonGoal;
            while (this.controller.getModel().getCommonGoals().size() != 2) {
                try {
                    newCommonGoal = this.getRandomCommonGoalSubclassInstance();
                    if (!this.controller.getModel().getCommonGoals().contains(newCommonGoal)) {
                        this.controller.getModel().getCommonGoals().add(newCommonGoal);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            //Initializing score tile list for each player, this is necessary in order to replace them later if a player complete a common goal
            for (Player player : this.controller.getModel().getPlayers()) {
                // the available score tiles in a game are one for each common goal plus the first finisher's score tile
                for (int i = 0; i < this.controller.getModel().getCommonGoals().size() + 1; i++) {
                    player.getGoalTiles().add(new ScoreTile(0));
                }
            }

            this.controller.changeState(new OnGoingState(this.controller));
            this.controller.getModel().setGameState(OnGoingState.toEnum());
        }
    }

    @Override
    public void disconnectPlayer(String nickname) {
        this.controller.addPersonalGoal(this.controller.getModel().getPlayerFromNickname(nickname).getPersonalGoal());
        this.controller.getModel().getPlayers().remove(this.controller.getModel().getPlayerFromNickname(nickname));
    }

    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) {
        if (chosenNumberOfPlayers >= 2 && chosenNumberOfPlayers <= 4) {
            if (this.controller.getModel().getPlayers().size() > chosenNumberOfPlayers) {
                System.err.println("Number of players in the lobby exceed the chosen one");
            } else {
                this.controller.getModel().setNumberOfPlayersToStartGame(chosenNumberOfPlayers);
            }
        } else {
            System.err.println("Unexpected value for number of lobby's players");
        }
    }

    private CommonGoal getRandomCommonGoalSubclassInstance() throws Exception {
        int numberOfPlayersToStartGame = this.controller.getModel().getNumberOfPlayersToStartGame();
        switch (this.controller.getRandomizer().nextInt(12)) {
            case 0 -> {
                return new TilesInPositionsPatternGoal(1, 1, CheckType.EQUALS, numberOfPlayersToStartGame, new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList(1, 1)),
                        new ArrayList<>(Arrays.asList(1, 1))
                )));
            }
            case 1 -> {
                return new MinEqualsTilesPattern(2, 2, CheckType.DIFFERENT, numberOfPlayersToStartGame, Direction.VERTICAL, 0);
            }
            case 2 -> {
                return new ConsecutiveTilesPatternGoal(3, 4, CheckType.EQUALS, numberOfPlayersToStartGame, 4);
            }
            case 3 -> {
                return new ConsecutiveTilesPatternGoal(4, 6, CheckType.EQUALS, numberOfPlayersToStartGame, 2);
            }
            case 4 -> {
                return new MinEqualsTilesPattern(5, 3, CheckType.INDIFFERENT, numberOfPlayersToStartGame, Direction.VERTICAL, 3);
            }
            case 5 -> {
                return new MinEqualsTilesPattern(6, 2, CheckType.DIFFERENT, numberOfPlayersToStartGame, Direction.HORIZONTAL, 0);
            }
            case 6 -> {
                return new MinEqualsTilesPattern(7, 4, CheckType.INDIFFERENT, numberOfPlayersToStartGame, Direction.HORIZONTAL, 2);
            }
            case 7 -> {
                return new FourCornersPatternGoal(8, 1, CheckType.EQUALS, numberOfPlayersToStartGame);
            }
            case 8 -> {
                return new EightShapelessPatternGoal(9, 1, CheckType.INDIFFERENT, numberOfPlayersToStartGame);
            }
            case 9 -> {
                return new DiagonalEqualPattern(10, 1, CheckType.EQUALS, numberOfPlayersToStartGame, new int[][]{
                        {1, 0, 1},
                        {0, 1, 0},
                        {1, 0, 1},
                });
            }
            case 10 -> {
                return new DiagonalEqualPattern(11, 1, CheckType.EQUALS, numberOfPlayersToStartGame, new int[][]{
                        {1, 0, 0, 0, 0},
                        {0, 1, 0, 0, 0},
                        {0, 0, 1, 0, 0},
                        {0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 1},
                });
            }
            case 11 -> {
                return new StairPatternGoal(12, 1, CheckType.INDIFFERENT, numberOfPlayersToStartGame);
            }
            default -> {
                throw new Exception("This class does not exists");
            }
        }
    }

    public static GameState toEnum() {
        return GameState.IN_CREATION;
    }
}
