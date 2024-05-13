package ingsw.codex_naturalis;

import ingsw.codex_naturalis.common.Server;
import ingsw.codex_naturalis.server.ServerImpl;
import ingsw.codex_naturalis.server.ClientSkeleton;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServer {

    public static void main(String[] args) throws RemoteException {

        ServerImpl server = new ServerImpl();

        new Thread( () -> startRMIProtocol(server)).start();

        new Thread( () -> startSocketsProtocol(server)).start();

    }

    private static void startSocketsProtocol(ServerImpl server) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Sockets protocol on");
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(() -> {
                    try {
                        ClientSkeleton clientSkeleton = new ClientSkeleton(socket, server);
                        server.register(clientSkeleton);
                        while (true) {
                            try {
                                clientSkeleton.receive();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (RemoteException e) {
                        System.err.println("Cannot receive from client. Closing this connection...");
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.err.println("Cannot close socket");
                        }
                    }
                });
            }
        } catch (IOException e) {
            System.err.println("Cannot start socket server");
        }

    }

    private static void startRMIProtocol(Server server) {

        Server stub = null;
        try {
            stub = (Server) UnicastRemoteObject.exportObject(server, 0);
        } catch (RemoteException e) {
            System.err.println("Error while exporting RMI server stub");
        }
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(1235);
        } catch (RemoteException e) {
            System.err.println("Error while creating the RMI registry");
        }
        try {
            registry.rebind("Server", stub);
        } catch (RemoteException e) {
            System.err.println("Error while binding the server stub");
        }
        System.out.println("RMI protocol on");

    }


}
