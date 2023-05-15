package it.polimi.ingsw.utils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class CommandQueue {

    private Queue<String> commands;

    public CommandQueue() {
        commands = new ConcurrentLinkedQueue<>();
    }

    public void removeOldestCommand() throws Exception {
        if(this.commands.poll() == null) {
            throw new Exception("Error while removing a command from the queue, size of the queue: " + this.commands.size());
        }
    }

    public String getOldestCommand() {
        String command;
        if ((command = this.commands.peek()) != null) {
            return command;
        }
        //-1 implies that there are no available commands;
        return "-1";
    }

    public String waitAndGetFirstCommandAvailable() {
        String command;
        try {
            while ((command = this.getOldestCommand()).equals("-1")) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            this.removeOldestCommand();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return command;
    }

    public int waitAndGetFirstIntegerCommandAvailable() {
        int parsedCommand;
        try {
            while ((parsedCommand = Integer.parseInt(this.getOldestCommand())) == -1) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            this.removeOldestCommand();
        } catch (NumberFormatException e) {
            parsedCommand = -1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return parsedCommand;
    }

    public void addCommand(String command) throws Exception {
        if(!this.commands.add(command)){
            throw new Exception("Error while adding a command to the queue, size of the queue: " + this.commands.size());
        }
    }

    public Queue<String> getCommands() {
        return this.commands;
    }
    public void setCommands(Queue<String> commands) {
        this.commands = commands;
    }
}
