package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class Record implements Attribute {

    private RecordComponentInfo[] components;

    public Record(int count) {
        components = new RecordComponentInfo[count];
    }

    public void set(int index, RecordComponentInfo component) {
        components[index] = component;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.Record;
    }

    @Override
    public String toString(ConstantPool constantPool) {
        StringBuilder builder = new StringBuilder();

        for (RecordComponentInfo component : components) {
            builder.append(component.toString(constantPool)).append("\n");
        }

        return builder.toString();
    }
}
