package it.polimi.ingsw.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Utility class used for all operations related to running/setting
 * the active {@code Player} commands as represented in a default queue {@code commands}.
 */
public class CommandQueue {
    private BlockingQueue<String> commands;

    /**
     * Initialize the commands
     */
    public CommandQueue() {
        commands = new LinkedBlockingQueue<>();
    }

    /**
     * Wait since the first command available.
     *
     * @return the command identified in the queue.
     */
    public String waitAndGetFirstCommandAvailable() {
        String command = "";
        try {
            command = this.commands.take();
        } catch (InterruptedException ignored) {
        }
        return command;
    }

    /**
     * Tries to get a Command from the BlockingQueue {@code commands}.
     *
     * @return the parsed command identified in the queue.
     */
    public int waitAndGetFirstIntegerCommandAvailable() {
        int parsedCommand;
        try {
            parsedCommand = Integer.parseInt(this.commands.take());
        } catch (NumberFormatException e) {
            parsedCommand = -1;
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        return parsedCommand;
    }

    /**
     * Adds a command to BlockingQueue {@code commands}.
     *
     * @param command the {@code command} from the
     * @throws Exception if the command can't be added,
     *                   prints an error message with the size of the queue.
     */
    public void addCommand(String command) throws Exception {
        if (!this.commands.add(command)) {
            throw new Exception("Error while adding a command to the queue, size of the queue: " + this.commands.size());
        }
    }

    /**
     * get the command of the BlockingQueue.
     *
     * @return the list of command identified in the queue.
     */
    public BlockingQueue<String> getCommands() {
        return this.commands;
    }

    /**
     * set the command of the BlockingQueue.
     *
     * @param commands the list of command identified in the queue.
     */
    public void setCommands(BlockingQueue<String> commands) {
        this.commands = commands;
    }
}
