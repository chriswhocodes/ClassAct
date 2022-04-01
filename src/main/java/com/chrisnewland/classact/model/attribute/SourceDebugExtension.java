package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class SourceDebugExtension implements Attribute {
    private byte[] debugExtension;

    public SourceDebugExtension(int length) {
        debugExtension = new byte[length];
    }

    public byte[] getDebugExtension() {
        return debugExtension;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.SourceDebugExtension;
    }

    @Override
    public String toString(ConstantPool constantPool) {
        return new String(debugExtension);
    }
}