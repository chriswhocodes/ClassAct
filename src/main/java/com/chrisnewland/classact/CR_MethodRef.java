package com.chrisnewland.classact;

public class CR_MethodRef {
    private final int classIndex;
    private final int nameAndTypeIndex;

    public CR_MethodRef(int classIndex, int nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }
}