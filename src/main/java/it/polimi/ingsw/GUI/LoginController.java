package it.polimi.ingsw.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label principalLabel;
    @FXML
    private Label ErrorLabel;
    @FXML
    private TextField Nickname;
    @FXML
    private Button FirstButton;
    @FXML
    private ChoiceBox<String> NumberOfPlayerChoice;
    @FXML
    private Button PlayerOk;
    private final String[] playerNumber = {"2","3","4"};
    @FXML
    public void controlNickname(ActionEvent actionEvent) throws IOException{
        //Controllo se è corretto l'username
        System.out.println("ciao " + Nickname.getText());
        changeScene();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NumberOfPlayerChoice.getItems().setAll(playerNumber);
        PlayerOk.setVisible(false);
        NumberOfPlayerChoice.setVisible(false);
    }
    public void changeScene(){
        Font font = principalLabel.getFont();
        principalLabel.setText("Inserisci il numero di giocatori");
        principalLabel.setFont(font);
        FirstButton.setVisible(false);
        Nickname.setVisible(false);
        PlayerOk.setVisible(true);
        NumberOfPlayerChoice.setVisible(true);
    }
    public void ControlNumberOfPlayer(ActionEvent actionEvent) throws IOException {
        startGame();
    }
    public void startGame() throws IOException {
        Stage stage = (Stage) FirstButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MainScene.fxml"));

        stage.setTitle("Number of Player Scene");
        stage.setScene(new Scene(root));
    }

}
