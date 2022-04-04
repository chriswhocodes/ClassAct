package com.chrisnewland.classact.model.attribute.annotations;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class ElementValueConstant implements ElementValue
{
	private int constValueIndex;

	public ElementValueConstant(int constValueIndex)
	{
		this.constValueIndex = constValueIndex;
	}

	public int getConstValueIndex()
	{
		return constValueIndex;
	}

	public String toString(ConstantPool constantPool)
	{
		return constantPool.toString(constValueIndex);
	}
}