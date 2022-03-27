package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class InnerClassEntry
{
	private int innerClassInfoIndex;
	private int outerClassInfoIndex;
	private int innerNameIndex;
	private int innerClassAccessFlags;

	public InnerClassEntry(int innerClassInfoIndex, int outerClassInfoIndex, int innerNameIndex, int innerClassAccessFlags)
	{
		this.innerClassInfoIndex = innerClassInfoIndex;
		this.outerClassInfoIndex = outerClassInfoIndex;
		this.innerNameIndex = innerNameIndex;
		this.innerClassAccessFlags = innerClassAccessFlags;
	}

	public int getInnerClassInfoIndex()
	{
		return innerClassInfoIndex;
	}

	public int getOuterClassInfoIndex()
	{
		return outerClassInfoIndex;
	}

	public int getInnerNameIndex()
	{
		return innerNameIndex;
	}

	public int getInnerClassAccessFlags()
	{
		return innerClassAccessFlags;
	}
}
