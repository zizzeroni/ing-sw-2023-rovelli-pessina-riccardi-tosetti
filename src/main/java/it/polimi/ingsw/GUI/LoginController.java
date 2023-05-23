package it.polimi.ingsw.GUI;

import it.polimi.ingsw.view.GUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    //Fa schifo
    private GUI mainGui;
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
    private final String[] playerNumber = {"2", "3", "4"};
    private String numberOfPlayerInGame;

    @FXML
    public void controlNickname(ActionEvent actionEvent) throws IOException, NotBoundException {

        //Controllo se è corretto l'username
        String nickname = Nickname.getText();
        if (!nickname.isEmpty()) {

            //Pass the nickname to the GUI
            mainGui.joinGameWithNick(Nickname.getText());
            //Se i è uguale a 1 devo scegliere il numero di giocatori
            //Altrimenti metto in pausa in attesa che arrivino giocatori
        } else {
            ErrorLabel.setText("Insert a nickname!");
        }
    }

    public void numberOfPlayer(boolean askNumberOfPlayer) {
        if (askNumberOfPlayer) {
            Platform.runLater(this::changeScene);
        } else {
            Platform.runLater(() -> {
                Font font = principalLabel.getFont();
                principalLabel.setText("Attesa di altri giocatori");
                ErrorLabel.setText("");
                principalLabel.setFont(font);
                FirstButton.setVisible(false);
                Nickname.setVisible(false);
            });

//            mainGui.waitWhileInState(State.WAITING_IN_LOBBY);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NumberOfPlayerChoice.getItems().setAll(playerNumber);
        PlayerOk.setVisible(false);
        NumberOfPlayerChoice.setVisible(false);
        ErrorLabel.setText("");
    }

    public void changeScene() {
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

    public void ControlNumberOfPlayer(ActionEvent actionEvent) throws IOException, InterruptedException {
        //Inserisco la scelta del numero di giocatori e metto in attesa
        numberOfPlayerInGame = NumberOfPlayerChoice.getValue();
        if (numberOfPlayerInGame != null && !numberOfPlayerInGame.isEmpty()) {

            PlayerOk.setVisible(false);
            NumberOfPlayerChoice.setVisible(false);


            Font font = principalLabel.getFont();
            principalLabel.setText("Attesa di altri giocatori");
            ErrorLabel.setText("");
            principalLabel.setFont(font);

        } else {
            ErrorLabel.setText("Select the number of player!");
        }
        Platform.runLater(() -> {
            if (numberOfPlayerInGame != null && !numberOfPlayerInGame.isEmpty()) {
                mainGui.setNumberOfPlayer(Integer.parseInt(numberOfPlayerInGame));
//            mainGui.waitWhileInState(State.WAITING_IN_LOBBY);
            }
        });

    }

    public void setMainGui(GUI gui) {
        this.mainGui = gui;
    }
}
