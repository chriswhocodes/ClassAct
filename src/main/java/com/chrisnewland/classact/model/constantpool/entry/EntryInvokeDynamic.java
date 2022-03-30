package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;
import com.chrisnewland.classact.model.constantpool.ConstantPoolType;

public class EntryInvokeDynamic implements ConstantPoolEntry {

    private final int bootstrapMethodAttrIndex;
    private final int nameAndTypeIndex;

    public EntryInvokeDynamic(int bootstrapMethodAttrIndex, int nameAndTypeIndex) {
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getBootstrapMethodAttrIndex() {
        return bootstrapMethodAttrIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    @Override
    public ConstantPoolType getType() {
        return ConstantPoolType.CONSTANT_InvokeDynamic;
    }

    @Override
    public String toString(ConstantPool constantPool) {
        EntryNameAndType entryNameAndType = (EntryNameAndType) constantPool.get(nameAndTypeIndex);

        StringBuilder builder = new StringBuilder();

        builder.append(bootstrapMethodAttrIndex)
                .append(' ')
                .append(entryNameAndType.toString(constantPool));

        return builder.toString();
    }
}