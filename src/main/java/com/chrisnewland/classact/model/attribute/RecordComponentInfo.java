package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class RecordComponentInfo {

    private int nameIndex;
    private int descriptorIndex;

    private Attributes attributes;

    public RecordComponentInfo(int nameIndex, int descriptorIndex, Attributes attributes) {
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributes = attributes;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public String toString(ConstantPool constantPool) {
        StringBuilder builder = new StringBuilder();

        builder.append(constantPool.toString(nameIndex)).append(' ').append(constantPool.toString(descriptorIndex)).append("\n");

        builder.append(attributes.toString(constantPool));

        return builder.toString();
    }
}