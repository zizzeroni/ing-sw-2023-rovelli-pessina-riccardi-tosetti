package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.exceptions.ExceptionType;
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
 * to modify the primary scenes of the main gui.
 * and to verify the user data when the different {@code Player}s
 * decide to log into the lobby.
 *
 * @see it.polimi.ingsw.view.GenericUILogic
 * @see it.polimi.ingsw.model.Player
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
    @FXML
    private Button restoreButton;
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
     * @param actionEvent is the event linked to username entering.
     * @throws IOException       is the exception called if the wrong username has been passed as input.
     * @throws NotBoundException is the exception called when lookup or unbind in the registry
     *                           for username that has no associated binding is attempted.
     */
    @FXML
    public void controlNickname(ActionEvent actionEvent) throws IOException, NotBoundException {

        //Controllo se è corretto l'username
        String nickname = Nickname.getText();
        if ((this.mainGraphicalUI.genericUILogic.getModel() != null) && (this.mainGraphicalUI.genericUILogic.getModel().getNumberOfPlayersToStartGame() == this.mainGraphicalUI.genericUILogic.getModel().getPlayers().size()) || !nickname.isEmpty()) {
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
                principalLabel.setText("Waiting for other player");
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
     * @param url            is the resources url.
     * @param resourceBundle is the bundle of the resources utilized in the scenes development.
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        NumberOfPlayerChoice.getItems().setAll(playerNumber);
        PlayerOk.setVisible(false);
        NumberOfPlayerChoice.setVisible(false);
        error.setVisible(false);
        ErrorLabel.setText("");
        restoreButton.setVisible(false);
    }

    /**
     * this method is used to change the current scene with that displaying the request
     * for entering the number of {@code Player}s for the current {@code Game}.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     */
    public void changeScene() {
        this.mainGraphicalUI.genericUILogic.getController().areThereStoredGamesForPlayer(this.mainGraphicalUI.genericUILogic.getNickname());

        if (this.mainGraphicalUI.genericUILogic.areThereStoredGamesForPlayer() && this.mainGraphicalUI.genericUILogic.getModel().getPlayers().size() == 1) {
            restoreButton.setVisible(true);
        }
        //Cambio schermata a quella di inserimento numero giocatori
        Font font = principalLabel.getFont();
        principalLabel.setText("Select the number of player");
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
    public void ControlNumberOfPlayer(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;
        numberOfPlayerInGame = NumberOfPlayerChoice.getValue();
        if (numberOfPlayerInGame != null && !numberOfPlayerInGame.isEmpty()) {

            PlayerOk.setVisible(false);
            NumberOfPlayerChoice.setVisible(false);
            Font font = principalLabel.getFont();
            principalLabel.setText("Waiting for other player");
            restoreButton.setVisible(false);
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

            }
        });
    }

    /**
     * Setter used to adjust the {@code mainGraphicalUI}.
     *
     * @param graphicalUI the Graphical User Interface passed to be set
     */
    public void setMainGui(GraphicalUI graphicalUI) {
        this.mainGraphicalUI = graphicalUI;
    }

    /**
     * set the label based on the error returned by the server
     *
     * @param exceptionType contains the error
     */
    public void nicknameException(ExceptionType exceptionType) {
        Platform.runLater(() -> {
            error.setVisible(true);
            if (exceptionType == ExceptionType.DUPLICATE_NICKNAME_EXCEPTION) {
                ErrorLabel.setText("nickname already used!");
            } else {
                ErrorLabel.setText("Game is already full!");
            }
        });
    }


    /**
     * Restore the game
     *
     * @param actionEvent action of pressing the button for restore the game
     */
    public void restoreGame(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof Button button))
            return;
        this.mainGraphicalUI.genericUILogic.getController().restoreGameForPlayer(this.mainGraphicalUI.genericUILogic.getNickname());
    }
}
