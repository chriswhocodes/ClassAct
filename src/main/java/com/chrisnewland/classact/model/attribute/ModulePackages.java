package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class ModulePackages implements Attribute {
    private int[] packageIndex;

    public ModulePackages(int[] packageIndex) {
        this.packageIndex = packageIndex;
    }

    public int[] getPackageIndex() {
        return packageIndex;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.ModulePackages;
    }

    @Override
    public String toString(ConstantPool constantPool) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < packageIndex.length; i++) {
            builder.append(constantPool.toString(packageIndex[i])).append(',');
        }

        if (packageIndex.length > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }
}