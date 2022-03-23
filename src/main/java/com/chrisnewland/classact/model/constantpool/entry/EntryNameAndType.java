package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;

public class EntryNameAndType implements ConstantPoolEntry {
    private int nameIndex;
    private int descriptorIndex;

    public EntryNameAndType(int nameIndex, int descriptorIndex) {
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    @Override
    public String toString(ConstantPool constantPool) {

        StringBuilder builder = new StringBuilder();

        builder.append(constantPool.get(nameIndex).toString(constantPool)).append(' ').append(constantPool.get(descriptorIndex).toString(constantPool));

        return builder.toString();

    }
}
