package ingsw.codex_naturalis.client;

import ingsw.codex_naturalis.client.view.UI;
import ingsw.codex_naturalis.common.NetworkProtocol;
import ingsw.codex_naturalis.common.Server;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientCreation {
    /**
     *To create a new client
     */
    public static ClientImpl createClient(UI ui, String networkProtocol, String ipAddress) throws RemoteException {
        switch (networkProtocol) {
            case "RMI" -> {
                return createRMIClient(ui, ipAddress);
            }
            case "socket" -> {
                return createSocketClient(ui, ipAddress);
            }
        }
        return null;
    }
    /**
     *To create a new RMI client
     */
    private static ClientImpl createRMIClient(UI ui, String ipAddress) throws RemoteException {
        Registry registry = LocateRegistry.getRegistry(ipAddress, 1235);
        Server server;
        try {
            server = (Server) registry.lookup("Server");
        } catch (NotBoundException e) {
            throw new RemoteException(e.getMessage());
        }

        return new ClientImpl(server, NetworkProtocol.RMI, ui);
    }
    /**
     *To create a new socket client
     */
    private static ClientImpl createSocketClient(UI ui, String ipAddress) throws RemoteException {
        ServerStub serverStub = new ServerStub(ipAddress, 1234);
        ClientImpl client = new ClientImpl(serverStub, NetworkProtocol.SOCKET, ui);
        new Thread(() -> {
            while (true) {
                try {
                    serverStub.receive();
                } catch (IOException e) {
                    System.err.println("Error: won't receive from server\n" + e.getMessage());
                    try {
                        serverStub.close();
                    } catch (RemoteException ex) {
                        System.err.println("Error while closing connection with server\n" + ex.getMessage());
                    }
                    System.exit(1);
                }
            }
        }).start();
        return client;
    }

}
