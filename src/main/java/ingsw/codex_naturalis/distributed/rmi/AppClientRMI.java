package ingsw.codex_naturalis.distributed.rmi;

import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppClientRMI {

    public static void main(String[] args) throws RemoteException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry(1234);
        Server server = (Server) registry.lookup("Server");

        ClientImpl client = new ClientImpl(server);
        client.run();


    }

}
