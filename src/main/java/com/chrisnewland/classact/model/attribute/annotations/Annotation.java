package com.chrisnewland.classact.model.attribute.annotations;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class Annotation implements ElementValue
{
	private int typeIndex;

	private ElementValuePair[] elementValuePairs;

	public Annotation(int typeIndex, int elementValueCount)
	{
		this.typeIndex = typeIndex;
		elementValuePairs = new ElementValuePair[elementValueCount];
	}

	public void setElementValuePair(int index, ElementValuePair elementValuePair)
	{
		elementValuePairs[index] = elementValuePair;
	}

	public int getTypeIndex()
	{
		return typeIndex;
	}

	public ElementValuePair[] getElementValuePairs()
	{
		return elementValuePairs;
	}

	public String toString(ConstantPool constantPool)
	{
		StringBuilder builder = new StringBuilder();

		builder.append("typeIndex:")
			   .append(typeIndex)
			   .append(' ')
			   .append(constantPool.toString(typeIndex))
			   .append("\n");

		for (ElementValuePair elementValuePair : elementValuePairs)
		{
			builder.append(elementValuePair.toString(constantPool))
				   .append("\n");
		}

		return builder.toString();
	}
}