package it.polimi.ingsw.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandQueue {

    private BlockingQueue<String> commands;

    public CommandQueue() {
        commands = new LinkedBlockingQueue<>();
    }

    public String waitAndGetFirstCommandAvailable() {
        String command = "";
        try {
            command = this.commands.take();
        } catch (InterruptedException ignored) {
        }
        return command;
    }

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

    public void addCommand(String command) throws Exception {
        if (!this.commands.add(command)) {
            throw new Exception("Error while adding a command to the queue, size of the queue: " + this.commands.size());
        }
    }

    public BlockingQueue<String> getCommands() {
        return this.commands;
    }

    public void setCommands(BlockingQueue<String> commands) {
        this.commands = commands;
    }
}
