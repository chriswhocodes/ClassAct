package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPoolType;

public class EntryMethodRef extends AbstractMemberRef {
    public EntryMethodRef(int classIndex, int nameAndTypeIndex) {
        super(classIndex, nameAndTypeIndex);
    }

    @Override
    public ConstantPoolType getType() {
        return ConstantPoolType.CONSTANT_MethodRef;
    }
}