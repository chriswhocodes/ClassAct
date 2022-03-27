package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class Synthetic implements Attribute
{
	@Override
	public AttributeType getType()
	{
		return AttributeType.Synthetic;
	}

	@Override
	public String toString(ConstantPool constantPool)
	{
		return "";
	}
}
