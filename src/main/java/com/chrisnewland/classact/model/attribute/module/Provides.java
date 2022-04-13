package com.chrisnewland.classact.model.attribute.module;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.modifier.ExportsFlags;
import com.chrisnewland.classact.model.modifier.Modifier;

public class Provides {

    private int providesIndex;
    private int[] providesWithIndex;

    public Provides(int providesIndex, int[] providesWithIndex) {
        this.providesIndex = providesIndex;
        this.providesWithIndex = providesWithIndex;
    }

    public int getProvidesIndex() {
        return providesIndex;
    }

    public int[] getProvidesWithIndex() {
        return providesWithIndex;
    }

    public String toString(ConstantPool constantPool) {
        StringBuilder builder = new StringBuilder();

        builder.append(constantPool.toString(providesIndex)).append(" provides ");

        for (int p : providesWithIndex) {
            builder.append(constantPool.toString(p)).append(',');
        }

        if (providesWithIndex.length > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }
}
