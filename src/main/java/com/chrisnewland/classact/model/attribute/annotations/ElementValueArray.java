package com.chrisnewland.classact.model.attribute.annotations;

public class ElementValueArray implements ElementValue {
    private ElementValue[] values;

    public ElementValueArray(int count) {
        this.values = new ElementValue[count];
    }

    public void set(int index, ElementValue elementValue) {
        values[index] = elementValue;
    }

    public ElementValue[] getValues() {
        return values;
    }
}
