package view;

import model.*;
import model.commongoal.Direction;
import model.view.*;
import utils.ObservableType;

import java.util.*;
import java.util.stream.Stream;

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
        while (true) {
            showNewTurnIntro();
            Optional<Choice> c = askPlayer();
        }
    }

    private void showNewTurnIntro() {
        System.out.println("---NEW TURN---");
        String pNickname = model.getPlayers().get(model.getActivePlayerIndex()).getNickname();
        TileView[][] boardMatrix = model.getBoard().getTiles();
        System.out.println("Tocca a te player: " + pNickname + "!");
        System.out.println("Stato della board attuale:");
        printTilesMatrix(boardMatrix);
    }

    private Optional<Choice> askPlayer() {
        Scanner s = new Scanner(System.in);

        while (true) {
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
                    TileView[][] boardMatrix = model.getBoard().getTiles();
                    printTilesMatrix(boardMatrix);

                    int counter = 0, firstRow = 0, firstColumn = 0;
                    boolean isInsertCorrect;
                    Choice playerChoice = new Choice();
                    Direction directionToCheck = null;

                    //---------------------------------SCELTA COORDINATE TESSERE---------------------------------
                    do {
                        isInsertCorrect = false;
                        int row = 0, column = 0;
                        while (!isInsertCorrect) {
                            System.out.println("Inserisci la riga della " + (counter + 1) + "° tessera che vuoi prendere:");
                            try {
                                row = s.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("Hai inserito un valore non valido, riprova!");
                                s.next();
                            }

                            if (row <= model.getBoard().getNumRows() && row > 0) {
                                isInsertCorrect = true;
                            } else {
                                System.out.println("Inserisci una riga valida (Un numero compreso tra 1 e " + model.getBoard().getNumRows() + "!)");
                            }
                        }

                        isInsertCorrect = false;

                        while (!isInsertCorrect) {
                            System.out.println("Inserisci la colonna della " + (counter + 1) + "° tessera che vuoi prendere:");
                            try {
                                column = s.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("Hai inserito un valore non valido, riprova!");
                                s.next();
                            }

                            if (column <= model.getBoard().getNumColumns() && column > 0) {
                                isInsertCorrect = true;
                            } else {
                                System.out.println("Inserisci una colonna valida (Un numero compreso tra 1 e " + model.getBoard().getNumColumns() + "!");
                            }
                        }

                        if (checkIfPickable(row - 1, column - 1)) {
                            switch (counter) {
                                case 0 -> {
                                    counter++;
                                    firstRow = row;
                                    firstColumn = column;
                                    playerChoice.addTile(this.model.getBoard().getTiles()[row - 1][column - 1]);
                                    playerChoice.addCoords(new Choice.Coord(row - 1, column - 1));
                                }
                                case 1 -> {
                                    Direction res = checkIfInLine(row, column, firstRow, firstColumn);
                                    if (res != null) {
                                        directionToCheck = res;
                                        counter++;
                                        playerChoice.addTile(this.model.getBoard().getTiles()[row - 1][column - 1]);
                                        playerChoice.addCoords(new Choice.Coord(row - 1, column - 1));
                                    }
                                }
                                case 2 -> {
                                    if (checkIfInLine(row-1, column-1, playerChoice.getTileCoords(), directionToCheck)) {
                                        counter++;
                                        playerChoice.addTile(this.model.getBoard().getTiles()[row - 1][column - 1]);
                                        playerChoice.addCoords(new Choice.Coord(row - 1, column - 1));
                                    }
                                }
                                default -> {
                                    System.err.println("ERROR: Unexpected number of chosen tiles, found: " + counter + ", expected value < 3");
                                }
                            }
                        }
                        if (counter > 0 && counter!=3) {
                            System.out.println("Vuoi continuare? (Digita \"SI\" per continuare, \"NO\" per fermarti)");
                            input = s.next();
                        }
                    } while (!input.equals("NO") && counter < 3);


                    //---------------------------------SCELTA COLONNA---------------------------------
                    showBookshelf(model.getPlayers().get(this.model.getActivePlayerIndex()).getBookshelf());

                    int chosenColumn=0;
                    do {
                        System.out.println("Scegli la colonna in cui vuoi inserire le tue tessere:");
                        try {
                            chosenColumn = s.nextInt();

                            if (chosenColumn <= 0 || chosenColumn > this.model.getPlayers().get(0).getBookshelf().getNumColumns()) {
                                System.out.println("Hai scelto una colonna al di fuori dei limiti della bookshelf, inserisci un valore compreso tra" +
                                        " 1 e " + this.model.getPlayers().get(0).getBookshelf().getNumColumns() + "!");
                            }
                        } catch (InputMismatchException ignored) {
                            System.out.println("Non hai inserito un valore valido, riprova!");
                        }


                    } while (chosenColumn <= 0 || chosenColumn > this.model.getPlayers().get(0).getBookshelf().getNumColumns());

                    playerChoice.setChosenColumn(chosenColumn - 1);


                    //---------------------------------SCELTA ORDINE---------------------------------
                    System.out.println("Digita l'ordine con cui vuoi inserire le tessere (1 indica la prima tessera scelta, 2 la seconda e 3 la terza, ES: 1,3,2)");
                    isInsertCorrect = false;
                    do {
                        input = s.next();
                        String[] temp;
                        temp = input.split(",");
                        boolean res = false;
                        if (temp.length == counter) {
                            switch (counter) {
                                case 1 -> {
                                    res = Arrays.asList(temp).contains("1");
                                }
                                case 2 -> {
                                    res = Arrays.asList(temp).containsAll(Arrays.asList("1", "2"));
                                }
                                case 3 -> {
                                    res = Arrays.asList(temp).containsAll(Arrays.asList("1", "2", "3"));
                                }
                                default -> {
                                    System.err.println("Unexpected value of chosen tiles");
                                }
                            }

                            if (res) {
                                int[] chosenPositions = new int[temp.length];
                                for (int i = 0; i < temp.length; i++) {
                                    chosenPositions[i] = Integer.parseInt(temp[i]) - 1;
                                }
                                playerChoice.setTileOrder(chosenPositions);
                                isInsertCorrect = true;
                            } else {
                                System.out.println("Hai inserito delle cifre non coerenti con il numero di tessere scelte");
                            }
                        } else {
                            System.out.println("Hai inserito un numero di cifre diverso dal numero di tessere scelte. Oppure hai effettuato un inserimento che non rispetta" +
                                    " la formattazione richiesta, riprova!");
                        }
                    } while (!isInsertCorrect);

                    //---------------------------------NOTIFICA CONTROLLER---------------------------------
                    setChanged();
                    notifyObservers(playerChoice);

                    /*
                    int c = 0;
                    for(int i=0;i<model.getPlayers().get(0).getBookshelf().getNumRows();i++) {
                        System.out.print("[ ");
                        for(int j=0;j<model.getPlayers().get(0).getBookshelf().getNumColumns();j++) {
                            if(playerChoice.getChosenColumn()-1==j && i==(model.getPlayers().get(0).getBookshelf().getNumRows() - 1) - model.getPlayers().get(0).getBookshelf().getNumElemColumn(j) - (playerChoice.getChosenTiles().size()-1-c)) {
                                System.out.print(playerChoice.getChosenTiles().get(playerChoice.getTileOrder()[c]).getColor()+" ");
                                c++;
                            } else {
                                System.out.print("0 ");
                            }

                        }
                        System.out.println("]");
                    }
                    */

                }
                case "3" -> {
                    System.out.println("Invio messaggio");
                }
                default -> {
                    System.err.println("Non hai inserito un valore valido, riprova! (Inserisci uno degli indici del menù)");
                }
            }
        }
    }

    private Direction checkIfInLine(int row, int column, int firstRow, int firstColumn) {
        if (row == firstRow && column == firstColumn) {
            System.out.println("Non puoi scegliere di nuovo una tessera già scelta, riprova!");
            return null;
        }
        if ((row == firstRow) && (column - 1 == firstColumn || column + 1 == firstColumn)) {
            return Direction.HORIZONTAL;
        }
        if ((column == firstColumn) && (row - 1 == firstRow || row + 1 == firstRow)) {
            return Direction.VERTICAL;
        }
        System.out.println("Le tessere selezionate devono formare una linea retta ed essere adiacenti, riprova!");
        return null;
    }

    private boolean checkIfInLine(int row, int column, List<Choice.Coord> prevTilesCoords, Direction directionToCheck) {
        if (prevTilesCoords.contains(new Choice.Coord(row, column))) {
            System.out.println("Non puoi scegliere di nuovo una tessera già scelta, riprova!");
            return false;
        }
        switch (directionToCheck) {
            case HORIZONTAL -> {
                if (row != prevTilesCoords.get(0).getX()) {
                    System.out.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
                    return false;
                } else {
                    for (Choice.Coord coords : prevTilesCoords) {
                        if (coords.getY() == column + 1 || coords.getY() == column - 1) {
                            return true;
                        }
                    }
                    System.out.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
                }
                return false;
            }
            case VERTICAL -> {
                if (column != prevTilesCoords.get(0).getY()) {
                    System.out.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
                    return false;
                } else {
                    for (Choice.Coord coords : prevTilesCoords) {
                        if (coords.getX() == row + 1 || coords.getX() == row - 1) {
                            return true;
                        }
                    }
                    System.out.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
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
        BoardView board = model.getBoard();
        TileView[][] boardMatrix = board.getTiles();

        if (!boardMatrix[row][column].isNull()) {
            if ((row != 0 && boardMatrix[row - 1][column].isNull()) ||
                    (row != board.getNumRows() && boardMatrix[row + 1][column].isNull()) ||
                        (column != board.getNumColumns() && boardMatrix[row][column + 1].isNull()) ||
                            (column != 0 && boardMatrix[row][column - 1].isNull())) {
                return true;
            } else {
                System.out.println("Impossibile prendere la tessera (Ha tutti i lati occupati), riprova!");
            }
        } else {
            System.out.println("Non è presente nessuna tessera nella cella selezionata, riprova!");
        }

        return false;
    }


    private void showPersonalRecap() {
        PlayerView activePlayer = model.getPlayers().get(model.getActivePlayerIndex());
        BookshelfView playerBookshelf = activePlayer.getBookshelf();
        PersonalGoalView playerPersonalGoal = activePlayer.getPersonalGoal();
        List<GoalTileView> playerGoalTiles = activePlayer.getGoalTiles();

        setChanged();
        notifyObservers(Event.COMPUTE_SCORE);
        int playerScore = activePlayer.score();

        List<CommonGoalView> commonGoals = model.getCommonGoals();

        System.out.println("Ecco il tuo recap:");

        showBookshelf(playerBookshelf);
        showPersonalObjective(playerPersonalGoal);
        showCommonGoals(playerGoalTiles, commonGoals);
        showScore(playerScore);
    }

    private void showBookshelf(BookshelfView bookshelf) {
        TileView[][] bookshelfMatrix = bookshelf.getTiles();
        System.out.println("Stato della tua bookshelf:");
        printTilesMatrix(bookshelfMatrix);
    }

    private void showPersonalObjective(PersonalGoalView personalGoal) {
        TileView[][] personalGoalMatrix = personalGoal.getPattern();
        System.out.println("Il tuo obiettivo personale:");
        printTilesMatrix(personalGoalMatrix);
    }

    private void showCommonGoals(List<GoalTileView> goalTiles, List<CommonGoalView> commonGoals) {
        GoalTileView goalTile1;
        GoalTileView goalTile2;
        GoalTileView goalTile3;
        switch (goalTiles.size()) {
            case 0 -> {
                goalTile1 = null;
                goalTile2 = null;
                goalTile3 = null;
            }
            case 1 -> {
                goalTile1 = goalTiles.get(0);
                goalTile2 = null;
                goalTile3 = null;
            }
            case 2 -> {
                goalTile1 = goalTiles.get(0);
                goalTile2 = goalTiles.get(1);
                goalTile3 = null;
            }
            case 3 -> {
                goalTile1 = goalTiles.get(0);
                goalTile2 = goalTiles.get(1);
                goalTile3 = goalTiles.get(2);
            }
            default -> {
                goalTile1 = null;
                goalTile2 = null;
                goalTile3 = null;
                System.err.println("Error! Player ha less than 0 goal tiles, or more than 3");
            }
        }


        //Tile[][] commonGoal1Matrix = commonGoals.get(0).getPattern();       //Non c'è modo di recuperare il pattern dei commonGoal
        //Tile[][] commonGoal2Matrix = commonGoals.get(1).getPattern();       //Non c'è modo di recuperare il pattern dei commonGoal
        System.out.println("Obiettivi comuni completati: Obiettivo1:" + (goalTile1 != null ? goalTile1 : "/") + ", Obiettivo2:" + (goalTile2 != null ? goalTile2 : "/") + ", Vittoria:" + (goalTile2 != null ? goalTile3 : "/") + " (Valore delle goalTile)");
        //stampTilesMatrix(commonGoal1Matrix);
        //stampTilesMatrix(commonGoal2Matrix);
    }

    private void showScore(int score) {
        System.out.println("Il tuo punteggio attuale: " + score);
    }

    //TODO: Si è rivelato necessario aggiungere in TileView un metodo isNull(), per verificare se l'attributo tileModel risultava nullo. Questo può accadere poichè la TileView viene
    //      creata a partire dalla Tile che può essere NULL cosa che non comporta che anche TileView lo sia (Da qui la necessità di questo metodo)
    private void printTilesMatrix(TileView[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < matrix[0].length; j++) {
                TileView currentTile = matrix[i][j];
                System.out.print(currentTile.isNull() ? "0 " : currentTile.getColor() + " ");
            }
            System.out.println("]");
        }
    }

    @Override
    public void update(GameView o, ObservableType arg) {
        switch (arg.getEvent()) {
            case REMOVE_TILES_BOARD,ADD_TILES_BOOKSHELF -> {
                updateModel(o);
            }
            default -> {
                System.err.println("Ignoring event from " + o + ": " + arg);
            }
        }
    }

    private void updateModel(GameView model) {
        this.model = model;
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
