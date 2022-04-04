package com.chrisnewland.classact.model.attribute.annotations;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class ElementValueEnum implements ElementValue
{
	private int typeNameIndex;
	private int constNameIndex;

	public ElementValueEnum(int typeNameIndex, int constNameIndex)
	{
		this.typeNameIndex = typeNameIndex;
		this.constNameIndex = constNameIndex;
	}

	public int getTypeNameIndex()
	{
		return typeNameIndex;
	}

	public int getConstNameIndex()
	{
		return constNameIndex;
	}

	public String toString(ConstantPool constantPool)
	{
		StringBuilder builder = new StringBuilder();

		builder.append("type:")
			   .append(constantPool.toString(typeNameIndex))
			   .append("\n");
		builder.append("constant:")
			   .append(constantPool.toString(constNameIndex))
			   .append("\n");

		return builder.toString();
	}
}
