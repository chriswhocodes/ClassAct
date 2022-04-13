package com.chrisnewland.classact.model.attribute.module;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.modifier.Modifier;
import com.chrisnewland.classact.model.modifier.RequiresFlags;

public class Requires {
    private int requiresIndex;
    private int requiresFlags;
    private int requiresVersionIndex;

    public Requires(int requiresIndex, int requiresFlags, int requiresVersionIndex) {
        this.requiresIndex = requiresIndex;
        this.requiresFlags = requiresFlags;
        this.requiresVersionIndex = requiresVersionIndex;
    }

    public int getRequiresIndex() {
        return requiresIndex;
    }

    public int getRequiresFlags() {
        return requiresFlags;
    }

    public int getRequiresVersionIndex() {
        return requiresVersionIndex;
    }

    public String toString(ConstantPool constantPool) {
        StringBuilder builder = new StringBuilder();

        builder.append(constantPool.toString(requiresIndex)).append(' ')
                .append(Modifier.getAccessString(RequiresFlags.values(), requiresFlags)).append(' ')
                .append(constantPool.toString(requiresVersionIndex));

        return builder.toString();
    }
}