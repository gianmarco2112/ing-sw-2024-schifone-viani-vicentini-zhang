package ingsw.codex_naturalis.view.playing.events;

import ingsw.codex_naturalis.view.playing.events.commands.PlayCardCommand;

public record PlayCardEvent(PlayCardCommand playCardCommand, int x, int y) {

}
