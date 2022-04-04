package com.chrisnewland.classact.model.attribute.annotations;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class ElementValueClass implements ElementValue
{
	private int classInfoIndex;

	public ElementValueClass(int classInfoIndex)
	{
		this.classInfoIndex = classInfoIndex;
	}

	public int getClassInfoIndex()
	{
		return classInfoIndex;
	}

	public String toString(ConstantPool constantPool)
	{
		return constantPool.toString(classInfoIndex);
	}
}
