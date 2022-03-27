package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class SourceFile implements Attribute
{
	@Override
	public AttributeType getType()
	{
		return AttributeType.SourceFile;
	}

	private int index;

	public SourceFile(int index)
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
