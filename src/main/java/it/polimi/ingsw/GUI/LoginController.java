package it.polimi.ingsw.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    //Fa schifo
    private MainGui mainGui = new MainGui();
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
    private String numberOfPlayer;
    private String nickname;
    @FXML
    public void controlNickname(ActionEvent actionEvent) throws IOException{
        //Controllo se è corretto l'username
        nickname=Nickname.getText();
        if(!nickname.isEmpty()) {
            System.out.println("ciao " + Nickname.getText());
            changeScene();
        }else{
            ErrorLabel.setText("Insert a nickname!");
        }
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
        ErrorLabel.setText("");
        principalLabel.setFont(font);
        FirstButton.setVisible(false);
        Nickname.setVisible(false);
        PlayerOk.setVisible(true);
        NumberOfPlayerChoice.setVisible(true);
    }
    public void ControlNumberOfPlayer(ActionEvent actionEvent) throws IOException {
        numberOfPlayer = NumberOfPlayerChoice.getValue();
        if (numberOfPlayer!=null&&!numberOfPlayer.isEmpty()){
            User user=new User(numberOfPlayer, nickname);
            Stage stage = (Stage) FirstButton.getScene().getWindow();
            stage.setUserData(user);
            mainGui.startGame(stage);
        }else{
            ErrorLabel.setText("Select the number of player!");
        }
    }

}
