package ingsw.codex_naturalis.distributed;

import ingsw.codex_naturalis.distributed.socket.ServerStub;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class AppClient {

    private enum NetworkProtocol {

        RMI("Remote Method Interface (RMI)"),
        SOCKET("Socket TCP/IP");

        private final String description;

        NetworkProtocol(String description){
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

    }


    public static void main(String[] args) {

        NetworkProtocol networkProtocol = askNetworkProtocol();

        try {
            switch (networkProtocol) {
                case RMI -> rmiClient();
                case SOCKET -> socketClient();
            }
        } catch (RemoteException e) {
            System.err.println("Error while creating client");
        }
    }


    private static void socketClient() throws RemoteException {

        ServerStub serverStub = new ServerStub("localhost", 1234);
        ClientImpl client = new ClientImpl(serverStub);

        new Thread(() -> {
            while(true) {
                try {
                    serverStub.receive(client);
                } catch (RemoteException e) {
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

    private static void rmiClient() throws RemoteException {

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

    private static NetworkProtocol askNetworkProtocol() {

        Scanner s = new Scanner(System.in);

        System.out.println("Before we begin, please choose your preferred network protocol");


        Map<Integer, NetworkProtocol> networkProtocolMap = new LinkedHashMap<>();
        for (int key = 0; key < NetworkProtocol.values().length; key++)
            networkProtocolMap.put(key+1, NetworkProtocol.values()[key]);

        for (Map.Entry<Integer, NetworkProtocol> entry : networkProtocolMap.entrySet())
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());

        System.out.println("""

                To navigate the menu, simply enter the number corresponding to the option you'd like to select and press Enter. Here's how it works:

                To use RMI, type '1' and press Enter.
                To use Sockets, type '2' and press Enter.
                
                """);

        while (true) {
            String input = s.next();
            try{
                int option = Integer.parseInt(input);
                if (networkProtocolMap.containsKey(option))
                    return networkProtocolMap.get(option);
                else
                    System.err.println("Invalid option");
            } catch (NumberFormatException e) {
                System.err.println("Invalid option");
            }
        }
    }

}
