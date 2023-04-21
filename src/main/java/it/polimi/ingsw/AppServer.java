package it.polimi.ingsw;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.ServerImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppServer {
    public static void main(String[] args) throws RemoteException {
        Server server = new ServerImpl();

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("server", server);
    }

}
