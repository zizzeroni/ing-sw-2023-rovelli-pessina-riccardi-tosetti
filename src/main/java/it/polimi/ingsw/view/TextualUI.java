package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.view.*;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.commongoal.Direction;

import java.rmi.RemoteException;
import java.util.*;

public class TextualUI extends UI {


    public TextualUI(GameView model) {
        super(model);
    }

    public TextualUI() {
        super();
    }


    @Override
    public void run() {
        while (true) {
            showNewTurnIntro();
            Choice choice = askPlayer();

            //---------------------------------NOTIFICA CONTROLLER---------------------------------
            this.controller.insertUserInputIntoModel(choice);
            this.controller.changeTurn();
        }
    }

    @Override
    public void showNewTurnIntro() {
        System.out.println("---NEW TURN---");
        String pNickname = this.getModel().getPlayers().get(this.getModel().getActivePlayerIndex()).getNickname();
        System.out.println("Tocca a te player: " + pNickname + "!");
        System.out.println("Stato della board attuale:");
        System.out.println(this.getModel().getBoard());
    }
    
    @Override
    public Choice askPlayer() {
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
                    System.out.println(this.getModel().getBoard());

                    int counter = 0, firstRow = 0, firstColumn = 0;
                    boolean isInsertCorrect;
                    Choice playerChoice = new Choice();
                    Direction directionToCheck = null;
                    int maxNumberOfCellsFreeInBookshelf = 0;
                    //---------------------------------SCELTA COORDINATE TESSERE---------------------------------
                    for (int i = 0; i < this.getModel().getPlayers().get(this.getModel().getActivePlayerIndex()).getBookshelf().getNumberOfColumns(); i++) {
                        int numberOfFreeSpaces = this.getModel().getPlayers().get(this.getModel().getActivePlayerIndex()).getBookshelf().getNumberOfEmptyCellsInColumn(i);
                        if (numberOfFreeSpaces > maxNumberOfCellsFreeInBookshelf) {
                            maxNumberOfCellsFreeInBookshelf = numberOfFreeSpaces;
                        }
                    }
                    do {
                        isInsertCorrect = false;
                        int row = 0, column = 0;
                        while (!isInsertCorrect) {
                            System.out.println("Inserisci la riga della " + (counter + 1) + "° tessera che vuoi prendere:");
                            try {
                                row = s.nextInt();
                            } catch (InputMismatchException e) {
                                System.err.println("Hai inserito un valore non valido, riprova!");
                                s.next();
                            }

                            if (row <= this.getModel().getBoard().getNumberOfRows() && row > 0) {
                                isInsertCorrect = true;
                            } else {
                                System.err.println("Inserisci una riga valida (Un numero compreso tra 1 e " + this.getModel().getBoard().getNumberOfRows() + "!)");
                            }
                        }

                        isInsertCorrect = false;

                        while (!isInsertCorrect) {
                            System.out.println("Inserisci la colonna della " + (counter + 1) + "° tessera che vuoi prendere:");
                            try {
                                column = s.nextInt();
                            } catch (InputMismatchException e) {
                                System.err.println("Hai inserito un valore non valido, riprova!");
                                s.next();
                            }

                            if (column <= this.getModel().getBoard().getNumberOfColumns() && column > 0) {
                                isInsertCorrect = true;
                            } else {
                                System.err.println("Inserisci una colonna valida (Un numero compreso tra 1 e " + this.getModel().getBoard().getNumberOfColumns() + "!");
                            }
                        }

                        if (checkIfPickable(row - 1, column - 1)) {
                            switch (counter) {
                                case 0 -> {
                                    counter++;
                                    firstRow = row;
                                    firstColumn = column;
                                    playerChoice.addTile(this.getModel().getBoard().getTiles()[row - 1][column - 1]);
                                    playerChoice.addCoordinates(new Coordinates(row - 1, column - 1));
                                }
                                case 1 -> {
                                    Direction res = checkIfInLine(row, column, firstRow, firstColumn);
                                    if (res != null) {
                                        directionToCheck = res;
                                        counter++;
                                        playerChoice.addTile(this.getModel().getBoard().getTiles()[row - 1][column - 1]);
                                        playerChoice.addCoordinates(new Coordinates(row - 1, column - 1));
                                    }
                                }
                                case 2 -> {
                                    if (checkIfInLine(row - 1, column - 1, playerChoice.getTileCoordinates(), directionToCheck)) {
                                        counter++;
                                        playerChoice.addTile(this.getModel().getBoard().getTiles()[row - 1][column - 1]);
                                        playerChoice.addCoordinates(new Coordinates(row - 1, column - 1));
                                    }
                                }
                                default -> {
                                    System.err.println("ERROR: Unexpected number of chosen tiles, found: " + counter + ", expected value < 3");
                                }
                            }
                        }
                        if (counter > 0 && counter != 3 && counter <= maxNumberOfCellsFreeInBookshelf) {
                            do {
                                System.out.println("Vuoi continuare? (Digita \"SI\" per continuare, \"NO\" per fermarti)");
                                input = s.next();
                            } while (!input.equalsIgnoreCase("SI") && !input.equalsIgnoreCase("NO"));
                        }
                    } while (!input.equalsIgnoreCase("NO") && counter < 3);


                    //---------------------------------SCELTA COLONNA---------------------------------
                    System.out.println(this.getModel().getPlayers().get(this.getModel().getActivePlayerIndex()).getBookshelf());


                    int chosenColumn = 0;
                    do {
                        isInsertCorrect = true;
                        System.out.println("Scegli la colonna in cui vuoi inserire le tue tessere:");
                        try {
                            chosenColumn = s.nextInt();

                            if (chosenColumn <= 0 || chosenColumn > this.getModel().getPlayers().get(0).getBookshelf().getNumberOfColumns()) {
                                isInsertCorrect = false;
                                System.err.println("Hai scelto una colonna al di fuori dei limiti della bookshelf, inserisci un valore compreso tra" +
                                        " 1 e " + this.getModel().getPlayers().get(0).getBookshelf().getNumberOfColumns() + "!");
                            }
                            if (this.getModel().getPlayers().get(this.getModel().getActivePlayerIndex()).getBookshelf().getNumberOfEmptyCellsInColumn(chosenColumn) + 1 <= counter) {
                                isInsertCorrect = false;
                                System.err.println("Hai scelto una colonna con un numero di spazi liberi non sufficiente per inserire le tessere scelte, riprova!");
                            }
                        } catch (InputMismatchException ignored) {
                            System.err.println("Non hai inserito un valore valido, riprova!");
                        }


                    } while (!isInsertCorrect);

                    playerChoice.setChosenColumn(chosenColumn - 1);


                    //---------------------------------SCELTA ORDINE---------------------------------

                    if (counter == 1) {
                        playerChoice.setTileOrder(new int[]{0});
                    } else {
                        System.out.println("Digita l'ordine con cui vuoi inserire le tessere (1 indica la prima tessera scelta, 2 la seconda e 3 la terza, ES: 1,3,2)");
                        isInsertCorrect = false;
                        do {
                            input = s.next();
                            String[] temp;
                            temp = input.split(",");
                            boolean res = false;
                            if (temp.length == counter) {
                                switch (counter) {
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
                                    System.err.println("Hai inserito delle cifre non coerenti con il numero di tessere scelte");
                                }
                            } else {
                                System.err.println("Hai inserito un numero di cifre diverso dal numero di tessere scelte. Oppure hai effettuato un inserimento che non rispetta" +
                                        " la formattazione richiesta, riprova!");
                            }
                        } while (!isInsertCorrect);
                    }

                    return playerChoice;
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

    private boolean checkIfInLine(int row, int column, List<Coordinates> prevTilesCoordinates, Direction directionToCheck) {
        if (prevTilesCoordinates.contains(new Coordinates(row, column))) {
            System.out.println("Non puoi scegliere di nuovo una tessera già scelta, riprova!");
            return false;
        }
        switch (directionToCheck) {
            case HORIZONTAL -> {
                if (row != prevTilesCoordinates.get(0).getX()) {
                    System.out.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
                    return false;
                } else {
                    for (Coordinates coordinates : prevTilesCoordinates) {
                        if (coordinates.getY() == column + 1 || coordinates.getY() == column - 1) {
                            return true;
                        }
                    }
                    System.out.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
                }
                return false;
            }
            case VERTICAL -> {
                if (column != prevTilesCoordinates.get(0).getY()) {
                    System.out.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
                    return false;
                } else {
                    for (Coordinates coordinates : prevTilesCoordinates) {
                        if (coordinates.getX() == row + 1 || coordinates.getX() == row - 1) {
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
        BoardView board = this.getModel().getBoard();
        TileView[][] boardMatrix = board.getTiles();

        if (boardMatrix[row][column]!=null && boardMatrix[row][column].getColor()!=null) {
            if ((row != 0 && (boardMatrix[row - 1][column]==null || boardMatrix[row - 1][column].getColor()==null)) ||
                    (row != board.getNumberOfRows() && (boardMatrix[row + 1][column]==null || boardMatrix[row + 1][column].getColor()==null)) ||
                    (column != board.getNumberOfColumns() && (boardMatrix[row][column + 1]==null || boardMatrix[row][column + 1].getColor()==null)) ||
                    (column != 0 && (boardMatrix[row][column - 1]==null || boardMatrix[row][column - 1].getColor()==null))) {
                return true;
            } else {
                System.out.println("Impossibile prendere la tessera (Ha tutti i lati occupati), riprova!");
            }
        } else {
            System.out.println("Non è presente nessuna tessera nella cella selezionata, riprova!");
        }

        return false;
    }

    @Override
    public void showPersonalRecap() {
        PlayerView activePlayer = this.getModel().getPlayers().get(this.getModel().getActivePlayerIndex());
        BookshelfView playerBookshelf = activePlayer.getBookshelf();
        PersonalGoalView playerPersonalGoal = activePlayer.getPersonalGoal();
        List<ScoreTileView> playerGoalTiles = activePlayer.getScoreTiles();

        int playerScore = activePlayer.score();

        List<CommonGoalView> commonGoals = this.getModel().getCommonGoals();

        System.out.println("Ecco il tuo recap:");
        System.out.println("Stato della tua bookshelf:\n" + playerBookshelf + "\n" +
                "Il tuo obiettivo personale:\n" + playerPersonalGoal + "\n" +
                "Gli obiettivi comuni sono:\n" + commonGoals.get(0) + "\n" + commonGoals.get(1) + "\n" +
                "Obiettivi comuni completati: Obiettivo1:" + (playerGoalTiles.size() > 0 && playerGoalTiles.get(0) != null ? playerGoalTiles.get(0) : "/") +
                ", Obiettivo2:" + (playerGoalTiles.size() > 0 && playerGoalTiles.get(1) != null ? playerGoalTiles.get(1) : "/") + ", Vittoria:" +
                (playerGoalTiles.size() > 0 && playerGoalTiles.get(2) != null ? playerGoalTiles.get(2) : "/") + " (Valore delle goalTile)" + "\n" +
                "Il tuo punteggio attuale " + playerScore);
    }

    //TODO: Si è rivelato necessario aggiungere in TileView un metodo isNull(), per verificare se l'attributo tileModel risultava nullo. Questo può accadere poichè la TileView viene
    //      creata a partire dalla Tile che può essere NULL cosa che non comporta che anche TileView lo sia (Da qui la necessità di questo metodo)

}
