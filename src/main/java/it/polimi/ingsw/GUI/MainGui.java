package it.polimi.ingsw.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * This class represents the MainGui that the {@code Player} can choose to use
 * instead of the CLI in order to simplify his interactions during the {@code Game}.
 * It contains a series of methods that are used to sets and display scenes when the game is created.
 *
 * @see it.polimi.ingsw.model.Game
 * @see it.polimi.ingsw.model.Player
 */
public class MainGui extends Application{
    private static Stage primaryStage;

    /**
     * This method pass the primary stage linked to the first scene at the start of the {@code Game}.
     *
     * @param primaryStage the stage linked to the primary scene.
     * @throws IOException the exception raised in case the primaryStage doesn't exist.
     *
     * @see it.polimi.ingsw.model.Game
     */
    @Override
    public void start(Stage primaryStage)throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/FirstScene.fxml")));
        primaryStage.setTitle("First Scene");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        this.primaryStage=primaryStage;
    }
    public static void main(String[] args){
        launch(args);
    }

    /**
     * This method sets the first scene at the starting of the {@code Game}.
     *
     * @param stage the stage that will be first displayed during game setup.
     * @throws IOException the exception raised in case the Stage is not passed correctly.
     */
    public void startGame(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MainScene.fxml")));
        stage.setTitle("Main Scene");
        stage.setScene(new Scene(root));
    }
}
