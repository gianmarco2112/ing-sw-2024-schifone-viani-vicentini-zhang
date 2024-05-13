package ingsw.codex_naturalis;

import ingsw.codex_naturalis.client.ClientImpl;
import ingsw.codex_naturalis.common.NetworkProtocol;
import ingsw.codex_naturalis.common.Server;
import ingsw.codex_naturalis.client.ServerStub;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AppClient {


    public static void main(String[] args) {

        NetworkProtocol networkProtocol = askNetworkProtocol();

        try {
            switch (networkProtocol) {
                case RMI -> runRMIClient();
                case SOCKET -> runSocketClient();
            }
        } catch (RemoteException e) {
            System.err.println("Error while creating client");
        }

    }

    private static void runRMIClient() throws RemoteException {

        Registry registry = LocateRegistry.getRegistry(1235);
        Server server;
        try {
            server = (Server) registry.lookup("Server");
        } catch (NotBoundException e) {
            throw new RemoteException();
        }

        ClientImpl client = new ClientImpl(server, NetworkProtocol.RMI);
        client.runView();

    }

    private static void runSocketClient() throws RemoteException {

        ServerStub serverStub = new ServerStub("localhost", 1234);
        ClientImpl client = new ClientImpl(serverStub, NetworkProtocol.SOCKET);

        new Thread(() -> {
            while (true) {
                try {
                    serverStub.receive();
                } catch (IOException e) {
                    System.err.println("Error: won't receive from server\n" + e.getMessage());
                    try {
                        serverStub.close();
                    } catch (RemoteException ex) {
                        System.err.println("Error while closing connection with server");
                    }
                    System.exit(1);
                }
            }
        }).start();

        client.runView();
    }


    private static NetworkProtocol askNetworkProtocol() {

        Scanner s = new Scanner(System.in);

        System.out.println("""
                
                
                --------------------------------------------------------------
                Before we begin, please choose your preferred network protocol:
                
                (1) Remote Method Interface - RMI
                (2) Socket - TCP/IP
                --------------------------------------------------------------
                
                
                To navigate the menu, simply enter the number corresponding to the option you'd like to select and press Enter. Here's how it works:

                To use RMI, type '1' and press Enter.
                To use Sockets, type '2' and press Enter.
                
                
                
                """);

        String input;

        while (true) {
            input = s.next();
            try{
                int option = Integer.parseInt(input);
                switch (option) {
                    case 1 -> { return NetworkProtocol.RMI; }
                    case 2 -> { return NetworkProtocol.SOCKET; }
                    default -> System.err.println("Invalid option");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid option");
            }
        }
    }

}
