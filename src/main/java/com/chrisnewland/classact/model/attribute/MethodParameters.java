package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class MethodParameters implements Attribute {
    private MethodParametersEntry[] entries;

    public MethodParameters(int count) {
        entries = new MethodParametersEntry[count];
    }

    @Override
    public AttributeType getType() {
        return AttributeType.MethodParameters;
    }

    public void set(int index, MethodParametersEntry entry) {
        entries[index] = entry;
    }

    public String toString(ConstantPool constantPool) {
        StringBuilder builder = new StringBuilder();

        builder.append("Name AccessFlags")
                .append("\n");

        for (MethodParametersEntry entry : entries) {
            builder.append(String.format("%10s%10d", constantPool.toString(entry.getNameIndex()), entry.getAccessFlags()))
                    .append("\n");
        }

        return builder.toString();
    }
}
