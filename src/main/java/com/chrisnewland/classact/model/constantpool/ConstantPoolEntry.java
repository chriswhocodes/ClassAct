package com.chrisnewland.classact.model.constantpool;

public interface ConstantPoolEntry {

    ConstantPoolType getType();

    String toString(ConstantPool constantPool);
}