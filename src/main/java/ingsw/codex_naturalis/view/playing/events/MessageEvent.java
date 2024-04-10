package ingsw.codex_naturalis.view.playing.events;

import java.util.List;

public record MessageEvent(String content, String sender, List<String> playersToText) {

}
