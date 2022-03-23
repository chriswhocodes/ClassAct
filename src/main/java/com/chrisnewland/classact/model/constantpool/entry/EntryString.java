package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;

public class EntryString implements ConstantPoolEntry {
    private int index;

    public EntryString(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString(ConstantPool constantPool) {
        return constantPool.toString(index);
    }
}
