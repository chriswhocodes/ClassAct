package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class Deprecated implements Attribute {
    @Override
    public AttributeType getType() {
        return AttributeType.Deprecated;
    }

    public String toString(ConstantPool constantPool) {
        return getClass().getSimpleName();
    }
}
