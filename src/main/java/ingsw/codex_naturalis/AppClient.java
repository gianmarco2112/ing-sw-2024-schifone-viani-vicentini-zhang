package ingsw.codex_naturalis;

import ingsw.codex_naturalis.client.ClientImpl;
import ingsw.codex_naturalis.client.UIChoice;
import ingsw.codex_naturalis.client.view.UI;
import ingsw.codex_naturalis.client.view.gui.GraphicalUI;
import ingsw.codex_naturalis.client.view.tui.TextualUI;
import ingsw.codex_naturalis.common.NetworkProtocol;
import ingsw.codex_naturalis.common.Server;
import ingsw.codex_naturalis.client.ServerStub;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class AppClient {


    public static void main(String[] args) {

        UIChoice uiChoice = askUIChoice();
        switch (uiChoice) {
            case TUI -> {
                new TextualUI().run(args);
            }
            case GUI -> {
                GraphicalUI.main(args);
            }
        }

    }

    /**
     * Asks for the UI choice
     * @return UI choice
     */
    private static UIChoice askUIChoice() {

        Scanner s = new Scanner(System.in);

        System.out.print("\n\nWelcome to ");

        //red, green, blue, purple
        String[] colors = {"\u001B[31m", "\u001B[32m", "\u001B[34m", "\u001B[35m"};
        String text = "Codex Naturalis!";

        //print fancy codex naturalis text
        for (int i = 0; i < text.length(); i++) {
            int colorIndex = i % colors.length;
            String color = colors[colorIndex];
            System.out.print(color + text.charAt(i));
        }

        //color reset
        System.out.println("\u001B[0m");

        System.out.println("""
                                
                --------------------------------------------------------
                Please choose your preferred user interface (UI) option:
                                
                (1) Textual User interface - TUI
                (2) Graphical User Interface - GUI
                --------------------------------------------------------
                                
                                
                """);

        Map<Integer, UIChoice> uiChoices = new LinkedHashMap<>();

        String input;
        while (true) {
            input = s.next();
            try {
                int option = Integer.parseInt(input);
                switch (option) {
                    case 1 -> {
                        return UIChoice.TUI;
                    }
                    case 2 -> {
                        return UIChoice.GUI;
                    }
                    default -> System.err.println("Invalid option");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid option");
            }
        }

    }

}
