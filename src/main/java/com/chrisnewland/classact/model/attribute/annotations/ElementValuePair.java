package com.chrisnewland.classact.model.attribute.annotations;

import com.sun.org.apache.bcel.internal.classfile.ElementValue;

public class ElementValuePair {
    private int elementNameIndex;
    private ElementValue value;

    public ElementValuePair(int elementNameIndex, ElementValue value) {
        this.elementNameIndex = elementNameIndex;
        this.value = value;
    }

    public int getElementNameIndex() {
        return elementNameIndex;
    }

    public ElementValue getValue() {
        return value;
    }
}
