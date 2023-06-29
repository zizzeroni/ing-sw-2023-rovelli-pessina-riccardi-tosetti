package it.polimi.ingsw.utils;

import java.util.Scanner;

/**
 * Utility class used for managing the {@code Player} input
 * for CLI/GUI implementation.
 * It is implemented as an extension of the Thread's class.
 *
 * @see it.polimi.ingsw.model.Player
 * @see it.polimi.ingsw.view.TextualUI
 * @see it.polimi.ingsw.view.GUI
 */
public class CommandReader extends Thread {
    private final Scanner scanner = new Scanner(System.in);
    public final static CommandQueue chatCommandQueue;
    public final static CommandQueue standardCommandQueue;

    static {
        chatCommandQueue = new CommandQueue();
        standardCommandQueue = new CommandQueue();
    }

    /**
     * Identifies the command written by the {@code Player} in the CLI.
     * The available commands are /all /private and /showChat.
     * If the given input doesn't match the referred command names, prints a message error.
     *
     * @see CommandQueue#addCommand(String)
     * @see it.polimi.ingsw.model.Player
     */
    public CommandReader() {
        super();
    }

    /**
     * Allows to identify the type of chat's command forwarded by the player.
     *
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public void run() {
        while (true) {
            try {
                String command = scanner.nextLine();

                switch (command.split(" ")[0]) {
                    case "/all", "/private", "/showChat" -> CommandReader.chatCommandQueue.addCommand(command);
                    default -> CommandReader.standardCommandQueue.addCommand(command);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
    }
}
