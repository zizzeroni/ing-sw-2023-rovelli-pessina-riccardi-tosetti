package view;

import model.*;
import model.commongoal.CommonGoal;
import model.tile.GoalTile;
import model.tile.Tile;
import model.view.GameView;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TextualUI extends UI {
    private GameView model;
    private boolean newTurnIntroStamped;
    private final Object newTurnMutex;

    public TextualUI(boolean newTurnIntroStamped, Object newTurnMutex, GameView model) {
        this.newTurnIntroStamped = newTurnIntroStamped;
        this.newTurnMutex = newTurnMutex;
        this.model = model;
    }

    public TextualUI() {
        this.newTurnMutex = new Object();
        this.newTurnIntroStamped = false;
        this.model = null;
    }

    public TextualUI(GameView model) {
        this.newTurnMutex = new Object();
        this.newTurnIntroStamped = false;
        this.model = model;
    }

    @Override
    public void run() {
        while(true) {
            showNewTurnIntro(model);
            Optional<Choice> c = askPlayer();
        }
    }

    private void showNewTurnIntro(GameView model) {
        System.out.println("---NEW TURN---");
        String pNickname = model.getPlayers().get(model.getActivePlayerIndex()).getNickname();
        Tile[][] boardMatrix = model.getBoard().getTiles();
        System.out.println("Tocca a te player: " + pNickname + "!");
        System.out.println("Stato della board attuale:");
        stampTilesMatrix(boardMatrix);
    }

    private Optional<Choice> askPlayer() {
        Scanner s = new Scanner(System.in);

        while(true) {
            System.out.println("Seleziona l'azione(Digita il numero associato all'azione):");
            System.out.println("1)Recap situazione personale");
            System.out.println("2)Scegli tessere");
            System.out.println("3)Invia messaggio tramite chat");
            String input = s.next();
            switch (input) {
                case "1" -> {
                    showPersonalRecap();
                }
                case "2" -> {
                    System.out.println("La situazione della board attuale:");
                    Tile[][] boardMatrix = model.getBoard().getTiles();
                    stampTilesMatrix(boardMatrix);

                    System.out.println("Inserisci le coordinate delle tessere che vuoi prendere (Digita STOP per fermarti)");
                    input = s.next();
                }
                case "3" -> {
                    System.out.println("Invio messaggio");
                }
            }
        }
    }
    private void showPersonalRecap() {
        Player activePlayer = model.getPlayers().get(model.getActivePlayerIndex());
        Bookshelf playerBookshelf = activePlayer.getBookshelf();
        PersonalGoal playerPersonalGoal = activePlayer.getPersonalGoal();
        List<GoalTile> playerGoalTiles = activePlayer.getGoalTiles();
        int playerScore = activePlayer.score();

        List<CommonGoal> commonGoals = model.getCommonGoals();

        System.out.println("Ecco il tuo recap:");

        showBookshelf(playerBookshelf);
        showPersonalObjective(playerPersonalGoal);
        showCommonGoals(playerGoalTiles,commonGoals);
        showScore(playerScore);
    }
    private void showBookshelf(Bookshelf bookshelf) {
        Tile[][] bookshelfMatrix = bookshelf.getTiles();
        System.out.println("Stato della tua bookshelf:");
        stampTilesMatrix(bookshelfMatrix);
    }
    private void showPersonalObjective(PersonalGoal personalGoal) {
        Tile[][] personalGoalMatrix = personalGoal.getPattern();
        System.out.println("Il tuo obiettivo personale:");
        stampTilesMatrix(personalGoalMatrix);
    }

    private void showCommonGoals(List<GoalTile> goalTiles, List<CommonGoal> commonGoals) {
        GoalTile goalTile1 = goalTiles.get(0);
        GoalTile goalTile2 = goalTiles.get(1);
        //Tile[][] commonGoal1Matrix = commonGoals.get(0).getPattern();       //Non c'è modo di recuperare il pattern dei commonGoal
        //Tile[][] commonGoal2Matrix = commonGoals.get(1).getPattern();       //Non c'è modo di recuperare il pattern dei commonGoal
        System.out.println("Obiettivi comuni completati: Obiettivo1:" + (goalTile1 != null ? goalTile1 : "/") + ", Obiettivo2: " + (goalTile2 != null ? goalTile2 : "/") + " (Valore delle goalTile)");
        //stampTilesMatrix(commonGoal1Matrix);
        //stampTilesMatrix(commonGoal2Matrix);
    }

    private void showScore(int score) {
        System.out.println("Il tuo punteggio attuale: " + score);
    }
    private void stampTilesMatrix(Tile[][] matrix) {
        for(int i=0;i<matrix.length;i++) {
            System.out.print("[ ");
            for(int j=0;j<matrix[0].length;j++) {
                Tile currentTile = matrix[i][j];
                System.out.print(currentTile!=null ? currentTile.getColor() + " " : "0 ");
            }
            System.out.println("]");
        }
    }

    @Override
    public void update(GameView o, Event arg) {

    }

    /*
    @Override
    public void run() {
        while (true) {
            synchronized (newTurnMutex) {
                while(!newTurnIntroStamped) {
                    try {
                        newTurnMutex.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            newTurnIntroStamped = false;
            Optional<Choice> c = askPlayer();

        }
    }

    private Optional<Choice> askPlayer() {
        Scanner s = new Scanner(System.in);

        while(true) {
            System.out.println("Seleziona l'azione(Digita il numero associato all'azione):");
            System.out.println("1)Recap situazione personale");
            System.out.println("2)Scegli tessere");
            System.out.println("3)Invia messaggio tramite chat");
            String input = s.next();
            switch (input) {
                case "1" -> {
                    setChanged();
                    notifyObservers(Event.RECAP_REQUEST);               //Modi migliori per farlo?
                    return Optional.empty();
                }
                case "2" -> {
                    
                }
                case "3" -> {
                    setChanged();
                    notifyObservers(Event.SEND_MESSAGE);                //Modi migliori per farlo?
                    return Optional.empty();
                }
            }
        }


    }

    @Override
    public void update(GameView o, Event arg) {
        switch (arg) {
            case NEW_TURN -> showNewTurnIntro(o);
            case RECAP_SEND -> showPersonalRecap(o);
        }
    }

    //----------------------------------NEW_TURN ENUM----------------------------------
    private void showNewTurnIntro(GameView model) {
        synchronized (newTurnMutex) {
            System.out.println("---NEW TURN---");
            String pNickname = model.getPlayers().get(model.getActivePlayerIndex()).getNickname();
            Tile[][] boardMatrix = model.getBoard().getTiles();
            System.out.println("Tocca a te player: " + pNickname + "!");
            System.out.println("Stato della board attuale:");
            stampTilesMatrix(boardMatrix);

            newTurnIntroStamped = true;
            newTurnMutex.notify();                          //? Non ho assolutamente idea se funzioni
        }

    }

    //----------------------------------RECAP_SEND ENUM----------------------------------
    private void showPersonalRecap(GameView model) {
        Player activePlayer = model.getPlayers().get(model.getActivePlayerIndex());
        Bookshelf playerBookshelf = activePlayer.getBookshelf();
        PersonalGoal playerPersonalGoal = activePlayer.getPersonalGoal();
        List<GoalTile> playerGoalTiles = activePlayer.getGoalTiles();
        int playerScore = activePlayer.score();

        List<CommonGoal> commonGoals = model.getCommonGoals();

        System.out.println("Ecco il tuo recap:");

        showBookshelf(playerBookshelf);
        showPersonalObjective(playerPersonalGoal);
        showCommonGoals(playerGoalTiles,commonGoals);
        showScore(playerScore);
    }
    private void showBookshelf(Bookshelf bookshelf) {
        Tile[][] bookshelfMatrix = bookshelf.getTiles();
        System.out.println("Stato della tua bookshelf:");
        stampTilesMatrix(bookshelfMatrix);
    }
    private void showPersonalObjective(PersonalGoal personalGoal) {
        Tile[][] personalGoalMatrix = personalGoal.getPattern();
        System.out.println("Il tuo obiettivo personale:");
        stampTilesMatrix(personalGoalMatrix);
    }

    private void showCommonGoals(List<GoalTile> goalTiles, List<CommonGoal> commonGoals) {
        GoalTile goalTile1 = goalTiles.get(0);
        GoalTile goalTile2 = goalTiles.get(1);
        //Tile[][] commonGoal1Matrix = commonGoals.get(0).getPattern();       //Non c'è modo di recuperare il pattern dei commonGoal
        //Tile[][] commonGoal2Matrix = commonGoals.get(1).getPattern();       //Non c'è modo di recuperare il pattern dei commonGoal
        System.out.println("Obiettivi comuni completati: Obiettivo1:" + (goalTile1 != null ? goalTile1 : "/") + ", Obiettivo2: " + (goalTile2 != null ? goalTile2 : "/") + " (Valore delle goalTile)");
        //stampTilesMatrix(commonGoal1Matrix);
        //stampTilesMatrix(commonGoal2Matrix);
    }

    private void showScore(int score) {
        System.out.println("Il tuo punteggio attuale: " + score);
    }

    private void stampTilesMatrix(Tile[][] matrix) {
        for(int i=0;i<matrix.length;i++) {
            System.out.print("[ ");
            for(int j=0;j<matrix[0].length;j++) {
                Tile currentTile = matrix[i][j];
                System.out.print(currentTile!=null ? currentTile.getColor() + " " : "0 ");
            }
            System.out.println("]");
        }
    }
    */
}
