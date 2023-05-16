package it.polimi.ingsw.view;

import com.sun.javafx.application.PlatformImpl;
import it.polimi.ingsw.GUI.LoginController;
import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.utils.CommandReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static javafx.application.Application.launch;

public class GUI extends UI{
    private Stage primaryStage;
    public GUI(GameView model){
        super(model);
    }

    public GUI() {super();
    }
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage=primaryStage;
        run();
    }
    public GUI(GameView model, ViewListener controller, String nicknameID) {
        super(model, controller, nicknameID);
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

    }

    @Override
    public void showPersonalRecap() {

    }
    @Override
    public void run() {

        //Creo la root della prima scena
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/FirstScene.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Mostro la prima scena
        primaryStage.setTitle("First Scene");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        //Aspetto che ci sia il numero giusto di giocatori
        int esci = 0;
        while(esci==0) {
            //Se il numero di giocatori diventa corretto
            if (getModel().getPlayers().size() == getModel().getNumberOfPlayers()) {
                //Notifico il controller dell'inizio partita
                this.controller.startGame();
                esci++;
                Parent secondRoot = null;
                //Carico la seconda scena e la mostro
                try {
                    secondRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MainScene.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                primaryStage.setTitle("Main Scene");
                primaryStage.setScene(new Scene(secondRoot));
            }
        }
    }
    public int addPlayer(String nickname){
        //Add the player to the game, if he is the first return 1
        this.controller.addPlayer(nickname);
        if (getModel().getPlayers().size() == 1) {
            return 1;
        }
        return 0;
    }
    public static void main(String[] args){
        launch(args);
    }
    public void waitWhileInState(State state) {
        synchronized (this.getLockState()) {
            switch (state) {
                case WAITING_IN_LOBBY -> {
                    System.out.println("Waiting...");
                }
                case WAITING_FOR_OTHER_PLAYER -> System.out.println("Waiting for others player moves...");
            }
            while (getState() == state) {
                try {
                    getLockState().wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void setNumberOfPlayer(int chosenNumberOfPlayer){
        //Setto il numero di player
        this.controller.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayer);
    }
}
