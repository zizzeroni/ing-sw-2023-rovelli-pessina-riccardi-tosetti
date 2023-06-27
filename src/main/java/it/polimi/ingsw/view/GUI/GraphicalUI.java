package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.exceptions.GenericException;
import it.polimi.ingsw.model.view.*;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientImpl;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.ServerStub;
import it.polimi.ingsw.utils.OptionsValues;
import it.polimi.ingsw.view.ClientGameState;
import it.polimi.ingsw.view.GenericUILogic;
import it.polimi.ingsw.view.TextualUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static it.polimi.ingsw.AppClient.startPingSenderThread;

/**
 * This class contains the GUI's methods.
 * It is developed as an extension of UI, because it shares some base methods
 * with the TextualUI
 *
 * @see UI
 * @see TextualUI
 */
public class GraphicalUI extends Application implements UI {
    Server serverConnectedTo;
    Client selfClient;
    protected GenericUILogic genericUILogic;
    private double widthOld, heightOld;
    private boolean resizing = true;
    private LoginController loginController;
    private MainSceneController mainSceneController;
    private FinalSceneController finalSceneController;
    private Stage primaryStage;
    private FXMLLoader loader;
    private volatile Choice takenTiles;
    private int typeOfConnection;
    private String ip;
    private String port;

    public double getWidthOld() {
        return widthOld;
    }

    public double getHeightOld() {
        return heightOld;
    }

    /**
     * Class constructor.
     * Initialize the game's model.
     *
     * @see GameView
     */
    public GraphicalUI(GenericUILogic genericUILogic) {
        this.genericUILogic = genericUILogic;
    }

    /**
     * Class constructor.
     * <p>
     * Sets the attributes as in the UI's superclass.
     *
     * @see UI
     */
    public GraphicalUI() {
        this.genericUILogic = new GenericUILogic();
    }

    public GraphicalUI(GameView model, ViewListener controller, String nickname) {
        this.genericUILogic = new GenericUILogic(model, controller, nickname);
    }

    public GraphicalUI(GameView model, ViewListener controller) {
        super();
        this.genericUILogic = new GenericUILogic(model, controller);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<String> temp = this.getParameters().getRaw();
        this.typeOfConnection = Integer.parseInt(temp.get(0));
        this.ip = temp.get(1);
        this.port = temp.get(2);
        this.primaryStage = primaryStage;
        //this.primaryStage.set

        if (typeOfConnection == 2) {
            serverConnectedTo = new ServerStub(ip, Integer.parseInt(port));
            //Creating a new client with a TextualUI and a Socket Server
            try {
                selfClient = new ClientImpl(serverConnectedTo, this);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            //Creating a new Thread that will take care of checking on availability of connected client
            startPingSenderThread(serverConnectedTo);
            //Creating a new Thread that will take care of the responses coming from the Server side
            startReceiverThread(selfClient, (ServerStub) serverConnectedTo);
        } else {
            try {
                Registry registry = LocateRegistry.getRegistry(ip, Integer.parseInt(port));
                serverConnectedTo = (Server) registry.lookup(OptionsValues.SERVER_RMI_NAME);
                new ClientImpl(serverConnectedTo, this);
                startPingSenderThread(serverConnectedTo);
            } catch (RemoteException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        }
        run();
    }

    /**
     * Displays a standard message to identify the starting of the next turn.
     * Calls the nickname of the active player and the shows the board's state.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Board
     */
    @Override
    public void showNewTurnIntro() {

        int tileId;
        String tileColor;
        takenTiles = null;

        BoardView boardView = this.genericUILogic.getModel().getBoard();
        //TileView[][] boardMatrix = boardView.getTiles();
        TileView[][] boardMatrix = this.genericUILogic.getModel().getBoard().getTiles();

        mainSceneController.setTable();

        //mainSceneController.setChat();
        //mainSceneController.updateChat();

        for (int row = 0; row < boardView.getNumberOfRows(); row++) {
            for (int column = 0; column < boardView.getNumberOfColumns(); column++) {
                if (boardMatrix[row][column] != null && boardMatrix[row][column].getColor() != null) {
                    tileId = boardMatrix[row][column].getId();
                    tileColor = boardMatrix[row][column].getColor().toGUI();
                    mainSceneController.setBoardTile(row, column, tileId, tileColor);
                } else {
                    mainSceneController.cancelBoardTile(row, column);
                }
            }
        }
        for (int row = 0; row < boardView.getNumberOfRows(); row++) {
            for (int column = 0; column < boardView.getNumberOfColumns(); column++) {
                if (boardMatrix[row][column] != null && boardMatrix[row][column].getColor() != null) {
                    if (row == 8 || column == 8 || row == 0 || column == 0 || ((boardMatrix[row - 1][column] == null || boardMatrix[row - 1][column].getColor() == null)) ||
                            (row != boardView.getNumberOfRows() && (boardMatrix[row + 1][column] == null || boardMatrix[row + 1][column].getColor() == null)) ||
                            (column != boardView.getNumberOfColumns() && (boardMatrix[row][column + 1] == null || boardMatrix[row][column + 1].getColor() == null)) ||
                            ((boardMatrix[row][column - 1] == null || boardMatrix[row][column - 1].getColor() == null))) {
                        mainSceneController.ableTile(row, column);
                    } else {
                        mainSceneController.disableTile(row, column);
                    }
                }
            }
        }
        mainSceneController.setCommonGoalPoints(this.genericUILogic.getModel().getCommonGoals());
        mainSceneController.setBookshelf(this.genericUILogic.getModel().getPlayers());

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
    public void setAreThereStoredGamesForPlayer(boolean result) {
        this.genericUILogic.setAreThereStoredGamesForPlayer(result);
    }

    /**
     * Performs the following in game actions. <p>
     * Creates the first scene root. <p>
     * Displays the first scene. <p>
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     * @see it.polimi.ingsw.controller.GameController
     */
    @Override
    public void run() {
        Parent root;
        //Creo la root della prima scena
        try {
            this.loader = new FXMLLoader();
            this.loader.setLocation(getClass().getClassLoader().getResource("fxml/FirstScene.fxml"));
            root = this.loader.load();
            loginController = this.loader.getController();
            loginController.setMainGui(this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Mostro la prima scena
        primaryStage.setTitle("First Scene");
        setPrimaryStage(new Scene(root));
        //primaryStage.setScene(new Scene(root));
        //primaryStage.show();
    }

    /**
     * Consents to a player to join the game's lobby using his nickname.
     * It is also used as follows to link the first the scene with the second: <p>
     * Waits to reach the given players number, notifies the controller of the game's start
     * then checks the second scene before displaying it.
     *
     * @param nickname the player's nickname.
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     * @see it.polimi.ingsw.controller.GameController
     */
    public void joinGameWithNick(String nickname) {
        var th = new Thread(() -> {

            this.genericUILogic.initializeChatThread(this.genericUILogic.getController(), this.genericUILogic.getNickname(), this.genericUILogic.getModel());
            this.genericUILogic.setExceptionToHandle(null);

            //Add the player to the game, if he is the first return 1
            this.setNickname(nickname);
            this.genericUILogic.getController().addPlayer(nickname);

            if (this.genericUILogic.getExceptionToHandle() != null) {
                loginController.nicknameAlreadyUsed();
                return;
            }
            boolean askNumberOfPlayer = this.genericUILogic.getModel().getPlayers().size() == 1;

            loginController.numberOfPlayer(askNumberOfPlayer);

            if (genericUILogic.getModel().getPlayers().size() == genericUILogic.getModel().getNumberOfPlayers() && genericUILogic.getModel().getGameState() == GameState.IN_CREATION) {
                CountDownLatch countDownLatchStartGame = new CountDownLatch(1);
                Platform.runLater(() -> {
                    this.genericUILogic.getController().startGame();
                    countDownLatchStartGame.countDown();
                });
                try {
                    countDownLatchStartGame.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //Aspetto che ci sia il numero giusto di giocatori
            boolean esci = true;
            while (esci) {
                //Se il numero di giocatori diventa corretto
                if (genericUILogic.getModel().getGameState() == GameState.ON_GOING) {
                    //Notifico il controller dell'inizio partita
                    esci = false;
                    Parent secondRoot;
                    //Carico la seconda scena e la mostro
                    try {
                        this.loader = new FXMLLoader();
                        this.loader.setLocation(getClass().getClassLoader().getResource("fxml/MainScene.fxml"));
                        secondRoot = this.loader.load();
                        mainSceneController = this.loader.getController();
                        mainSceneController.setMainGui(this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    CountDownLatch countDownLatch = new CountDownLatch(1);
                    Platform.runLater(() -> {
                        primaryStage.setTitle("Main Scene");
                        setPrimaryStage(new Scene(secondRoot));
                        //primaryStage.setScene(new Scene(secondRoot));
                        countDownLatch.countDown();
                    });
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            mainSceneController.setFirstPlayerNickname(nickname);
            mainSceneController.setScene(primaryStage.getScene());
            mainSceneController.setNumberOfPlayer(genericUILogic.getModel().getNumberOfPlayers());
            mainSceneController.setPlayersName(genericUILogic.getModel().getPlayers());

            PlayerView activePlayer = this.genericUILogic.getModel().getPlayers().stream().filter(player -> player.getNickname().equals(this.genericUILogic.getNickname())).toList().get(0);
            mainSceneController.setPersonalGoal(activePlayer.getPersonalGoal());

            List<CommonGoalView> commonGoals = this.genericUILogic.getModel().getCommonGoals();
            mainSceneController.setCommonGoal(commonGoals);
            showNewTurnIntro();

            if(!activePlayer.equals(this.genericUILogic.getModel().getPlayers().get(this.genericUILogic.getModel().getActivePlayerIndex()))){
                mainSceneController.lockAllTiles();
            }

            mainSceneController.setGameOn(true);
            mainSceneController.chatUpdate(true);
            while (this.genericUILogic.getState() != ClientGameState.GAME_ENDED) {
                //------------------------------------WAITING OTHER PLAYERS-----------------------------------
                waitWhileInState(ClientGameState.WAITING_FOR_OTHER_PLAYER);
                if (this.genericUILogic.getState() == ClientGameState.GAME_ENDED) break;
                showNewTurnIntro();
                //------------------------------------FIRST GAME RELATED INTERACTION------------------------------------
                while (takenTiles == null) {
                    Thread.onSpinWait();
                    //Aspetto che arrivino le scelte del player;
                }
                this.genericUILogic.getController().insertUserInputIntoModel(takenTiles);
                //---------------------------------NOTIFY CONTROLLER---------------------------------

                this.genericUILogic.getController().changeTurn();
                this.mainSceneController.refreshPoint();

            }
            mainSceneController.setGameOn(false);

            Parent lastRoot;
            //Carico l'ultima scena e la mostro
            try {
                this.loader = new FXMLLoader();
                this.loader.setLocation(getClass().getClassLoader().getResource("fxml/FinalScene.fxml"));
                lastRoot = this.loader.load();
                finalSceneController = this.loader.getController();
                finalSceneController.setMainGui(this);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            Platform.runLater(() -> {
                primaryStage.setTitle("Last Scene");
                setPrimaryStage(new Scene(lastRoot));
                //primaryStage.setScene(new Scene(lastRoot));
                countDownLatch.countDown();
                finalSceneController.setScene(primaryStage.getScene());
                finalSceneController.showResult(this.genericUILogic.getModel().getPlayers());
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
        th.setUncaughtExceptionHandler((t, e) -> {
            System.err.println("Uncaught exception in thread");
            e.printStackTrace();
        });
        th.start();
    }

    public void waitWhileInState(ClientGameState state) {
        synchronized (this.genericUILogic.getLockState()) {
            switch (state) {
                //Questo non dovrebbe più servire
                case WAITING_IN_LOBBY -> {
                    System.out.println("Waiting...");
                }
                case WAITING_FOR_OTHER_PLAYER -> {
                    System.out.println("Waiting for others player moves...");
                    //mainSceneController.updateChat();
                    mainSceneController.lockAllTiles();
                }
            }
            while (genericUILogic.getState() == state) {
                showUpdateFromOtherPlayer();
                try {
                    genericUILogic.getLockState().wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Sets the number of players for the current Game.
     *
     * @param chosenNumberOfPlayer the selected number of players.
     * @see it.polimi.ingsw.model.Game
     * @see it.polimi.ingsw.model.Player
     */
    public void setNumberOfPlayer(int chosenNumberOfPlayer) {
        //Setto il numero di player
        this.genericUILogic.getController().chooseNumberOfPlayerInTheGame(chosenNumberOfPlayer);
        var th = new Thread(() -> {
            if (genericUILogic.getModel().getPlayers().size() == genericUILogic.getModel().getNumberOfPlayers()) {
                CountDownLatch countDownLatchStartGame = new CountDownLatch(1);
                Platform.runLater(() -> {
                    this.genericUILogic.getController().startGame();
                    countDownLatchStartGame.countDown();
                });
                try {
                    countDownLatchStartGame.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        th.setUncaughtExceptionHandler((t, e) -> {
            System.err.println("Uncaught exception in thread");
            e.printStackTrace();
        });
        th.start();
    }

    public void finishTurn(Choice takenTiles) {
        this.takenTiles = takenTiles;
    }

    public void showUpdateFromOtherPlayer() {

        //mainSceneController.updateChat();
        int tileId;
        String tileColor;

        BoardView boardView = this.genericUILogic.getModel().getBoard();
        TileView[][] boardMatrix = this.genericUILogic.getModel().getBoard().getTiles();

        for (int row = 0; row < boardView.getNumberOfRows(); row++) {
            for (int column = 0; column < boardView.getNumberOfColumns(); column++) {
                if (boardMatrix[row][column] != null && boardMatrix[row][column].getColor() != null) {
                    tileId = boardMatrix[row][column].getId();
                    tileColor = boardMatrix[row][column].getColor().toGUI();
                    mainSceneController.setBoardTile(row, column, tileId, tileColor);
                } else {
                    mainSceneController.cancelBoardTile(row, column);
                }
            }
        }
        mainSceneController.setBookshelf(this.genericUILogic.getModel().getPlayers());
        mainSceneController.setCommonGoalPoints(this.genericUILogic.getModel().getCommonGoals());
    }

    private static void startReceiverThread(Client client, ServerStub serverStub) {
        //Creating a new Thread that will take care of the responses coming from the Server side
        new Thread(() -> {
            while (true) {
                try {
                    serverStub.receive(client);
                } catch (RemoteException e) {
                    System.err.println("[COMMUNICATION:ERROR] Error while receiving message from server (Server was closed)" + e.getMessage());
                    try {
                        serverStub.close();
                    } catch (RemoteException ex) {
                        System.err.println("[RESOURCE:ERROR] Cannot close connection with server. Halting...");
                    }
                    System.exit(1);
                }
            }
        }).start();
    }

    private void setPrimaryStage(Scene scene) {
        resizing = false;
        primaryStage.setScene(scene);
        primaryStage.show();

        widthOld = primaryStage.getScene().getWidth();
        heightOld = primaryStage.getScene().getHeight();
        this.primaryStage.widthProperty().addListener((obs, oldVal, newV) -> {
            rescale((double) newV, heightOld);
        });
        this.primaryStage.heightProperty().addListener((obs, oldVal, newV) -> {
            rescale(widthOld, (double) newV);
        });
        resizing = true;
    }

    public void rescale(double wi, double he) {
        if (resizing) {
            double w = wi / widthOld;
            double h = he / heightOld;

            widthOld = wi;
            heightOld = he;

            Scale scale = new Scale(w, h, 0, 0);
            primaryStage.getScene().lookup("#content").getTransforms().add(scale);
        }
    }
}
