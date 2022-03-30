package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;
import com.chrisnewland.classact.model.constantpool.ConstantPoolType;

public class EntryUTF8 implements ConstantPoolEntry {
    private String value;

    public EntryUTF8(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public ConstantPoolType getType() {
        return ConstantPoolType.CONSTANT_Utf8;
    }

    @Override
    public String toString(ConstantPool constantPool) {
        return value;
    }
}
