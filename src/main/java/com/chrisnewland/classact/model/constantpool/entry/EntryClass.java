package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;
import com.chrisnewland.classact.model.constantpool.ConstantPoolType;

public class EntryClass implements ConstantPoolEntry {
    private int nameIndex;

    public EntryClass(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    @Override
    public ConstantPoolType getType() {
        return ConstantPoolType.CONSTANT_Class;
    }

    @Override
    public String toString(ConstantPool constantPool) {
        return constantPool.get(nameIndex).toString(constantPool);
    }
}
