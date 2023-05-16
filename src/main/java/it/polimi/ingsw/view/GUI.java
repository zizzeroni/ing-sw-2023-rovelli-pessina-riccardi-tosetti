package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.view.GameView;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class GUI extends UI{
    private Stage primaryStage;
    public GUI(GameView model){
        super(model);
    }

    public GUI() {
    }
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage=primaryStage;
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

    }

    @Override
    public void showPersonalRecap() {

    }
    @Override
    public void run() {
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/FirstScene.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        primaryStage.setTitle("First Scene");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        this.primaryStage=primaryStage;
    }

    public void startGame(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MainScene.fxml"));
        stage.setTitle("Main Scene");
        stage.setScene(new Scene(root));
    }
}
