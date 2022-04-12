package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPoolType;

public class EntryInterfaceMethodRef extends AbstractMemberRef {
    public EntryInterfaceMethodRef(int classIndex, int nameAndTypeIndex) {
        super(classIndex, nameAndTypeIndex);
    }

    @Override
    public ConstantPoolType getType() {
        return ConstantPoolType.CONSTANT_InterfaceMethodRef;
    }
}