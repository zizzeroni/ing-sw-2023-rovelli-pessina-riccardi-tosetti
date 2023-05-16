package it.polimi.ingsw.GUI;

import it.polimi.ingsw.utils.CommandReader;
import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.view.State;
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
    //Fa schifo
    private GUI mainGui = new GUI();
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
            //Pass the nickname to the GUI
            int i = mainGui.addPlayer(Nickname.getText());
            //Se i è uguale a 1 devo scegliere il numero di giocatori
            //Altrimenti metto in pausa in attesa che arrivino giocatori
            if(i==1){
                changeScene();
            }else{
                mainGui.waitWhileInState(State.WAITING_IN_LOBBY);
            }
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
        //Cambio schermata a quella di inserimento numero giocatori
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
        //Inserisco la scelta del numero di giocatori e metto in attesa
        numberOfPlayer = NumberOfPlayerChoice.getValue();
        if (numberOfPlayer!=null&&!numberOfPlayer.isEmpty()){
            mainGui.setNumberOfPlayer(Integer.parseInt(numberOfPlayer));
            mainGui.waitWhileInState(State.WAITING_IN_LOBBY);
        }else{
            ErrorLabel.setText("Select the number of player!");
        }
    }
}
