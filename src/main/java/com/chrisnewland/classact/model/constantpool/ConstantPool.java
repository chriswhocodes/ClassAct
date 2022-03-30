package com.chrisnewland.classact.model.constantpool;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.attribute.Attribute;

public class ConstantPool {
    private ConstantPoolEntry[] entries;

    public ConstantPool(int size) {
        entries = new ConstantPoolEntry[size];
    }

    public void set(int index, ConstantPoolEntry entry) {
        entries[index] = entry;

//		System.out.println("pool[" + index + "] = " + entry.toString(this));
    }

    public ConstantPoolEntry get(int index) {
        return entries[index];
    }

    public int size() {
        return entries.length;
    }

    public String toString(int index) {
        ConstantPoolEntry entry = entries[index];

        if (entry == null) {
            return "ERROR null entry at " + index;
        }

        return entry.toString(this);
    }

    public String getClass(int index) {
        ConstantPoolEntry entry = entries[index];

        return entry.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 1; i < entries.length; i++) {
            ConstantPoolEntry entry = entries[i];

            ConstantPoolType type = entry.getType();

            builder.append('#')
                    .append(i)
                    .append(" = ")
                    .append(type)
                    .append(" ")
                    .append(entry.toString(this)).append("\n");

            if (type == ConstantPoolType.CONSTANT_Double || type == ConstantPoolType.CONSTANT_Long)
            {
                i++;
            }
        }

        return builder.toString();
    }
}