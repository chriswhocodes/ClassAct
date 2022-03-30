package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;
import com.chrisnewland.classact.model.constantpool.ConstantPoolType;

public class EntryPackage implements ConstantPoolEntry {
    private int nameIndex;

    public EntryPackage(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    @Override
    public ConstantPoolType getType() {
        return ConstantPoolType.CONSTANT_Package;
    }

    @Override
    public String toString(ConstantPool constantPool) {

        StringBuilder builder = new StringBuilder();

        builder.append(constantPool.get(nameIndex).toString(constantPool));

        return builder.toString();
    }
}