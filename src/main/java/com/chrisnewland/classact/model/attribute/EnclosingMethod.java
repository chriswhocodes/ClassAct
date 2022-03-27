package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class EnclosingMethod implements Attribute
{
	@Override
	public AttributeType getType()
	{
		return AttributeType.SourceFile;
	}

	private int classIndex;
	private int methodIndex;

	public EnclosingMethod(int classIndex, int methodIndex)
	{
		this.classIndex = classIndex;
		this.methodIndex = methodIndex;
	}

	public int getClassIndex()
	{
		return classIndex;
	}

	public int getMethodIndex()
	{
		return methodIndex;
	}

	@Override
	public String toString(ConstantPool constantPool)
	{
		return constantPool.toString(classIndex) + " " + constantPool.toString(methodIndex);
	}
}
