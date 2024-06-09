package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCReportException implements MessageToClient {

    private String error;

    public STCReportException() {
    }

    public STCReportException(String error) {
        this.error = error;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().reportException(error);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
