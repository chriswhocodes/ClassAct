package com.chrisnewland.classact.model.attribute;

public class MethodParametersEntry {
    private int nameIndex;
    private int accessFlags;

    public MethodParametersEntry(int nameIndex, int accessFlags) {
        this.nameIndex = nameIndex;
        this.accessFlags = accessFlags;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getAccessFlags() {
        return accessFlags;
    }
}
