package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class Exceptions implements Attribute
{
	@Override
	public AttributeType getType()
	{
		return AttributeType.Exceptions;
	}

	private int[] exceptions;

	public Exceptions(int count)
	{
		exceptions = new int[count];
	}

	public void set(int index, int constantPoolIndex)
	{
		exceptions[index] = constantPoolIndex;
	}

	public int[] getExceptions()
	{
		return exceptions;
	}

	public String toString(ConstantPool constantPool)
	{
		StringBuilder builder = new StringBuilder();

		for (int index : exceptions)
		{
			builder.append(constantPool.toString(index))
				   .append("\n");
		}

		return builder.toString();
	}
}
