package it.polimi.ingsw.network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.listeners.BoardListener;
import it.polimi.ingsw.model.listeners.BookshelfListener;
import it.polimi.ingsw.model.listeners.GameListener;
import it.polimi.ingsw.model.listeners.ModelListener;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.model.view.ModelViewListener;
import it.polimi.ingsw.view.TextualUI;
import it.polimi.ingsw.view.UI;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerImpl implements Server, ModelListener {
    private GameController controller;
    private Game model;
    private Set<Client> clientsToHandle;

    public ServerImpl() {
        //this.model=new Game();
        clientsToHandle=new HashSet<>();
        int numPlayers = 4;
        ArrayList<Player> players = new ArrayList<Player>(numPlayers);
        ArrayList<PersonalGoal> personalGoals = new ArrayList<PersonalGoal>();
        List<JsonBoardPattern> boardPatterns = new ArrayList<>();
        Gson gson = new Gson();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/java/it/polimi/ingsw/storage/personal-goals.json"));
            personalGoals = gson.fromJson(reader, new TypeToken<ArrayList<PersonalGoal>>() {
            }.getType());

            reader = Files.newBufferedReader(Paths.get("src/main/java/it/polimi/ingsw/storage/boards.json"));
            boardPatterns = gson.fromJson(reader, new TypeToken<ArrayList<JsonBoardPattern>>() {
            }.getType());
            reader.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        players.add(new Player("Alessandro", true, personalGoals.get(0), new ArrayList<ScoreTile>(), new Bookshelf()));
        players.add(new Player("Andrea", true, personalGoals.get(1), new ArrayList<ScoreTile>(), new Bookshelf()));
        players.add(new Player("Francesco", true, personalGoals.get(2), new ArrayList<ScoreTile>(), new Bookshelf()));
        players.add(new Player("Luca", true, personalGoals.get(3), new ArrayList<ScoreTile>(), new Bookshelf()));

        model = new Game(numPlayers, players, personalGoals, boardPatterns.stream().filter(boardPattern -> boardPattern.numberOfPlayers() == players.size()).findFirst().orElse(null));
    }
    @Override
    public void changeTurn() throws RemoteException {
        this.controller.changeTurn();
    }

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) throws RemoteException {
        this.controller.insertUserInputIntoModel(playerChoice);
    }

    @Override
    public void sendPrivateMessage(Player receiver, Player sender, String content) throws RemoteException {
        this.controller.sendPrivateMessage(receiver,sender,content);
    }

    @Override
    public void sendBroadcastMessage(Player sender, String content) throws RemoteException {
        this.controller.sendBroadcastMessage(sender,content);
    }

    @Override
    public GameView register(Client client) {
        clientsToHandle.add(client);
        this.model.registerListener(this);
        this.model.getBoard().registerListener(this);
        for(Player player : this.model.getPlayers()) {
            player.getBookshelf().registerListener(this);
        }
        this.controller = new GameController(model);
        return new GameView(model);
    }

    //Listeners methods
    @Override
    public void addedTilesToBoard(Board board) {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void removedTilesFromBoard(Board board) {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void tileAddedToBookshelf(Bookshelf bookshelf) {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void imageModified(String image) {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void numberOfPlayersModified() {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void activePlayerIndexModified() {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void bagModified() {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void commonGoalsModified() {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
