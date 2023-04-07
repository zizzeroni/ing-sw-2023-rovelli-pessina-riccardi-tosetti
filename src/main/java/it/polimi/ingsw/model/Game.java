package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commongoal.*;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.ObservableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

import java.util.stream.Collectors;

public class Game extends Observable<ObservableType> {

    private int numberOfPlayers;
    private int activePlayerIndex;
    private List<Player> players;
    private List<Tile> bag;
    private Board board;
    private List<CommonGoal> commonGoals;
    private final Random randomizer = new Random();

    public Game(int numberOfPlayers, List<Player> players, List<PersonalGoal> personalGoals, JsonBoardPattern boardPattern) {
        this.players = players;
        this.activePlayerIndex = 0;
        this.board = new Board(boardPattern);
        this.numberOfPlayers = numberOfPlayers;
        this.bag = new ArrayList<>(132);
        this.commonGoals = new ArrayList<>(2);

        //initialize bag and shuffle items
        for (int i = 0; i < 132; i++){
            this.bag.add(new Tile(TileColor.values()[i % 6]));
        }

        Collections.shuffle(personalGoals);

        //initialize players
        for (Player player: this.players) {
            player.setBookshelf(new Bookshelf());
            player.setGoalTiles(new ArrayList<>(3));
            player.setPersonalGoal(personalGoals.remove(0));
        }

        //initialize common goals
        CommonGoal newCommonGoal;
        while(this.commonGoals.size() == 2) {
            try{
                newCommonGoal = this.getRandomCommonGoalSubclassInstance();
                if(!this.commonGoals.contains(newCommonGoal)){
                    this.commonGoals.add(newCommonGoal);
                }
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        this.refillBoard();
    }
    public Game(int numberOfPlayers, int activePlayerIndex, List<Player> players, List<Tile> bag, Board board, List<CommonGoal> commonGoals) {
        this.numberOfPlayers = numberOfPlayers;
        this.activePlayerIndex = activePlayerIndex;
        this.players = players;
        this.bag = bag;
        this.board = board;
        this.commonGoals = commonGoals;
    }


    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getActivePlayerIndex() {
        return activePlayerIndex;
    }
    public void setActivePlayerIndex(int activePlayerIndex) {
        this.activePlayerIndex = activePlayerIndex;
    }

    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Tile> getBag() {
        return bag;
    }
    public void setBag(List<Tile> bag) {
        this.bag = bag;
    }

    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }

    public List<CommonGoal> getCommonGoals() {
        return commonGoals;
    }
    public void setCommonGoals(List<CommonGoal> commonGoals) {
        this.commonGoals = commonGoals;
    }

    public void changeTurn() {

        //if board needs to be refilled, remove tiles from the bag and add them to the board
        if(this.board.numberOfTilesToRefill() != 0) {
            this.refillBoard();
        }

        this.updatePlayerScore(this.players.get(this.activePlayerIndex));

        if(this.activePlayerIndex == this.players.size() - 1) {
            this.activePlayerIndex = 0;
        } else {
            this.activePlayerIndex++;
        }

    }
    private void refillBoard(){
        Collections.shuffle(this.bag);

        List<Tile> drawnTiles = this.bag.subList(0, this.board.numberOfTilesToRefill());
        this.board.addTiles(drawnTiles);
        drawnTiles.clear();
    }

    private void updatePlayerScore(Player player){
        player.score();
    }

    private boolean isPaused(){
        return this.connectedPlayers().size() == 1;
    }

    private List<Player> connectedPlayers() {
        return this.players.stream()
                .filter(Player::isConnected)
                .collect(Collectors.toList());
    }

    private CommonGoal getRandomCommonGoalSubclassInstance() throws Exception {
        switch (this.randomizer.nextInt(12)) {
            case 0 -> {
                return new EightShaplessPatternGoal();
            }
            case 1 -> {
                return new MinEqualsTilesPattern(0,2,CheckType.DIFFERENT, Direction.HORIZONTAL,0);
            }
            case 2 -> {
                return new MinEqualsTilesPattern(0,3,CheckType.INDIFFERENT,Direction.VERTICAL,3);
            }
            case 3 -> {
                return new DiagonalEqualPattern(1,1, CheckType.EQUALS, new int[][]{
                    {1, 0, 1},
                    {0, 1, 0},
                    {1, 0, 1},
                });
            }
            case 4 -> {
                return new MinEqualsTilesPattern(0, 4, CheckType.INDIFFERENT, Direction.HORIZONTAL, 2);
            }
            case 5 -> {
                return new StairPatternGoal(1, 1, CheckType.INDIFFERENT);
            }
            case 6 -> {
                return new MinEqualsTilesPattern(0,2,CheckType.DIFFERENT,Direction.VERTICAL,0);
            }
            case 7 -> {
                return new DiagonalEqualPattern(1,1, CheckType.EQUALS, new int[][]{
                    {1, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 1, 0},
                    {0, 0, 0, 0, 1},
                });
            }
            case 8 -> {
                return new ConsecutiveTilesPatternGoal(1, 6, CheckType.EQUALS, 2);
            }
            case 9 -> {
                return new TilesInPositionsPatternGoal(1,1, CheckType.EQUALS, new int[][]{
                    {1, 1},
                    {1, 1},
                });
            }
            case 10 -> {
                return new ConsecutiveTilesPatternGoal(1, 4, CheckType.EQUALS, 4);
            }
            case 11 -> {
                return new FourCornersPatternGoal();
            }
            default -> {
                throw new Exception("This class does not exists");
            }
        }

    }

    private void sendMessage(Player receiver, Player sender, String content) {

        String senderNickname = sender.getNickname();
        String receiverNickname = receiver.getNickname();

        if(receiver == null) {
            for (Player player: this.players) {
                //player.addMessage(new Message(player.getNickname(), senderNickname, content));
            }
        } else {
            //sender.addMessage(new Message(receiverNickname, senderNickname, content));
            //receiver.addMessage(new Message(receiverNickname, senderNickname, content));
        }
    }

    private Player getPlayerFromNickname(String nickname){
        return players.stream()
                        .filter(player -> player.getNickname().equals(nickname))
                        .findFirst()
                        .orElse(null);
    }

    public void boardModified() {
        setChangedAndNotifyObservers(Event.REMOVE_TILES_BOARD);
    }
    public void bookshelfModified() {
        setChangedAndNotifyObservers(Event.ADD_TILES_BOOKSHELF);
    }
    private void setChangedAndNotifyObservers(ObservableType arg) {
        setChanged();
        notifyObservers(arg);
    }
}
