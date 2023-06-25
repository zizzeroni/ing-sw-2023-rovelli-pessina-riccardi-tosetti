package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.commongoal.Direction;
import it.polimi.ingsw.model.view.*;
import it.polimi.ingsw.utils.CommandReader;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class TextualUI extends UI {

    public TextualUI(GameView model) {
        super(model);
    }

    public TextualUI() {
        super();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    private void firstInteractionWithUser() {
        System.out.println("Welcome to My Shelfie");
        this.initializeChatThread(this.controller, this.getNickname(), this.getModel());

        do {
            this.setExceptionToHandle(null);
            System.out.println("Insert your nickname!");
            String nickname = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();

            this.setNickname(nickname);
            this.controller.addPlayer(this.getNickname());

            if (this.getExceptionToHandle() != null) {
                this.getExceptionToHandle().handle();
            }

        } while (this.getExceptionToHandle() != null);

        this.controller.areThereStoredGamesForPlayer(this.getNickname());

        if (this.areThereStoredGamesForPlayer() && getModel().getPlayers().size() == 1) {
            String restoreGameChoice;
            do {
                System.out.println("There is a stored game for your user. Would you like to restore it? (Type \"YES\" to restore it, \"NO\" to delete it)");
                restoreGameChoice = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
            } while (!restoreGameChoice.equalsIgnoreCase("YES") && !restoreGameChoice.equalsIgnoreCase("NO"));

            if (restoreGameChoice.equalsIgnoreCase("YES")) {
                this.controller.restoreGameForPlayer(this.getNickname());
                System.out.println("Stored game has been restored correctly");

            } else if (restoreGameChoice.equalsIgnoreCase("NO")) {
                this.setUpLobby();
            }
        } else {
            this.setUpLobby();
        }
        System.out.println(this.getState());
        waitWhileInState(ClientGameState.WAITING_IN_LOBBY);
    }

    private void waitWhileInState(ClientGameState clientGameState) {
        synchronized (this.getLockState()) {
            switch (clientGameState) {
                case WAITING_IN_LOBBY -> {
                    System.out.println("Waiting...");
                }
                case WAITING_FOR_OTHER_PLAYER -> System.out.println("Waiting for others player moves...");
                case WAITING_FOR_RESUME ->
                        System.out.println("You are the last player in the game, 15 seconds remaining to win the game...");
            }
            while (getState() == clientGameState) {
                try {
                    getLockState().wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private Thread createNewPrintCountdownThread() {
        return new Thread(() -> {
            System.out.println("You are the last player in the game, 15 seconds remaining to win the game...");
            System.out.print(getCountdown());
            for (int countdown = getCountdown() - 1; countdown > -1; countdown--) {
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

        synchronized (this.getLockState()) {
            switch (getState()) {
                case WAITING_IN_LOBBY -> {
                    System.out.println("Waiting...");
                }
                case WAITING_FOR_OTHER_PLAYER -> System.out.println("Waiting for others player moves...");
                case WAITING_FOR_RESUME ->
                        System.out.println("You are the last player in the game, 15 seconds remaining to win the game...");
            }
            while (gameStates.contains(getState())) {
                try {
                    getLockState().wait();

                    if (this.getState() == ClientGameState.WAITING_FOR_RESUME) {
                        if (firstTime) {
                            firstTime = false;
                            printCountdownTh.start();
                        }
                    } else {
                        printCountdownTh.interrupt();
                        printCountdownTh = this.createNewPrintCountdownThread();
                        firstTime = true;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void run() {
        //------------------------------------ADDING PLAYER TO THE LOBBY------------------------------------
        firstInteractionWithUser();

        while (this.getState() != ClientGameState.GAME_ENDED) {
            //------------------------------------WAITING OTHER PLAYERS-----------------------------------
            //waitWhileInState(ClientGameState.WAITING_FOR_OTHER_PLAYER);
            waitWhileInStates(Arrays.asList(ClientGameState.WAITING_FOR_OTHER_PLAYER, ClientGameState.WAITING_FOR_RESUME));
            if (this.getState() == ClientGameState.GAME_ENDED) break;
            //------------------------------------FIRST GAME RELATED INTERACTION------------------------------------
            showNewTurnIntro();
            Choice choice = askPlayer();
            //---------------------------------NOTIFY CONTROLLER---------------------------------
            this.controller.insertUserInputIntoModel(choice);
            if (this.getExceptionToHandle() != null) {
                this.getExceptionToHandle().handle();
                this.setExceptionToHandle(null);
            }
            this.controller.changeTurn();
        }
        showPersonalRecap();
        System.out.println("---GAME ENDED---");
    }

    @Override
    public void showNewTurnIntro() {
        System.out.println("---NEW TURN---");
        String activePlayerNickname = this.getModel().getPlayers().get(this.getModel().getActivePlayerIndex()).getNickname();
        System.out.println("It's your turn: " + activePlayerNickname + "!");
        System.out.println("Current board state:");
        System.out.println(this.getModel().getBoard());
    }

    private int rowColumnTileChoiceFromBoard(int iterationCount, boolean isRowBeingChosen) {
        boolean isInsertCorrect = false;
        int choice = 0;
        while (!isInsertCorrect) {
            System.out.println("Insert the " + (isRowBeingChosen ? "row" : "column") + " of the " + (iterationCount + 1) + "° tile you want to take:");
            try {
                choice = CommandReader.standardCommandQueue.waitAndGetFirstIntegerCommandAvailable();
            } catch (InputMismatchException e) {
                System.err.println("A not valid value has been entered, try again!");
                CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
            }

            if (choice > 0 && choice <= (isRowBeingChosen ? this.getModel().getBoard().getNumberOfRows() : this.getModel().getBoard().getNumberOfColumns())) {
                isInsertCorrect = true;
            } else {
                System.err.println("Insert a valid " + (isRowBeingChosen ? "row" : "column") + " (A number between 1 and " + (isRowBeingChosen ? this.getModel().getBoard().getNumberOfRows() : this.getModel().getBoard().getNumberOfColumns()) + "!)");
            }
        }
        return choice;
    }

    private int bookshelfColumnChoice(int iterationCount) {
        boolean isInsertCorrect;
        int chosenColumn = 0;
        do {
            isInsertCorrect = true;
            System.out.println("Choose the column in which you want to insert the tiles:");
            try {
                chosenColumn = CommandReader.standardCommandQueue.waitAndGetFirstIntegerCommandAvailable();
                if (chosenColumn <= 0 || chosenColumn > this.getModel().getPlayers().get(0).getBookshelf().getNumberOfColumns()) {
                    isInsertCorrect = false;
                    System.err.println("You have choosen a column out of bookshelf's bounds. Insert a value between" +
                            " 1 and " + this.getModel().getPlayers().get(0).getBookshelf().getNumberOfColumns() + "!");
                } else {
                    if (this.getModel().getPlayers().get(this.getModel().getActivePlayerIndex()).getBookshelf().getNumberOfEmptyCellsInColumn(chosenColumn - 1) < iterationCount) {
                        isInsertCorrect = false;
                        System.err.println("You have choosen a column with not enough empty spaces to insert the chosen tiles, try again!");
                    }
                }
            } catch (InputMismatchException ignored) {
                isInsertCorrect = false;
                System.err.println("A not valid value has been entered, try again!");
                CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
            }
        } while (!isInsertCorrect);
        return chosenColumn;
    }

    @Override
    public Choice askPlayer() {
        while (true) {
            System.out.println("Choose what to do (Type the number paired to the action):");
            System.out.println("1)Recap personal state");
            System.out.println("2)Choose tiles");
//            System.out.println("3)Invia messaggio tramite chat");
//            System.out.println("4)Disconnettiti");
            String input = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
            switch (input) {
                case "1" -> {
                    showPersonalRecap();
                }
                case "2" -> {
                    System.out.println("Current board state:");
                    System.out.println(this.getModel().getBoard());

                    int counter = 0, firstRow = 0, firstColumn = 0;

                    Choice playerChoice = new Choice();
                    Direction directionToCheck = null;
                    int maxNumberOfCellsFreeInBookshelf;
                    //---------------------------------TILES COORDINATES CHOICE---------------------------------
                    maxNumberOfCellsFreeInBookshelf = this.getModel().getPlayers().get(this.getModel().getActivePlayerIndex()).getBookshelf().getMaxNumberOfCellsFreeInBookshelf();
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
                                    System.err.println("[INPUT:ERROR]: Unexpected number of chosen tiles, found: " + counter + ", expected value < 3");
                                }
                            }
                        }
                        if (counter < maxNumberOfCellsFreeInBookshelf) {
                            if (counter > 0 && counter != 3) {
                                do {
                                    System.out.println("Do you want to pick another tile? (Type \"YES\" to continue, \"NO\" to stop)");
                                    input = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
                                } while (!input.equalsIgnoreCase("YES") && !input.equalsIgnoreCase("NO"));
                            }
                        } else {
                            input = "NO";
                        }
                    } while (!input.equalsIgnoreCase("NO") && counter < 3);

                    //---------------------------------COLUMN CHOICE---------------------------------
                    System.out.println(this.getModel().getPlayers().get(this.getModel().getActivePlayerIndex()).getBookshelf());

                    int chosenColumn = bookshelfColumnChoice(counter);

                    playerChoice.setChosenColumn(chosenColumn - 1);
                    //---------------------------------ORDER CHOICE---------------------------------

                    if (counter == 1) {
                        playerChoice.setTileOrder(new int[]{0});
                    } else {
                        System.out.println("Choose the tiles' insertion order(1 stands for the first picked tile, 2 for the second and 3 for the third.");
                        System.out.println("An example of insertion is: 1,3,2)");
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
                                    System.err.println("Not fitting digits have been entered");
                                }
                            } else {
                                System.err.println("The number of digits that has been entered is different from the " +
                                        "number of chosen tiles or the given input does not meet the given format, try again!");
                            }
                        } while (!isInsertCorrect);
                    }

                    return playerChoice;
                }
//                case "3" -> {
//                    String receiver = null;
//
//                    System.out.println("Che tipo di messaggio vuoi inviare? Pubblico (B)/ Privato (P)");
//                    String messageType = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
//
//                    if (messageType.equals("P")) {
//                        System.out.println("A chi vuoi inviare il messaggio?");
//                        receiver = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
//                    }
//
//                    System.out.println("Inserisci il tuo messaggio qui");
//                    String content = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
//
//                    if (messageType.equals("P")) {
//                        this.controller.sendPrivateMessage(this.getNickname(), receiver, content);
//                    } else if (messageType.equals("B")) {
//                        this.controller.sendBroadcastMessage(this.getNickname(), content);
//                    } else {
//                        System.err.println("La tipologia di messaggio specificata non è riconosciuta, utilizzarne una valida");
//                    }
//                    //   } while (!isInsertCorrect);
//
//                }
//                case "4" -> {
//                    this.controller.disconnectPlayer(this.getNickname());
//                    System.err.println("Ti sei disconnesso dalla partita");
//                    System.exit(0);
//                }
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

    private boolean checkIfInLine(int row, int column, List<Coordinates> prevTilesCoordinates, Direction
            directionToCheck) {
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
        BoardView board = this.getModel().getBoard();
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
        PlayerView activePlayer = this.getModel().getPlayers().stream().filter(player -> player.getNickname().equals(this.getNickname())).toList().get(0);
        BookshelfView playerBookshelf = activePlayer.getBookshelf();
        PersonalGoalView playerPersonalGoal = activePlayer.getPersonalGoal();
        List<ScoreTileView> playerScoreTiles = activePlayer.getScoreTiles();

        int playerScore = activePlayer.score();

        List<CommonGoalView> commonGoals = this.getModel().getCommonGoals();

        System.out.println("Here is your recap:");
        System.out.println("Bookshelf's state:\n" + playerBookshelf + "\n" +
                "Personal goal:\n" + playerPersonalGoal + "\n" +
                "Common goals:\n" + commonGoals.get(0) + "\n" + commonGoals.get(1) + "\n" +
                "Completed common goals: First common goal:" + (playerScoreTiles.size() > 0 && playerScoreTiles.get(0) != null ? playerScoreTiles.get(0).getValue() : "/") +
                ", Second common goal:" + (playerScoreTiles.size() > 1 && playerScoreTiles.get(1) != null ? playerScoreTiles.get(1).getValue() : "/") + ", Victory:" +
                (playerScoreTiles.size() > 2 && playerScoreTiles.get(2) != null ? playerScoreTiles.get(2).getValue() : "/") + " (Score tiles values)" + "\n" +
                "Your current score: " + playerScore);
    }

    private void setUpLobby() {
        this.askNumberOfPlayers();

        if (getModel().getPlayers().size() == getModel().getNumberOfPlayers() && getModel().getGameState() == GameState.IN_CREATION) {
            this.controller.startGame();
        }
    }
    private void askNumberOfPlayers() {
        int chosenNumberOfPlayer = 0;
        if (getModel().getPlayers().size() == 1) {
            do {
                System.out.println("You're the first player, how many people will play? (Min:2, Max:4)");
                chosenNumberOfPlayer = CommandReader.standardCommandQueue.waitAndGetFirstIntegerCommandAvailable();
            } while (chosenNumberOfPlayer < 2 || chosenNumberOfPlayer > 4);
            this.controller.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayer);
        }
    }

}
