package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

import java.io.BufferedReader;
import java.io.IOException;

public class STCReportException implements MessageToClient {

    @Override
    public void run(ServerStub serverStub) {
        try {
            BufferedReader reader = serverStub.getReader();
            String error = reader.readLine();
            serverStub.getClient().reportException(error);
        } catch (IOException e) {
            System.err.println("Error while receiving from server\n"+e.getMessage());
        }
    }
}
