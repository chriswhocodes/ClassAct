package com.chrisnewland.classact.model.attribute.annotations;

public class ElementValueClass implements ElementValue {
    private int classInfoIndex;

    public ElementValueClass(int classInfoIndex) {
        this.classInfoIndex = classInfoIndex;
    }

    public int getClassInfoIndex() {
        return classInfoIndex;
    }
}
