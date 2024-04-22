package ingsw.codex_naturalis.controller.setupPhase;

import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.view.setupPhase.ObjectiveCardChoice;

public interface SetupObserver {

    void updateReady();

    void updateInitialCard(InitialCardEvent initialCardEvent);

    void updateColor(Color color);

    void updateObjectiveCardChoice(ObjectiveCardChoice objectiveCardChoice);
}
