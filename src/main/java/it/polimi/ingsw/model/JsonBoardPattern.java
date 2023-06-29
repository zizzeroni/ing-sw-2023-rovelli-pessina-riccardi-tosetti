package it.polimi.ingsw.model;

/**
 * The json record used to represent the pattern of the {@code Board},
 * depending on the number of {@code Player}s.
 *
 * @param numberOfPlayers the number of participants in the active {@code Game}.
 * @param pattern         the identified {@code Board}'s pattern, based on the number of active players entered when the game started.
 * @see Board
 * @see Game
 * @see Player
 */
public record JsonBoardPattern(int numberOfPlayers, int[][] pattern) {
}
