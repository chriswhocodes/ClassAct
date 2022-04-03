package com.chrisnewland.classact.model.attribute.annotations;

public class ElementValueEnum implements ElementValue {
    private int typeNameIndex;
    private int constNameIndex;

    public ElementValueEnum(int typeNameIndex, int constNameIndex) {
        this.typeNameIndex = typeNameIndex;
        this.constNameIndex = constNameIndex;
    }

    public int getTypeNameIndex() {
        return typeNameIndex;
    }

    public int getConstNameIndex() {
        return constNameIndex;
    }
}
