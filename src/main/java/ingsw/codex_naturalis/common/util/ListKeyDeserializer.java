package ingsw.codex_naturalis.common.util;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by Jackson to deserialize the keys of the player area HashMap
 */

public class ListKeyDeserializer extends KeyDeserializer {
    @Override
    public List<Integer> deserializeKey(String key, DeserializationContext ctxt) {
        List<Integer> list = new ArrayList<>();
        String[] elements = key.substring(1, key.length() - 1).split(", ");
        for (String element : elements) {
            list.add(Integer.parseInt(element));
        }
        return list;
    }
}