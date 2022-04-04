package com.chrisnewland.classact.model.attribute.annotations;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class ElementValuePair
{
	private int elementNameIndex;
	private ElementValue value;

	public ElementValuePair(int elementNameIndex, ElementValue value)
	{
		this.elementNameIndex = elementNameIndex;
		this.value = value;
	}

	public int getElementNameIndex()
	{
		return elementNameIndex;
	}

	public ElementValue getValue()
	{
		return value;
	}

	public String toString(ConstantPool constantPool)
	{
		StringBuilder builder = new StringBuilder();

		builder.append("elementNameIndex:")
			   .append(elementNameIndex)
			   .append("\n");

		builder.append(value.toString(constantPool))
			   .append("\n");

		return builder.toString();
	}
}
