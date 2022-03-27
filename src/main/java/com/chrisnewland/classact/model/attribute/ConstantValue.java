package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class ConstantValue implements Attribute
{
	@Override
	public AttributeType getType()
	{
		return AttributeType.ConstantValue;
	}

	private int index;

	public ConstantValue(int index)
	{
		this.index = index;
	}

	public int getIndex()
	{
		return index;
	}

	@Override
	public String toString(ConstantPool constantPool)
	{
		return constantPool.toString(index);
	}
}
