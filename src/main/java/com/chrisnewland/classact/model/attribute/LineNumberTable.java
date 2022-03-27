package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.IntegerIntegerMap;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

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
		return toString();
	}
}
