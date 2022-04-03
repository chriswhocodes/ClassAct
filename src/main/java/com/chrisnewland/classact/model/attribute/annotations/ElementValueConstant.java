package com.chrisnewland.classact.model.attribute.annotations;

public class ElementValueConstant implements ElementValue {
    private int constValueIndex;

    public ElementValueConstant(int constNameIndex) {
        this.constValueIndex = constValueIndex;
    }

    public int getConstValueIndex() {
        return constValueIndex;
    }
}