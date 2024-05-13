package ingsw.codex_naturalis.client;

import ingsw.codex_naturalis.client.view.gui.GraphicalUI;
import ingsw.codex_naturalis.client.view.tui.TextualUI;
import ingsw.codex_naturalis.client.view.UI;
import ingsw.codex_naturalis.client.view.util.UIObservableItem;


public enum UIChoice {

    TUI {

        public UI createView(UIObservableItem uiObservableItem) {
            return new TextualUI(uiObservableItem);
        }

    },



    GUI{

        public UI createView(UIObservableItem uiObservableItem) {
            return new GraphicalUI(uiObservableItem);
        }

    };




    public abstract UI createView(UIObservableItem uiObservableItem);
}
