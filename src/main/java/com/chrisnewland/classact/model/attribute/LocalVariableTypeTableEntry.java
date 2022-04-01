package com.chrisnewland.classact.model.attribute;

public class LocalVariableTypeTableEntry
{
	private int startPc;
	private int length;
	private int nameIndex;
	private int signatureIndex;
	private int index;

	public LocalVariableTypeTableEntry(int startPc, int length, int nameIndex, int signatureIndex, int index)
	{
		this.startPc = startPc;
		this.length = length;
		this.nameIndex = nameIndex;
		this.signatureIndex = signatureIndex;
		this.index = index;
	}

	public int getStartPc()
	{
		return startPc;
	}

	public int getLength()
	{
		return length;
	}

	public int getNameIndex()
	{
		return nameIndex;
	}

	public int getSignatureIndex()
	{
		return signatureIndex;
	}

	public int getIndex()
	{
		return index;
	}
}