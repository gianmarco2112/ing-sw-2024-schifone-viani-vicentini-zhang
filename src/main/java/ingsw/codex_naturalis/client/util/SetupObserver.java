package ingsw.codex_naturalis.client.util;

import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.common.events.setupPhase.ObjectiveCardChoice;

public interface SetupObserver {

    void ctsUpdateReady();

    void ctsUpdateInitialCard(InitialCardEvent initialCardEvent);

    void ctsUpdateColor(Color color);

    void ctsUpdateObjectiveCardChoice(ObjectiveCardChoice objectiveCardChoice);
}
