package ingsw.codex_naturalis.common.immutableModel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ingsw.codex_naturalis.common.enumerations.ExtremeCoordinate;
import ingsw.codex_naturalis.common.enumerations.Symbol;
import ingsw.codex_naturalis.common.util.ListKeyDeserializer;

import java.util.List;
import java.util.Map;

public record ImmPlayerArea(@JsonDeserialize(keyUsing = ListKeyDeserializer.class) Map<List<Integer>, ImmPlayableCard> area,
                            String areaTUI,
                            Map<ExtremeCoordinate, Integer> extremeCoordinates,
                            Map<Symbol, Integer> numOfSymbols,
                            ImmObjectiveCard objectiveCard,
                            int points, int extraPoints) {
}
