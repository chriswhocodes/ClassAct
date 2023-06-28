package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.IntegerIntegerMap;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

import java.util.Map;

public class LineNumberTable extends IntegerIntegerMap implements Attribute
{
    @Override
    public AttributeType getType()
    {
        return AttributeType.LineNumberTable;
    }

    @Override
    public String toString(ConstantPool constantPool)
    {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<Integer, Integer> entry : entrySet())
        {
            builder.append("bci: ")
                    .append(entry.getKey())
                    .append(" line: ")
                    .append(entry.getValue())
                    .append("\n");
        }

        return builder.toString();
    }
}