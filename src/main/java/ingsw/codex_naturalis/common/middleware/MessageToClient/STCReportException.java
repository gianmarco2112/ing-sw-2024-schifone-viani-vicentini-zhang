package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;
/**
 * Message from server to client: an exception has occurred
 */
public class STCReportException implements MessageToClient {

    private String error;
    /**
     * STCReportException's constructor
     */
    public STCReportException() {
    }
    /**
     * STCReportException's constructor
     * @param error : the exception to report
     */
    public STCReportException(String error) {
        this.error = error;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().reportException(error);
    }
    /**
     * Error's getter
     * @return error
     */
    public String getError() {
        return error;
    }
    /**
     * Errore's setter
     * @param error to set
     */
    public void setError(String error) {
        this.error = error;
    }
}
