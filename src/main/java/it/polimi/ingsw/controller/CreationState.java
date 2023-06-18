package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commongoal.*;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class is referred to the first state assumed by the {@code Game}.
 * It contains methods used during its creation and setup to provide
 * different useful information such as the number of the active players,
 * their present state (connected or not) and other methods linked to game,
 * turn and players management.
 *
 */
public class CreationState extends ControllerState {

    public CreationState(GameController controller) {
        super(controller);
    }

    /**
     * Method employed for turn management.
     * If the {@code Game} is in creation phase, does nothing.
     *
     * @see Game
     */
    @Override
    public void changeTurn() {
        //Game is in creation phase, so do nothing...
    }

    /**
     * Method employed to read the {@code User} input.
     * When the {@code Game} is in creation phase, does nothing.
     *
     * @param playerChoice the {@code Choice} made by the {@code Player}
     *                     (as a selection of multiple tiles).
     *
     * @see Game
     * @see Choice
     */
    @Override
    public void insertUserInputIntoModel(Choice playerChoice) {
        //Game is in creation phase, so do nothing...
    }

    /**
     * This method is used to stream a message privately.
     * Only the specified receiver will be able to read the message
     * in any chat implementation. It builds a new object message at each call, setting
     * the {@code nickname}s of the receiving {@code Player}s and its message type to {@code PRIVATE}.
     *
     * @param receiver the {@code Player} receiving the message.
     * @param sender the {@code Player} sending the message.
     * @param content the text of the message being sent.
     *
     * @see Player
     * @see Player#getNickname()
     * @see Message#messageType()
     */
    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) {
        Message message = new Message(MessageType.PRIVATE, receiver, sender, content);
        for (Player player : this.controller.getModel().getPlayers()) {
            if (player.getNickname().equals(receiver)) {
                player.addMessage(message);
            }
        }

    }

    /**
     * This method is used to stream a message in broadcast mode.
     * All the players will be able to read the message
     * in any chat implementation. It builds a new object message at each call, setting
     * the {@code nickname} of the sending {@code Player} and its message type to {@code BROADCAST}.
     *
     * @param sender the {@code Player} sending the message.
     * @param content the text of the message being sent.
     *
     * @see Player
     * @see Player#getNickname()
     * @see Message#messageType()
     */
    @Override
    public void sendBroadcastMessage(String sender, String content) {
        for (Player player : this.controller.getModel().getPlayers()) {
            Message message = new Message(MessageType.BROADCAST, player.getNickname(), sender, content);
            player.addMessage(message);
        }

    }

    /**
     * This method is used to add a {@code Player} to the current {@code Game}
     * through the nickname he has chosen during game creation and to assign a player a
     * randomly chosen {@code PersonalGoal}. In order to provide the goal it as to
     * access the GameController to get the number of persona goals for each player.
     * <p>
     * All the added players are characterized by {@code Bookshelf},
     * {@code PersonalGoal} and an array of {@code ScoreTile} elements.
     * <p>
     * The method also sets the connection state of any given {@code Player} to {@code true}.
     *
     * @param nickname is the reference for the name of the {@code Player} being added.
     *
     * @see PersonalGoal
     * @see GameController#getNumberOfPersonalGoals()
     */
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
        newPlayer = new Player(nickname, true, randomPersonalGoal, new ArrayList<ScoreTile>(), new Bookshelf());
        //}
        this.controller.getModel().addPlayer(newPlayer);
    }

    /**
     * The method starts verifying  if the {@code Game} creation has occurred properly,
     * confronting the number of active players registered during the previous phase with
     * that stored in the {@code Model}.
     * Then, it proceeds to adjust the {@code Board} and to draw a list of Tiles.
     * Finally, it initializes the {@code ScoreTile} list for each active {@code Player},
     * (necessary in order to replace them later if a player complete a {@code CommonGoal}).
     *
     * @see Game
     * @see Player
     * @see ScoreTile
     * @see CommonGoal
     * @see Game#getNumberOfPlayersToStartGame()
     * @see Game#getPlayers()
     * @see Board#setPattern(JsonBoardPattern)
     * @see Board#numberOfTilesToRefill()
     */
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

    /** Disconnects the selected {@code Player} from the {@code Game}.
     * (only possible when the {@code Game} has already started).
     *
     * @param nickname is the nickname identifying the player selected for disconnection.
     *
     * @see Game
     * @see Player
     * @see Game#getPlayerFromNickname
     * @see Player#getPersonalGoal()
     */
    @Override
    public void disconnectPlayer(String nickname) {
        this.controller.addPersonalGoal(this.controller.getModel().getPlayerFromNickname(nickname).getPersonalGoal());
        this.controller.getModel().getPlayers().remove(this.controller.getModel().getPlayerFromNickname(nickname));
    }

    /**
     * Method to implement the selection of the number of players for the {@code Game}.
     *
     * @param chosenNumberOfPlayers identifies the number of players present
     *                              in the lobby during the game creation.
     *
     * @see Game
     * @see Game#getPlayers()
     *
     */
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

    /**
     * Method implementing the random generation of a series
     * of different-type {@code CommonGoal} objects.
     *
     * @return the {@code CommonGoal} object being randomly generated
     * @throws Exception signals if generation cannot be provided due to an error
     * linked to class instantiation
     *
     * @see CommonGoal
     * @see Game#getNumberOfPlayersToStartGame()
     */
    private CommonGoal getRandomCommonGoalSubclassInstance() throws Exception {
        int numberOfPlayersToStartGame = this.controller.getModel().getNumberOfPlayersToStartGame();
        int commonGoalSize = this.controller.getModel().getCommonGoals().size();
        switch (this.controller.getRandomizer().nextInt(12)) {
            case 0 -> {
                return new EightShapelessPatternGoal(0, 1, CheckType.INDIFFERENT, numberOfPlayersToStartGame, commonGoalSize);
            }
            case 1 -> {
                return new MinEqualsTilesPattern(0, 2, CheckType.DIFFERENT, numberOfPlayersToStartGame, commonGoalSize, Direction.HORIZONTAL, 0);
            }
            case 2 -> {
                return new MinEqualsTilesPattern(0, 3, CheckType.INDIFFERENT, numberOfPlayersToStartGame, commonGoalSize, Direction.VERTICAL, 3);
            }
            case 3 -> {
                return new DiagonalEqualPattern(1, 1, CheckType.EQUALS, numberOfPlayersToStartGame, commonGoalSize, new int[][]{
                        {1, 0, 1},
                        {0, 1, 0},
                        {1, 0, 1},
                });
            }
            case 4 -> {
                return new MinEqualsTilesPattern(0, 4, CheckType.INDIFFERENT, numberOfPlayersToStartGame, commonGoalSize, Direction.HORIZONTAL, 2);
            }
            case 5 -> {
                return new StairPatternGoal(1, 1, CheckType.INDIFFERENT, numberOfPlayersToStartGame, commonGoalSize);
            }
            case 6 -> {
                return new MinEqualsTilesPattern(0, 2, CheckType.DIFFERENT, numberOfPlayersToStartGame, commonGoalSize, Direction.VERTICAL, 0);
            }
            case 7 -> {
                return new DiagonalEqualPattern(1, 1, CheckType.EQUALS, numberOfPlayersToStartGame, commonGoalSize, new int[][]{
                        {1, 0, 0, 0, 0},
                        {0, 1, 0, 0, 0},
                        {0, 0, 1, 0, 0},
                        {0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 1},
                });
            }
            case 8 -> {
                return new ConsecutiveTilesPatternGoal(1, 6, CheckType.EQUALS, numberOfPlayersToStartGame, commonGoalSize, 2);
            }
            case 9 -> {
                return new TilesInPositionsPatternGoal(1, 1, CheckType.EQUALS, numberOfPlayersToStartGame, commonGoalSize, new int[][]{
                        {1, 1},
                        {1, 1},
                });
            }
            case 10 -> {
                return new ConsecutiveTilesPatternGoal(1, 4, CheckType.EQUALS, numberOfPlayersToStartGame, commonGoalSize, 4);
            }
            case 11 -> {
                return new FourCornersPatternGoal(0, 1, CheckType.EQUALS, numberOfPlayersToStartGame, commonGoalSize);
            }
            default -> {
                throw new Exception("This class does not exists");
            }
        }
    }

    /**
     * Returns the current {@code State} of the {@code Game}.
     *
     * @return the {@code IN_CREATION} state of the {@code Game}.
     *
     * @see GameState#IN_CREATION
     */
    public static GameState toEnum() {
        return GameState.IN_CREATION;
    }
}
