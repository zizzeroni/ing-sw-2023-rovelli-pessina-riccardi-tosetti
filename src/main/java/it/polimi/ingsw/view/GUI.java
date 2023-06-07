package it.polimi.ingsw.view;

import it.polimi.ingsw.GUI.LoginController;
import it.polimi.ingsw.GUI.MainSceneController;
import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.view.*;
import it.polimi.ingsw.network.ClientImpl;
import it.polimi.ingsw.network.Server;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static it.polimi.ingsw.AppClient.startPingSenderThread;

public class GUI extends UI {
    private LoginController loginController;
    private MainSceneController mainSceneController;
    private Stage primaryStage;
    private FXMLLoader loader;
    public GUI(GameView model) {
        super(model);
    }

    public GUI() {
        super();
    }

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        run();
    }
    public GUI(GameView model, ViewListener controller, String nickname) {
        super(model, controller, nickname);
    }

    public GUI(GameView model, ViewListener controller) {
        super(model, controller);
    }

    @Override
    public Choice askPlayer() {
        return null;
    }

    @Override
    public void showNewTurnIntro() {
        System.out.println("---NEW TURN---");
        int tileId;
        String tileColor;

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
                }
            }
        }
        for (int row = 0; row < boardView.getNumberOfRows(); row++) {
            for (int column = 0; column < boardView.getNumberOfColumns(); column++) {
                if (boardMatrix[row][column] != null && boardMatrix[row][column].getColor() != null) {
                    if ((row != 0 && (boardMatrix[row - 1][column] == null || boardMatrix[row - 1][column].getColor() == null)) ||
                            (row != boardView.getNumberOfRows() && (boardMatrix[row + 1][column] == null || boardMatrix[row + 1][column].getColor() == null)) ||
                            (column != boardView.getNumberOfColumns() && (boardMatrix[row][column + 1] == null || boardMatrix[row][column + 1].getColor() == null)) ||
                            (column != 0 && (boardMatrix[row][column - 1] == null || boardMatrix[row][column - 1].getColor() == null))) {

                        mainSceneController.ableTile(row, column);
                    } else {

                        mainSceneController.disableTile(row, column);
                    }
                }
            }
        }
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
            try {
                Registry registry = LocateRegistry.getRegistry();
                Server server = (Server) registry.lookup("server");
                new ClientImpl(server, this, nickname);
                startPingSenderThread(server);
            } catch (RemoteException | NotBoundException e) {
                throw new RuntimeException(e);
            }

            //Add the player to the game, if he is the first return 1
            this.setNickname(nickname);
            this.controller.addPlayer(nickname);

            boolean askNumberOfPlayer = this.getModel().getPlayers().size() == 1;

            loginController.numberOfPlayer(askNumberOfPlayer);

            //Aspetto che ci sia il numero giusto di giocatori
            boolean esci = true;
            while (esci) {
                //Se il numero di giocatori diventa corretto
                if (getModel().getPlayers().size() == getModel().getNumberOfPlayers()) {
                    //Notifico il controller dell'inizio partita
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
                //Devo abilitare tutte le tile
                //.unlockAllTiles
                //------------------------------------FIRST GAME RELATED INTERACTION------------------------------------
                showNewTurnIntro();
                //Questo metodo va sostituito con un metodo che aspetta che il player confermi la presa delle tiles
                //Choice choice = askPlayer();

                //---------------------------------NOTIFY CONTROLLER---------------------------------
                //this.controller.insertUserInputIntoModel(choice);
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Enter Input : ");
                try {
                    String s = br.readLine();
                    System.out.println(s);
                }catch(Exception e) {
                    System.out.println(e);
                }
                this.controller.changeTurn();
                //Devo disabilitare tutte le tile
                //.lockAllTiles();
            }
            System.out.println("---GAME ENDED---");
        });
        th.setUncaughtExceptionHandler((t, e) -> {
            System.err.println("Uncaught exception in thread");
            e.printStackTrace();
        });
        th.start();
    }

    public static void main(String[] args) {
        launch(args);
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

                    //Devo comunque aggiornare il giocatore di turno e la board (Qua?)
                }
            }
            while (getState() == state) {
                try {
                    //Devo comunque aggiornare il giocatore di turno e la board (o Qua?)
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

}
