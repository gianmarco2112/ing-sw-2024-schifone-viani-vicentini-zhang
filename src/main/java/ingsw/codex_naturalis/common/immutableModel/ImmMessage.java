package ingsw.codex_naturalis.common.immutableModel;

import java.util.List;

public record ImmMessage (String content,
                          String sentTime,
                          String sender,
                          List<String>receivers) {
}
