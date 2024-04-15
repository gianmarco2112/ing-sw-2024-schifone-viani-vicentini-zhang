package ingsw.codex_naturalis.distributed.rmi;

import ingsw.codex_naturalis.distributed.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AppServerRMI {

    public static void main(String[] args) throws RemoteException {

        Server engine = new ServerImpl();
        Server stub = (Server) UnicastRemoteObject.exportObject(engine, 0);
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind("Server", stub);
        System.out.println("Server started");

    }

}
