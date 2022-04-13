package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class ModuleMainClass implements Attribute {

    private int mainClassIndex;

    public ModuleMainClass(int mainClassIndex) {
        this.mainClassIndex = mainClassIndex;
    }

    public int getMainClassIndex() {
        return mainClassIndex;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.ModuleMainClass;
    }

    @Override
    public String toString(ConstantPool constantPool) {
        return constantPool.toString(mainClassIndex);
    }
}
