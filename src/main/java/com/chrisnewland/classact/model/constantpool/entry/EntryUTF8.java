package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;

public class EntryUTF8 implements ConstantPoolEntry {
    private String value;

    public EntryUTF8(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString(ConstantPool constantPool) {
        return value;
    }
}
