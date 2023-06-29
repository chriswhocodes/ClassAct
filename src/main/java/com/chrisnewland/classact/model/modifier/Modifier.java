package com.chrisnewland.classact.model.modifier;

public interface Modifier {

    int getFlag();

    static String getAccessString(Modifier[] modifiers, int flags) {
        StringBuilder builder = new StringBuilder();

        for (Modifier modifier : modifiers) {
            if ((modifier.getFlag() & flags) > 0) {
                builder.append(modifier).append(", ");
            }
        }

        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 2);
        }

        return builder.toString();
    }
}