package ingsw.codex_naturalis.controller.setupPhase;

import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.view.setupPhase.ObjectiveCardChoice;

public interface SetupObserver {

    void ctsUpdateReady();

    void ctsUpdateInitialCard(InitialCardEvent initialCardEvent);

    void ctsUpdateColor(Color color);

    void ctsUpdateObjectiveCardChoice(ObjectiveCardChoice objectiveCardChoice);
}
