package model;

import model.commongoal.CommonGoal;
import model.commongoal.EightShaplessPatternGoal;
import model.commongoal.FiveXShapePatternGoal;
import model.commongoal.MinEqualsTilesPattern;
import model.commongoal.StairPatternGoal;
import model.commongoal.GoalPattern_1_3_4;
import model.commongoal.FiveDiagonalPatternGoal;
import model.commongoal.FourCornersPatternGoal;

import model.tile.Tile;
import model.tile.TileColor;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

import java.util.stream.Collectors;

public class Game {

    private int numPlayers;
    private int activePlayerIndex;
    private ArrayList<Player> players;
    private ArrayList<Tile> bag;
    private Board board;
    private ArrayList<CommonGoal> commonGoals;
    private final Random randomizer = new Random();

    public Game(int numPlayers, ArrayList<Player> players, ArrayList<PersonalGoal> personalGoals) {
        this.players = players;
        this.board = new Board();
        this.activePlayerIndex = 0;
        this.numPlayers = numPlayers;
        this.bag = new ArrayList<Tile>(132);
        this.commonGoals = new ArrayList<CommonGoal>(2);

        //initialize bag and shuffle items
        for (int i = 0; i < 132; i++){
            this.bag.add(new Tile(TileColor.values()[i % 6]));
        }

        Collections.shuffle(personalGoals);

        //initialize players
        for (Player player: players) {
            player.setBookshelf(new Bookshelf());
//            player.setGoalTile(new ArrayList<>(3));
//            player.setPersonalGoal(personalGoals.get(0));
//            personalGoals.remove(0);
        }

        //initialize common goals
//        CommonGoal newCommonGoal;
//        while(this.commonGoals.size() == 2) {
//            try{
//                newCommonGoal = this.getRandomCommonGoalSubclassInstance();
//                if(!this.commonGoals.contains(newCommonGoal)){
//                    this.commonGoals.add(newCommonGoal);
//                }
//            } catch(Exception e){
//                System.out.println(e.getMessage());
//            }
//        }

        CommonGoal testCommonGoal = new GoalPattern_1_3_4();
        Tile[][] tiles = {
            {null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
            {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
            {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
            {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
            {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
            {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}
        };

        this.players.get(0).setBookshelf(new Bookshelf("", tiles));
        testCommonGoal.goalPattern(this.players.get(0).getBookshelf());



        this.refillBoard();
    }

    public Game(int numPlayers, int activePlayerIndex, ArrayList<Player> players, ArrayList<Tile> bag, Board board, ArrayList<CommonGoal> commonGoals) {
        this.numPlayers = numPlayers;
        this.activePlayerIndex = activePlayerIndex;
        this.players = players;
        this.bag = bag;
        this.board = board;
        this.commonGoals = commonGoals;
    }


    public int getNumPlayers() {
        return numPlayers;
    }
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public int getActivePlayerIndex() {
        return activePlayerIndex;
    }
    public void setActivePlayerIndex(int activePlayerIndex) {
        this.activePlayerIndex = activePlayerIndex;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Tile> getBag() {
        return bag;
    }
    public void setBag(ArrayList<Tile> bag) {
        this.bag = bag;
    }

    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<CommonGoal> getCommonGoals() {
        return commonGoals;
    }
    public void setCommonGoals(ArrayList<CommonGoal> commonGoals) {
        this.commonGoals = commonGoals;
    }

    public void changeTurn() {
        if(this.activePlayerIndex == this.players.size() - 1) {
            this.activePlayerIndex = 0;
        } else {
            this.activePlayerIndex++;
        }

        //if board needs to be refilled, remove tiles from the bag and add them to the board
        if(this.board.needRefill() != 0) {
            this.refillBoard();
        }
    }
    private void refillBoard(){
        Collections.shuffle(bag);
        this.board.addTiles((ArrayList<Tile>) this.bag.subList(0, this.board.needRefill()));
    }

    private boolean isPaused(){
        return this.connectedPlayers().size() == 1;
    }

    private ArrayList<Player> connectedPlayers() {
        return this.players.stream()
                .filter(Player::isConnected)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private CommonGoal getRandomCommonGoalSubclassInstance() throws Exception {
        switch (this.randomizer.nextInt(12)) {
            case 0 -> {
                return new EightShaplessPatternGoal();
            }
            case 1 -> {
                return new MinEqualsTilesPattern();
            }
            case 2 -> {
                return new MinEqualsTilesPattern();
            }
            case 3 -> {
                return new EightShaplessPatternGoal();
                // return new FiveXShapePatternGoal();
            }
            case 4 -> {
                return new MinEqualsTilesPattern();
            }
            case 5 -> {
                return new EightShaplessPatternGoal();
                // return new StairPatternGoal();
            }
            case 6 -> {
                return new MinEqualsTilesPattern();
            }
            case 7 -> {
                return new EightShaplessPatternGoal();
                // return new FiveDiagonalPatternGoal();
            }
            case 8 -> {
                return new GoalPattern_1_3_4();
            }
            case 9 -> {
                return new GoalPattern_1_3_4();
            }
            case 10 -> {
                return new GoalPattern_1_3_4();
            }
            case 11 -> {
                return new FourCornersPatternGoal();
            }
            default -> {
                throw new Exception("This class does not exists");
            }
        }

    }

}
