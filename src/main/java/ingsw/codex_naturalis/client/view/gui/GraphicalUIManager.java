package ingsw.codex_naturalis.client.view.gui;

import javafx.application.Application;

public class GraphicalUIManager {
    private static boolean isLaunched = false;

    public static synchronized void launchApp() {
        if (!isLaunched) {
            Thread javafxThread = new Thread(() -> Application.launch(GraphicalUI.class));
            javafxThread.setDaemon(true);
            javafxThread.start();
            //Application.launch(GraphicalUI.class);
            isLaunched = true;

            // Aspetta che l'istanza di GraphicalUI sia inizializzata
            while (GraphicalUI.getInstance() == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
