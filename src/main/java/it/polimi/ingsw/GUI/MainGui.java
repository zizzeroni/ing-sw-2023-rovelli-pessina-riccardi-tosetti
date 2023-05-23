package it.polimi.ingsw.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGui extends Application{
    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage)throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getClassLoader().getResource("fxml/FirstScene.fxml"));
        primaryStage.setTitle("First Scene");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        this.primaryStage=primaryStage;
    }
    public static void main(String[] args){
        launch(args);
    }
    public void startGame(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MainScene.fxml"));
        stage.setTitle("Main Scene");
        stage.setScene(new Scene(root));
    }
}
