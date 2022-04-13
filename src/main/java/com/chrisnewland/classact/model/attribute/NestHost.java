package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class NestHost implements Attribute {

    private int hostClassIndex;

    public NestHost(int hostClassIndex) {
        this.hostClassIndex = hostClassIndex;
    }

    public int getHostClassIndex() {
        return hostClassIndex;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.NestHost;
    }

    @Override
    public String toString(ConstantPool constantPool) {
        return constantPool.toString(hostClassIndex);
    }
}