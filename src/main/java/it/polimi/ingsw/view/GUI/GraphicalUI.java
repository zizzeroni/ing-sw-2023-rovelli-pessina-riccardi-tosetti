package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.ClientGameState;
import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.view.*;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientImpl;
import it.polimi.ingsw.network.Server;

import it.polimi.ingsw.network.socketMiddleware.ServerStub;
import it.polimi.ingsw.view.UI;
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

public class GraphicalUI extends UI {
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

    public GraphicalUI(GameView model) {
        super(model);
    }

    public GraphicalUI() {
        super();
    }

    public void start(Stage primaryStage) throws Exception {
        List<String> temp=this.getParameters().getRaw();
        this.typeOfConnection = Integer.parseInt(temp.get(0));
        this.ip = temp.get(1);
        this.port = temp.get(2);
        this.primaryStage = primaryStage;
        //this.primaryStage.set
        run();
    }
    public GraphicalUI(GameView model, ViewListener controller, String nickname) {
        super(model, controller, nickname);
    }

    public GraphicalUI(GameView model, ViewListener controller) {
        super(model, controller);
    }

    //TODO non usata
    @Override
    public Choice askPlayer() {return null;
    }

    @Override
    public void showNewTurnIntro() {
        int tileId;
        String tileColor;
        takenTiles=null;

        BoardView boardView = this.getModel().getBoard();
        //TileView[][] boardMatrix = boardView.getTiles();
        TileView[][] boardMatrix = this.getModel().getBoard().getTiles();

        mainSceneController.setTable();

        for (int row = 0; row < boardView.getNumberOfRows(); row++) {
            for (int column = 0; column < boardView.getNumberOfColumns(); column++) {
                if (boardMatrix[row][column] != null && boardMatrix[row][column].getColor() != null) {
                    tileId = boardMatrix[row][column].getImageID();
                    tileColor = boardMatrix[row][column].getColor().toGUI();
                    mainSceneController.setBoardTile(row, column, tileId, tileColor);
                }else{
                    mainSceneController.cancelBoardTile(row, column);
                }
            }
        }
        for (int row = 0; row < boardView.getNumberOfRows(); row++) {
            for (int column = 0; column < boardView.getNumberOfColumns(); column++) {
                if (boardMatrix[row][column] != null && boardMatrix[row][column].getColor() != null) {
                    if (row==8 || column ==8 || row == 0 || column==0 || ((boardMatrix[row - 1][column] == null || boardMatrix[row - 1][column].getColor() == null)) ||
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
        mainSceneController.setCommonGoalPoints(this.getModel().getCommonGoals());
        mainSceneController.setBookshelf(this.getModel().getPlayers());

    }

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
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void joinGameWithNick(String nickname) {
        var th = new Thread(() -> {
            if(typeOfConnection==2){
                ServerStub serverStub = new ServerStub(ip, Integer.parseInt(port));
                //Creating a new client with a TextualUI and a Socket Server
                Client client = null;
                try {
                    client = new ClientImpl(serverStub, this);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                //Creating a new Thread that will take care of checking on availability of connected client
                startPingSenderThread(serverStub);
                //Creating a new Thread that will take care of the responses coming from the Server side
                startReceiverThread(client, serverStub);
            }else {
                try {
                    Registry registry = LocateRegistry.getRegistry(ip, Integer.parseInt(port));
                    Server server = (Server) registry.lookup("server");
                    new ClientImpl(server, this);
                    startPingSenderThread(server);
                } catch (RemoteException | NotBoundException e) {
                    throw new RuntimeException(e);
                }
            }
            this.initializeChatThread(this.controller, this.getNickname(), this.getModel());
            //Add the player to the game, if he is the first return 1
            this.setNickname(nickname);
            this.controller.addPlayer(nickname);

            boolean askNumberOfPlayer = this.getModel().getPlayers().size() == 1;

            loginController.numberOfPlayer(askNumberOfPlayer);

            if(getModel().getPlayers().size() == getModel().getNumberOfPlayers()) {
                CountDownLatch countDownLatchStartGame = new CountDownLatch(1);
                Platform.runLater(() -> {
                    this.controller.startGame();
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
                if (getModel().getGameState()==GameState.ON_GOING) {
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
                        primaryStage.setScene(new Scene(secondRoot));
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
            mainSceneController.setNumberOfPlayer(getModel().getNumberOfPlayers());
            mainSceneController.setPlayersName(getModel().getPlayers());

            PlayerView activePlayer = this.getModel().getPlayers().stream().filter(player -> player.getNickname().equals(this.getNickname())).toList().get(0);
            mainSceneController.setPersonalGoal(activePlayer.getPersonalGoal());

            List<CommonGoalView> commonGoals = this.getModel().getCommonGoals();
            mainSceneController.setCommonGoal(commonGoals);
            showNewTurnIntro();

            while (this.getState() != ClientGameState.GAME_ENDED) {
                //------------------------------------WAITING OTHER PLAYERS-----------------------------------
                waitWhileInState(ClientGameState.WAITING_FOR_OTHER_PLAYER);
                if (this.getState() == ClientGameState.GAME_ENDED) break;
                showNewTurnIntro();
                //------------------------------------FIRST GAME RELATED INTERACTION------------------------------------
                while (takenTiles == null) {
                    Thread.onSpinWait();
                    //Aspetto che arrivino le scelte del player;
                }
                this.controller.insertUserInputIntoModel(takenTiles);
                //---------------------------------NOTIFY CONTROLLER---------------------------------
                this.controller.changeTurn();

            }
            System.out.println("---GAME ENDED---");
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
                primaryStage.setScene(new Scene(lastRoot));
                countDownLatch.countDown();
                finalSceneController.setScene(primaryStage.getScene());
                finalSceneController.showResult(this.getModel().getPlayers());
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
        synchronized (this.getLockState()) {
            switch (state) {
                //Questo non dovrebbe più servire
                case WAITING_IN_LOBBY -> {
                    System.out.println("Waiting...");
                }
                case WAITING_FOR_OTHER_PLAYER -> {
                    System.out.println("Waiting for others player moves...");
                    mainSceneController.lockAllTiles();
                }
            }
            while (getState() == state) {
                showUpdateFromOtherPlayer();
                try {
                    getLockState().wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void setNumberOfPlayer(int chosenNumberOfPlayer) {
        //Setto il numero di player
        this.controller.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayer);
    }

    public void finishTurn(Choice takenTiles) {
        this.takenTiles=takenTiles;
    }

    public void showUpdateFromOtherPlayer() {
        int tileId;
        String tileColor;

        BoardView boardView = this.getModel().getBoard();
        TileView[][] boardMatrix = this.getModel().getBoard().getTiles();

        for (int row = 0; row < boardView.getNumberOfRows(); row++) {
            for (int column = 0; column < boardView.getNumberOfColumns(); column++) {
                if (boardMatrix[row][column] != null && boardMatrix[row][column].getColor() != null) {
                    tileId = boardMatrix[row][column].getImageID();
                    tileColor = boardMatrix[row][column].getColor().toGUI();
                    mainSceneController.setBoardTile(row, column, tileId, tileColor);
                }else{
                    mainSceneController.cancelBoardTile(row, column);
                }
            }
        }
        mainSceneController.setBookshelf(this.getModel().getPlayers());
        mainSceneController.setCommonGoalPoints(this.getModel().getCommonGoals());
    }

    public void rescale(){
        if(resizing){
            double widthWindow = primaryStage.getScene().getWidth();
            double heightWindow = primaryStage.getScene().getHeight();

            widthOld=widthWindow;
            heightOld=heightWindow;

            Scale scale = new Scale(widthWindow, heightWindow, 0, 0);
            primaryStage.getScene().lookup("#mainPage").getTransforms().add(scale);
        }
    }
    private static void startReceiverThread(Client client, ServerStub serverStub) {
        //Creating a new Thread that will take care of the responses coming from the Server side
        new Thread(() -> {
            while (true) {
                try {
                    serverStub.receive(client);
                } catch (RemoteException e) {
                    System.err.println("[COMMUNICATION:ERROR] Error while receiving message from server (Server was closed)");
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

}
