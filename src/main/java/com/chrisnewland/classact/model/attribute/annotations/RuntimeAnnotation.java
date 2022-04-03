package com.chrisnewland.classact.model.attribute.annotations;

public class RuntimeAnnotation implements ElementValue {
    private int typeIndex;

    private ElementValuePair[] elementValuePairs;

    public RuntimeAnnotation(int typeIndex, int elementValueCount) {
        this.typeIndex = typeIndex;
        elementValuePairs = new ElementValuePair[elementValueCount];
    }

    public void setElementValuePair(int index, ElementValuePair elementValuePair) {
        elementValuePairs[index] = elementValuePair;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public ElementValuePair[] getElementValuePairs() {
        return elementValuePairs;
    }
}