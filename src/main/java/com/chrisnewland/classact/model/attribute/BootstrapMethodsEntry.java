package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class BootstrapMethodsEntry
{

    private int bootstrapMethodRef;

    private int[] bootstrapArguments;

    public BootstrapMethodsEntry(int bootstrapMethodRef, int bootstrapArgumentCount)
    {
        this.bootstrapMethodRef = bootstrapMethodRef;

        this.bootstrapArguments = new int[bootstrapArgumentCount];
    }

    public void setArgument(int index, int constantPoolIndex)
    {
        bootstrapArguments[index] = constantPoolIndex;
    }

    public int getBootstrapMethodRef()
    {
        return bootstrapMethodRef;
    }

    public int[] getBootstrapArguments()
    {
        return bootstrapArguments;
    }

    public String toString(ConstantPool constantPool)
    {
        StringBuilder builder = new StringBuilder();

        builder.append(constantPool.toString(bootstrapMethodRef)).append('(');

        if (bootstrapArguments.length > 0)
        {
            for (int argIndex : bootstrapArguments)
            {
                builder.append(constantPool.toString(argIndex)).append(',');
            }

            builder.deleteCharAt(builder.length() - 1);
        }

        builder.append(')');

        return builder.toString();
    }
}