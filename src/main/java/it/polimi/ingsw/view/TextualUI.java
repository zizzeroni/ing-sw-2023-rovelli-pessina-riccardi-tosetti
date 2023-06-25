package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.commongoal.Direction;
import it.polimi.ingsw.model.view.*;
import it.polimi.ingsw.model.exceptions.GenericException;
import it.polimi.ingsw.utils.CommandReader;
import it.polimi.ingsw.utils.OptionsValues;
import it.polimi.ingsw.view.GUI.UI;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TextualUI implements UI {
    private final GenericUILogic genericUILogic;

    public TextualUI(GenericUILogic genericUILogic) {
        this.genericUILogic = genericUILogic;
    }

    public TextualUI() {
        this.genericUILogic = new GenericUILogic();
    }

    private void firstInteractionWithUser() {
        System.out.println("Benvenuto a MyShelfie, inserisci il tuo nickname!");

        this.genericUILogic.initializeChatThread(this.genericUILogic.controller, this.genericUILogic.getNickname(), this.genericUILogic.getModel());

        do {
            this.genericUILogic.setExceptionToHandle(null);
            System.out.println("Inserisci il tuo nickname!");
            String nickname = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();

            this.setNickname(nickname);
            this.genericUILogic.controller.addPlayer(this.genericUILogic.getNickname());

            if (this.genericUILogic.getExceptionToHandle() != null) {
                this.genericUILogic.getExceptionToHandle().handle();
            }

        } while (this.genericUILogic.getExceptionToHandle() != null);

        int chosenNumberOfPlayer = 0;
        if (genericUILogic.getModel().getPlayers().size() == 1) {
            do {
                System.out.println("Sei il primo giocatore, per quante persone vuoi creare la lobby? (Min:2, Max:4)");
                chosenNumberOfPlayer = CommandReader.standardCommandQueue.waitAndGetFirstIntegerCommandAvailable();
            } while (chosenNumberOfPlayer < OptionsValues.MIN_SELECTABLE_NUMBER_OF_PLAYERS || chosenNumberOfPlayer > OptionsValues.MAX_SELECTABLE_NUMBER_OF_PLAYERS);
            this.genericUILogic.controller.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayer);
        }

        if (genericUILogic.getModel().getPlayers().size() == genericUILogic.getModel().getNumberOfPlayers() && genericUILogic.getModel().getGameState() == GameState.IN_CREATION) {
            this.genericUILogic.controller.startGame();
        }
        System.out.println(this.genericUILogic.getState());
        waitWhileInState(ClientGameState.WAITING_IN_LOBBY);
    }

    private void waitWhileInState(ClientGameState clientGameState) {
        synchronized (this.genericUILogic.getLockState()) {
            switch (clientGameState) {
                case WAITING_IN_LOBBY -> {
                    System.out.println("Waiting for the game to start...");
                }
                case WAITING_FOR_OTHER_PLAYER ->
                        System.out.println("Waiting for others player moves: " + this.genericUILogic.getModel().getPlayers().get(this.genericUILogic.getModel().getActivePlayerIndex()).getNickname() + "...");
            }
            while (genericUILogic.getState() == clientGameState) {
                try {
                    genericUILogic.getLockState().wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private Thread createNewPrintCountdownThread() {
        AtomicInteger countdown = new AtomicInteger(genericUILogic.getCountdown() - 1);
        return new Thread(() -> {
            System.out.println("You are the last player in the game, 15 seconds remaining to win the game...");
            System.out.print(genericUILogic.getCountdown());
            for (; countdown.get() > -1; countdown.getAndDecrement()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                    System.out.println("\nA player has rejoined, game resumed...");
                    return;
                }
                System.out.print("," + countdown);
            }
        });
    }

    private void waitWhileInStates(List<ClientGameState> gameStates) {
        boolean firstTime = true;
        Thread printCountdownTh = this.createNewPrintCountdownThread();

        synchronized (this.genericUILogic.getLockState()) {
            switch (genericUILogic.getState()) {
                case WAITING_IN_LOBBY -> {
                    System.out.println("Waiting for the game to start...");
                }
                case WAITING_FOR_OTHER_PLAYER ->
                        System.out.println("Waiting for others player moves: " + this.genericUILogic.getModel().getPlayers().get(this.genericUILogic.getModel().getActivePlayerIndex()).getNickname() + "...");
            }
            while (gameStates.contains(genericUILogic.getState())) {
                try {
                    genericUILogic.getLockState().wait();

                    if (this.genericUILogic.getState() == ClientGameState.WAITING_FOR_RESUME) {
                        if (firstTime) {
                            firstTime = false;
                            printCountdownTh.start();
                        }
                    } else {
                        if (this.genericUILogic.getState() != ClientGameState.GAME_ENDED) {
                            printCountdownTh.interrupt();
                            printCountdownTh = this.createNewPrintCountdownThread();
                            firstTime = true;
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void registerListener(ViewListener listener) {
        this.genericUILogic.registerListener(listener);
    }

    @Override
    public void removeListener() {
        this.genericUILogic.removeListener();
    }

    @Override
    public void setNickname(String nickname) {
        this.genericUILogic.setNickname(nickname);
    }

    @Override
    public void modelModified(GameView modelUpdated) {
        this.genericUILogic.modelModified(modelUpdated);
    }

    @Override
    public void printException(GenericException exception) {
        this.genericUILogic.printException(exception);
    }

    @Override
    public void run() {
        //------------------------------------ADDING PLAYER TO THE LOBBY------------------------------------
        firstInteractionWithUser();

        while (this.genericUILogic.getState() != ClientGameState.GAME_ENDED) {
            //------------------------------------WAITING OTHER PLAYERS-----------------------------------
            //waitWhileInState(ClientGameState.WAITING_FOR_OTHER_PLAYER);
            waitWhileInStates(Arrays.asList(ClientGameState.WAITING_FOR_OTHER_PLAYER, ClientGameState.WAITING_FOR_RESUME));
            if (this.genericUILogic.getState() == ClientGameState.GAME_ENDED) break;
            //------------------------------------FIRST GAME RELATED INTERACTION------------------------------------
            showNewTurnIntro();
            Choice choice = askPlayer();
            //---------------------------------NOTIFY CONTROLLER---------------------------------
            this.genericUILogic.controller.insertUserInputIntoModel(choice);
            if (this.genericUILogic.getExceptionToHandle() != null) {
                this.genericUILogic.getExceptionToHandle().handle();
                this.genericUILogic.setExceptionToHandle(null);
            }
            this.genericUILogic.controller.changeTurn();
        }
        if(this.genericUILogic.getExceptionToHandle()!=null) {
            this.genericUILogic.getExceptionToHandle().handle();
        } else {
            showPersonalRecap();
            System.out.println("---GAME ENDED---");
        }
    }

    public void showNewTurnIntro() {
        System.out.println("---NEW TURN---");
        String activePlayerNickname = this.genericUILogic.getModel().getPlayers().get(this.genericUILogic.getModel().getActivePlayerIndex()).getNickname();
        System.out.println("Tocca a te player: " + activePlayerNickname + "!");
        System.out.println("Stato della board attuale:");
        System.out.println(this.genericUILogic.getModel().getBoard());
    }

    private int rowColumnTileChoiceFromBoard(int iterationCount, boolean isRowBeingChosen) {
        boolean isInsertCorrect = false;
        int choice = 0;
        while (!isInsertCorrect) {
            System.out.println("Inserisci la " + (isRowBeingChosen ? "riga" : "colonna") + " della " + (iterationCount + 1) + "° tessera che vuoi prendere:");
            try {
                choice = CommandReader.standardCommandQueue.waitAndGetFirstIntegerCommandAvailable();
            } catch (InputMismatchException e) {
                System.err.println("Hai inserito un valore non valido, riprova!");
                CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
            }

            if (choice > 0 && choice <= (isRowBeingChosen ? this.genericUILogic.getModel().getBoard().getNumberOfRows() : this.genericUILogic.getModel().getBoard().getNumberOfColumns())) {
                isInsertCorrect = true;
            } else {
                System.err.println("Inserisci una " + (isRowBeingChosen ? "riga" : "colonna") + " valida (Un numero compreso tra 1 e " + (isRowBeingChosen ? this.genericUILogic.getModel().getBoard().getNumberOfRows() : this.genericUILogic.getModel().getBoard().getNumberOfColumns()) + "!)");
            }
        }
        return choice;
    }

    private int bookshelfColumnChoice(int iterationCount) {
        boolean isInsertCorrect;
        int chosenColumn = 0;
        do {
            isInsertCorrect = true;
            System.out.println("Scegli la colonna in cui vuoi inserire le tue tessere:");
            try {
                chosenColumn = CommandReader.standardCommandQueue.waitAndGetFirstIntegerCommandAvailable();
                if (chosenColumn <= 0 || chosenColumn > this.genericUILogic.getModel().getPlayers().get(0).getBookshelf().getNumberOfColumns()) {
                    isInsertCorrect = false;
                    System.err.println("Hai scelto una colonna al di fuori dei limiti della bookshelf, inserisci un valore compreso tra" +
                            " 1 e " + this.genericUILogic.getModel().getPlayers().get(0).getBookshelf().getNumberOfColumns() + "!");
                } else {
                    if (this.genericUILogic.getModel().getPlayers().get(this.genericUILogic.getModel().getActivePlayerIndex()).getBookshelf().getNumberOfEmptyCellsInColumn(chosenColumn - 1) < iterationCount) {
                        isInsertCorrect = false;
                        System.err.println("Hai scelto una colonna con un numero di spazi liberi non sufficiente per inserire le tessere scelte, riprova!");
                    }
                }
            } catch (InputMismatchException ignored) {
                isInsertCorrect = false;
                System.err.println("Non hai inserito un valore valido, riprova!");
                CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
            }
        } while (!isInsertCorrect);
        return chosenColumn;
    }

    public Choice askPlayer() {
        while (true) {
            System.out.println("Seleziona l'azione(Digita il numero associato all'azione):");
            System.out.println("1)Recap situazione personale");
            System.out.println("2)Scegli tessere");
            System.out.println("3)Invia messaggio tramite chat");
            System.out.println("4)Disconnettiti");
            String input = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
            switch (input) {
                case "1" -> {
                    showPersonalRecap();
                }
                case "2" -> {
                    System.out.println("La situazione della board attuale:");
                    System.out.println(this.genericUILogic.getModel().getBoard());

                    int counter = 0, firstRow = 0, firstColumn = 0;

                    Choice playerChoice = new Choice();
                    Direction directionToCheck = null;
                    int maxNumberOfCellsFreeInBookshelf;
                    //---------------------------------SCELTA COORDINATE TESSERE---------------------------------
                    maxNumberOfCellsFreeInBookshelf = this.genericUILogic.getModel().getPlayers().get(this.genericUILogic.getModel().getActivePlayerIndex()).getBookshelf().getMaxNumberOfCellsFreeInBookshelf();
                    do {
                        int row, column;
                        row = rowColumnTileChoiceFromBoard(counter, true);
                        column = rowColumnTileChoiceFromBoard(counter, false);

                        if (checkIfPickable(row - 1, column - 1)) {
                            switch (counter) {
                                case 0 -> {
                                    counter++;
                                    firstRow = row;
                                    firstColumn = column;
                                    playerChoice.addTile(this.genericUILogic.getModel().getBoard().getTiles()[row - 1][column - 1]);
                                    playerChoice.addCoordinates(new Coordinates(row - 1, column - 1));
                                }
                                case 1 -> {
                                    Direction res = checkIfInLine(row, column, firstRow, firstColumn);
                                    if (res != null) {
                                        directionToCheck = res;
                                        counter++;
                                        playerChoice.addTile(this.genericUILogic.getModel().getBoard().getTiles()[row - 1][column - 1]);
                                        playerChoice.addCoordinates(new Coordinates(row - 1, column - 1));
                                    }
                                }
                                case 2 -> {
                                    if (checkIfInLine(row - 1, column - 1, playerChoice.getTileCoordinates(), directionToCheck)) {
                                        counter++;
                                        playerChoice.addTile(this.genericUILogic.getModel().getBoard().getTiles()[row - 1][column - 1]);
                                        playerChoice.addCoordinates(new Coordinates(row - 1, column - 1));
                                    }
                                }
                                default -> {
                                    System.err.println("[INPUT:ERROR]: Unexpected number of chosen tiles, found: " + counter + ", expected value < 3");
                                }
                            }
                        }
                        if (counter < maxNumberOfCellsFreeInBookshelf) {
                            if (counter > 0 && counter != OptionsValues.MAX_NUMBER_PICKABLE_TILES) {
                                do {
                                    System.out.println("Vuoi continuare? (Digita \"SI\" per continuare, \"NO\" per fermarti)");
                                    input = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
                                } while (!input.equalsIgnoreCase("SI") && !input.equalsIgnoreCase("NO"));
                            }
                        } else {
                            input = "NO";
                        }
                    } while (!input.equalsIgnoreCase("NO") && counter < 3);

                    //---------------------------------SCELTA COLONNA---------------------------------
                    System.out.println(this.genericUILogic.getModel().getPlayers().get(this.genericUILogic.getModel().getActivePlayerIndex()).getBookshelf());

                    int chosenColumn = bookshelfColumnChoice(counter);

                    playerChoice.setChosenColumn(chosenColumn - 1);
                    //---------------------------------SCELTA ORDINE---------------------------------

                    if (counter == 1) {
                        playerChoice.setTileOrder(new int[]{0});
                    } else {
                        System.out.println("Digita l'ordine con cui vuoi inserire le tessere (1 indica la prima tessera scelta, 2 la seconda e 3 la terza, ES: 1,3,2)");
                        boolean isInsertCorrect = false;
                        do {
                            input = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
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
                                        System.err.println("[INPUT:ERROR] Unexpected value of chosen tiles");
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
                    String receiver = null;

                    System.out.println("Che tipo di messaggio vuoi inviare? Pubblico (B)/ Privato (P)");
                    String messageType = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();

                    if (messageType.equals("P")) {
                        System.out.println("A chi vuoi inviare il messaggio?");
                        receiver = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
                    }

                    System.out.println("Inserisci il tuo messaggio qui");
                    String content = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();

                    if (messageType.equals("P")) {
                        this.genericUILogic.controller.sendPrivateMessage(this.genericUILogic.getNickname(), receiver, content);
                    } else if (messageType.equals("B")) {
                        this.genericUILogic.controller.sendBroadcastMessage(this.genericUILogic.getNickname(), content);
                    } else {
                        System.err.println("La tipologia di messaggio specificata non è riconosciuta, utilizzarne una valida");
                    }
                    //   } while (!isInsertCorrect);

                }
                case "4" -> {
                    this.genericUILogic.controller.disconnectPlayer(this.genericUILogic.getNickname());
                    System.err.println("Ti sei disconnesso dalla partita");
                    System.exit(0);
                }
                default -> {
                    System.err.println("Non hai inserito un valore valido, riprova! (Inserisci uno degli indici del menù)");
                }
            }
        }
    }

    private Direction checkIfInLine(int row, int column, int firstRow, int firstColumn) {
        if (row == firstRow && column == firstColumn) {
            System.err.println("Non puoi scegliere di nuovo una tessera già scelta, riprova!");
            return null;
        }
        if ((row == firstRow) && (column - 1 == firstColumn || column + 1 == firstColumn)) {
            return Direction.HORIZONTAL;
        }
        if ((column == firstColumn) && (row - 1 == firstRow || row + 1 == firstRow)) {
            return Direction.VERTICAL;
        }
        System.err.println("Le tessere selezionate devono formare una linea retta ed essere adiacenti, riprova!");
        return null;
    }

    private boolean checkIfInLine(int row, int column, List<Coordinates> prevTilesCoordinates, Direction directionToCheck) {
        if (prevTilesCoordinates.contains(new Coordinates(row, column))) {
            System.err.println("Non puoi scegliere di nuovo una tessera già scelta, riprova!");
            return false;
        }
        switch (directionToCheck) {
            case HORIZONTAL -> {
                if (row != prevTilesCoordinates.get(0).getX()) {
                    System.err.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
                    return false;
                } else {
                    for (Coordinates coordinates : prevTilesCoordinates) {
                        if (coordinates.getY() == column + 1 || coordinates.getY() == column - 1) {
                            return true;
                        }
                    }
                    System.err.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
                }
                return false;
            }
            case VERTICAL -> {
                if (column != prevTilesCoordinates.get(0).getY()) {
                    System.err.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
                    return false;
                } else {
                    for (Coordinates coordinates : prevTilesCoordinates) {
                        if (coordinates.getX() == row + 1 || coordinates.getX() == row - 1) {
                            return true;
                        }
                    }
                    System.err.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
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
        BoardView board = this.genericUILogic.getModel().getBoard();
        TileView[][] boardMatrix = board.getTiles();

        if (boardMatrix[row][column] != null && boardMatrix[row][column].getColor() != null) {
            if ((row != 0 && (boardMatrix[row - 1][column] == null || boardMatrix[row - 1][column].getColor() == null)) ||
                    (row != board.getNumberOfRows() - 1 && (boardMatrix[row + 1][column] == null || boardMatrix[row + 1][column].getColor() == null)) ||
                    (column != board.getNumberOfColumns() - 1 && (boardMatrix[row][column + 1] == null || boardMatrix[row][column + 1].getColor() == null)) ||
                    (column != 0 && (boardMatrix[row][column - 1] == null || boardMatrix[row][column - 1].getColor() == null))) {
                return true;
            } else {
                System.err.println("Impossibile prendere la tessera (Ha tutti i lati occupati), riprova!");
            }
        } else {
            System.err.println("Non è presente nessuna tessera nella cella selezionata, riprova!");
        }
        return false;
    }

    //TODO: remove from UI
    public void showPersonalRecap() {
        PlayerView activePlayer = this.genericUILogic.getModel().getPlayers().stream().filter(player -> player.getNickname().equals(this.genericUILogic.getNickname())).toList().get(0);
        //PlayerView activePlayer = this.getModel().getPlayers().get(this.getModel().getActivePlayerIndex());
        BookshelfView playerBookshelf = activePlayer.getBookshelf();
        PersonalGoalView playerPersonalGoal = activePlayer.getPersonalGoal();
        List<ScoreTileView> playerGoalTiles = activePlayer.getGoalTiles();

        int playerScore = activePlayer.score();

        List<CommonGoalView> commonGoals = this.genericUILogic.getModel().getCommonGoals();

        System.out.println("Ecco il tuo recap:");
        System.out.println("Stato della tua bookshelf:\n" + playerBookshelf + "\n" +
                "Il tuo obiettivo personale:\n" + playerPersonalGoal + "\n" +
                "Gli obiettivi comuni sono:\n" + commonGoals.get(0) + "\n" + commonGoals.get(1) + "\n" +
                "Obiettivi comuni completati: Obiettivo1:" + (playerGoalTiles.size() > 0 && playerGoalTiles.get(0) != null ? playerGoalTiles.get(0).getValue() : "/") +
                ", Obiettivo2:" + (playerGoalTiles.size() > 1 && playerGoalTiles.get(1) != null ? playerGoalTiles.get(1).getValue() : "/") + ", Vittoria:" +
                (playerGoalTiles.size() > 2 && playerGoalTiles.get(2) != null ? playerGoalTiles.get(2).getValue() : "/") + " (Valore delle goalTile)" + "\n" +
                "Il tuo punteggio attuale " + playerScore);
    }

}
