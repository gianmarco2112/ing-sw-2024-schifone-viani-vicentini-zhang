package ingsw.codex_naturalis.controller;


import ingsw.codex_naturalis.view.playing.events.MessageEvent;
import ingsw.codex_naturalis.view.playing.events.PlayCardEvent;
import ingsw.codex_naturalis.view.playing.events.commands.Command;
import ingsw.codex_naturalis.view.playing.events.commands.DrawCardCommand;
import ingsw.codex_naturalis.view.playing.events.commands.FlipCardCommand;

public interface ObserverController {

    void update(FlipCardCommand arg);
    void update(PlayCardEvent arg);
    void update(DrawCardCommand arg);
    void update(MessageEvent arg);

}
