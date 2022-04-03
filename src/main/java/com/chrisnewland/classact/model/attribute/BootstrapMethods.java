package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class BootstrapMethods implements Attribute {

    private BootstrapMethodsEntry[] entries;

    public BootstrapMethods(int count) {
        entries = new BootstrapMethodsEntry[count];
    }

    public void set(int index, BootstrapMethodsEntry entry) {
        entries[index] = entry;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.BootstrapMethods;
    }

    @Override
    public String toString(ConstantPool constantPool) {
        StringBuilder builder = new StringBuilder();

        for (BootstrapMethodsEntry entry : entries) {
            builder.append(entry.toString(constantPool)).append("\n");
        }

        return builder.toString();
    }
}
