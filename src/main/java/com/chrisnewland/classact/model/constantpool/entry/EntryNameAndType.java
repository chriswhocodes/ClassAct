package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;
import com.chrisnewland.classact.model.constantpool.ConstantPoolType;

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
    public ConstantPoolType getType() {
        return ConstantPoolType.CONSTANT_NameAndType;
    }

    @Override
    public String toString(ConstantPool constantPool) {

        StringBuilder builder = new StringBuilder();

        builder.append(constantPool.get(nameIndex).toString(constantPool)).append(' ').append(constantPool.get(descriptorIndex).toString(constantPool));

        return builder.toString();

    }
}
