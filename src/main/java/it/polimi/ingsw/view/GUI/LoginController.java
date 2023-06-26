package it.polimi.ingsw.view.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;

/**
 * This class contains a series of methods used to set and
 * to modify the primary scenes of the {@code MainGui}.
 * and to verify the user data when the different {@code Player}s
 * decide to log into the lobby.
 *
 * @see MainGui
 * @see it.polimi.ingsw.model.Player
 *
 */
public class LoginController implements Initializable {
    private GraphicalUI mainGraphicalUI;
    @FXML
    private Label principalLabel;
    @FXML
    private Label ErrorLabel;
    @FXML
    private TextField Nickname;
    @FXML
    private Button FirstButton;
    @FXML
    private Pane error;
    @FXML
    private ChoiceBox<String> NumberOfPlayerChoice;
    @FXML
    private Button PlayerOk;
    private final String[] playerNumber = {"2", "3", "4"};
    private String numberOfPlayerInGame;

    /**
     * Verifies if the username has been entered correctly.
     * In case no username is entered, calls an exception.
     * <p>
     * If an attempt is made to lookup or unbind in the registry a username
     * that has no associated binding, calls an exception.
     * <p>
     * If every control is passed successfully, passes the username
     * <p> to the GUI.
     *
     *
     * @param actionEvent is the event linked to username entering.
     * @throws IOException is the exception called if the wrong username has been passed as input.
     * @throws NotBoundException is the exception called when lookup or unbind in the registry
     *                              for username that has no associated binding is attempted.
     */
    @FXML
    public void controlNickname(ActionEvent actionEvent) throws IOException, NotBoundException {

        //Controllo se è corretto l'username
        String nickname = Nickname.getText();
        if (!nickname.isEmpty()) {
            //Pass the nickname to the GUI

            mainGraphicalUI.joinGameWithNick(Nickname.getText());
            //Se i è uguale a 1 devo scegliere il numero di giocatori
            //Altrimenti metto in pausa in attesa che arrivino giocatori
        } else {
            error.setVisible(true);
            ErrorLabel.setText("Insert a nickname!");
        }
    }

    /**
     * This method sets the number of players for the current {@code Game} in the GUI.
     *
     * @param askNumberOfPlayer is the boolean representing if the number of players has been entered
     *                          as consequence of an entering request displayed by the GUI.
     */
    public void numberOfPlayer(boolean askNumberOfPlayer) {
        if (askNumberOfPlayer) {
            Platform.runLater(this::changeScene);
        } else {
            Platform.runLater(() -> {
                Font font = principalLabel.getFont();
                principalLabel.setText("Attesa di altri giocatori");
                ErrorLabel.setText("");
                principalLabel.setFont(font);
                error.setVisible(false);
                FirstButton.setVisible(false);
                Nickname.setVisible(false);
            });
//            mainGui.waitWhileInState(State.WAITING_IN_LOBBY);
        }
    }

    /**
     * This method initialize the url and resource bundle used for the
     * setting of GUI scenes linked to the {@code Player}s login.
     *
     * @param url is the resources url.
     * @param resourceBundle is the bundle of the resources utilized in the scenes development.
     *
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        NumberOfPlayerChoice.getItems().setAll(playerNumber);
        PlayerOk.setVisible(false);
        NumberOfPlayerChoice.setVisible(false);
        error.setVisible(false);
        ErrorLabel.setText("");
    }

    /**
     * this method is used to change the current scene with that displaying the request
     * for entering the number of {@code Player}s for the current {@code Game}.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     */
    public void changeScene() {
        //Cambio schermata a quella di inserimento numero giocatori
        Font font = principalLabel.getFont();
        principalLabel.setText("Inserisci il numero di giocatori");
        error.setVisible(false);
        ErrorLabel.setText("");
        principalLabel.setFont(font);
        FirstButton.setVisible(false);
        Nickname.setVisible(false);
        PlayerOk.setVisible(true);
        NumberOfPlayerChoice.setVisible(true);
    }

    /**
     * This method is used to manage the wait on joining players during the lobby creation,
     * once the number of players for the current {@code Game} has been set.
     *
     * @param actionEvent the join event
     */
    public void ControlNumberOfPlayer(ActionEvent actionEvent) throws IOException, InterruptedException {
        //Inserisco la scelta del numero di giocatori e metto in attesa
        numberOfPlayerInGame = NumberOfPlayerChoice.getValue();
        if (numberOfPlayerInGame != null && !numberOfPlayerInGame.isEmpty()) {

            PlayerOk.setVisible(false);
            NumberOfPlayerChoice.setVisible(false);


            Font font = principalLabel.getFont();
            principalLabel.setText("Attesa di altri giocatori");
            error.setVisible(false);
            ErrorLabel.setText("");
            principalLabel.setFont(font);

        } else {
            error.setVisible(true);
            ErrorLabel.setText("Select the number of player!");
        }
        Platform.runLater(() -> {
            if (numberOfPlayerInGame != null && !numberOfPlayerInGame.isEmpty()) {
                mainGraphicalUI.setNumberOfPlayer(Integer.parseInt(numberOfPlayerInGame));

//            mainGui.waitWhileInState(State.WAITING_IN_LOBBY);
            }
        });

    }

<<<<<<< HEAD:src/main/java/it/polimi/ingsw/GUI/LoginController.java
    /**
     * Setter used to adjust the {@code mainGui}.
     *
     * @param gui the gui passed to be set
     *
     */
    public void setMainGui(GUI gui) {
        this.mainGui = gui;
=======
    public void setMainGui(GraphicalUI graphicalUI) {
        this.mainGraphicalUI = graphicalUI;
    }

    public void nicknameAlreadyUsed() {
        Platform.runLater(() -> {
        error.setVisible(true);
        ErrorLabel.setText("nickname already used!");
        });
>>>>>>> 859bad82d69f5d3a13cbdcd56fcc32f950648cfd:src/main/java/it/polimi/ingsw/view/GUI/LoginController.java
    }
}
