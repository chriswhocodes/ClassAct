package com.chrisnewland.classact.model.attribute.module;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.modifier.ExportsFlags;
import com.chrisnewland.classact.model.modifier.Modifier;
import com.chrisnewland.classact.model.modifier.RequiresFlags;

public class Exports {

    private int exportsIndex;
    private int exportsFlags;
    private int[] exportsToIndex;

    public Exports(int exportsIndex, int exportsFlags, int[] exportsToIndex) {
        this.exportsIndex = exportsIndex;
        this.exportsFlags = exportsFlags;
        this.exportsToIndex = exportsToIndex;
    }

    public int getExportsIndex() {
        return exportsIndex;
    }

    public int getExportsFlags() {
        return exportsFlags;
    }

    public int[] getExportsToIndex() {
        return exportsToIndex;
    }

    public String toString(ConstantPool constantPool) {
        StringBuilder builder = new StringBuilder();

        builder.append(constantPool.toString(exportsIndex)).append(' ')
                .append(Modifier.getAccessString(ExportsFlags.values(), exportsFlags)).append(" exports to ");

        for (int e : exportsToIndex) {
            builder.append(constantPool.toString(e)).append(',');
        }

        if (exportsToIndex.length > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }
}
