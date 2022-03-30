package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;
import com.chrisnewland.classact.model.constantpool.ConstantPoolType;

public class EntryMethodType implements ConstantPoolEntry {
    private final int descriptorIndex;

    public EntryMethodType(int descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    @Override
    public ConstantPoolType getType() {
        return ConstantPoolType.CONSTANT_MethodType;
    }

    @Override
    public String toString(ConstantPool constantPool) {
        EntryUTF8 entryUTF8 = (EntryUTF8) constantPool.get(descriptorIndex);

        return entryUTF8.toString(constantPool);
    }
}