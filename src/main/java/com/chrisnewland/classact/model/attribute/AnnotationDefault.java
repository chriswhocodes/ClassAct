package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.attribute.annotations.ElementValue;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class AnnotationDefault implements Attribute {
    private ElementValue elementValue;

    public AnnotationDefault(ElementValue elementValue) {
        this.elementValue = elementValue;
    }

    public ElementValue getElementValue() {
        return elementValue;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.AnnotationDefault;
    }

    @Override
    public String toString(ConstantPool constantPool) {
        return elementValue.toString(constantPool);
    }
}