package com.chrisnewland.classact;

import java.util.LinkedHashMap;
import java.util.Map;

public class IntegerIntegerMap extends LinkedHashMap<Integer, Integer> {

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<Integer, Integer> entry : entrySet()) {
            builder.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        }

        return builder.toString();
    }
}
