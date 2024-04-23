package ingsw.codex_naturalis.distributed;

import ingsw.codex_naturalis.distributed.socket.ServerStub;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class AppClient {

    private enum NetworkProtocol {

        RMI {
            public void runClient() throws RemoteException {
                Registry registry = LocateRegistry.getRegistry(1235);
                Server server;
                try {
                    server = (Server) registry.lookup("Server");
                } catch (NotBoundException e) {
                    throw new RemoteException();
                }

                ClientImpl client = new ClientImpl(server);
                client.run();
            }
        },

        SOCKET {
            public void runClient() throws RemoteException {
                ServerStub serverStub = new ServerStub("localhost", 1234);
                ClientImpl client = new ClientImpl(serverStub);

                new Thread(() -> {
                    while(true) {
                        try {
                            serverStub.receive();
                        } catch (IOException e) {
                            System.err.println("Error: won't receive from server");
                            try {
                                serverStub.close();
                            } catch (RemoteException ex) {
                                System.err.println("Error while closing connection with server");
                            }
                            System.exit(1);
                        }
                    }
                }).start();

                client.run();
            }
        };

        public abstract void runClient() throws RemoteException;

    }


    public static void main(String[] args) {

        NetworkProtocol networkProtocol = askNetworkProtocol();

        try {
            networkProtocol.runClient();
        } catch (RemoteException e) {
            System.err.println("Error while creating client");
        }

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
