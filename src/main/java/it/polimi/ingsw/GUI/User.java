package it.polimi.ingsw.GUI;

/**
 * The User class is used to represent the active {@code Player}s
 * in the visualization provided through the GUI.
 *
 */
public class User {
    private String numberOfPlayer;
    private String firstPlayerNickname;

    /**
     * This is the class builder.
     * Initially sets both the {@code numberOfPlayer} and the {@code firstPlayerNickname}
     * to the default value of a void string.
     */
    public User() {
        this.numberOfPlayer = "";
        this.firstPlayerNickname = "";
    }

    /**
     * In this implementation the class builder sets both the {@code numberOfPlayer}
     * and the {@code firstPlayerNickname} to their respective values.
     *
     * @param numberOfPlayer the number of currently active {@code Player}s.
     * @param firstPlayerNickname the nickname of the first player.
     *
     * @see it.polimi.ingsw.model.Player
     * @see User#numberOfPlayer
     * @see User#firstPlayerNickname
     */
    public User(String numberOfPlayer, String firstPlayerNickname) {
        this.numberOfPlayer = numberOfPlayer;
        this.firstPlayerNickname = firstPlayerNickname;
    }

    /**
     * Gets the identifying numbers of the active players.
     *
     * @return the id-number of the {@code Player}.
     *
     * @see it.polimi.ingsw.model.Player
     */
    public String getNumberOfPlayer() {
        return numberOfPlayer;
    }

    /**
     * Sets the identifying numbers of players for the active {@code Game}
     * to the given parameter.
     *
     * @param numberOfPlayer the identifying numbers of the active players.
     */
    public void setNumberOfPlayer(String numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    /**
     * Gets the nickname of the first {@code Player} in the active {@code Game}.
     *
     * @return the nickname of the first player.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     */
    public String getFirstPlayerNickname() {
        return firstPlayerNickname;
    }

    /**
     * Sets the nickname of the first {@code Player} in the active {@code Game}.
     * to the given parameter.
     *
     * @param firstPlayerNickname is the nickname assigned to the first player.
     *
     * @see it.polimi.ingsw.model.Game
     * @see it.polimi.ingsw.model.Player
     */
    public void setFirstPlayerNickname(String firstPlayerNickname) {
        this.firstPlayerNickname = firstPlayerNickname;
    }
}
