package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class PermittedSubclasses implements Attribute {

    private int[] classes;

    public PermittedSubclasses(int[] classes) {
        this.classes = classes;
    }

    public int[] getClasses() {
        return classes;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.PermittedSubclasses;
    }

    @Override
    public String toString(ConstantPool constantPool) {
        StringBuilder builder = new StringBuilder();

        for (int c : classes) {
            builder.append(constantPool.toString(c)).append(',');
        }

        if (classes.length > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }
}