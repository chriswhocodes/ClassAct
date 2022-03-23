package com.chrisnewland.classact.model.constantpool;

public interface ConstantPoolEntry {

    default String getType() {
        return getClass().getSimpleName().substring("Entry".length());
    }

    String toString(ConstantPool constantPool);
}
