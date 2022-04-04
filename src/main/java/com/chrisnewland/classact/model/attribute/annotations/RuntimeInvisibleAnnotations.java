package com.chrisnewland.classact.model.attribute.annotations;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class RuntimeInvisibleAnnotations extends RuntimeAnnotations {

    private Annotation[] entries;

    public RuntimeInvisibleAnnotations(int count) {
        entries = new Annotation[count];
    }

    public void set(int index, Annotation entry) {
        entries[index] = entry;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.RuntimeInvisibleAnnotations;
    }

    @Override
    public String toString(ConstantPool constantPool) {
        return null;
    }

    public Annotation[] getEntries() {
        return entries;
    }
}