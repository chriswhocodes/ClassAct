package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class NestMembers implements Attribute {

    private int[] classes;

    public NestMembers(int[] classes) {
        this.classes = classes;
    }

    public int[] getClasses() {
        return classes;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.NestMembers;
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