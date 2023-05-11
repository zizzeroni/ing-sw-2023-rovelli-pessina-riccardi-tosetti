package it.polimi.ingsw.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIController {
    @FXML
    private Label ErrorLabel;
    @FXML
    private TextField Nickname;
    @FXML
    private Button FirstButton;
    @FXML
    private ChoiceBox NumberOfPlayerChoice;

    private final String[] playerNumber = {"2","3","4"};
    @FXML
    public void ControlNickname(ActionEvent actionEvent) throws IOException{
        //Controllo se è corretto l'username
        ErrorLabel.setText("Errore");
        CambiaPagina();
    }

    @FXML
    public void CambiaPagina() throws IOException {
        Stage stage = (Stage) FirstButton.getScene().getWindow();
        Stage primaryStage = new Stage();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/NumberOfPlayerScene.fxml"));
        primaryStage.setTitle("Number of Player Scene");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


}
