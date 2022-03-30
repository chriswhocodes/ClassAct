package com.chrisnewland.classact.model.attribute;

public class StackMapTableEntry
{
	private int attributeNameIndex;
	private int attributeLength;
	private int numberOfEntries;

	u2              attribute_name_index;
	u4              attribute_length;
	u2              number_of_entries;

	public StackMapTableEntry(int startPc, int endPc, int handlerPc, int catchType)
	{
		this.startPc = startPc;
		this.endPc = endPc;
		this.handlerPc = handlerPc;
		this.catchType = catchType;
	}

	public int getStartPc()
	{
		return startPc;
	}

	public int getEndPc()
	{
		return endPc;
	}

	public int getHandlerPc()
	{
		return handlerPc;
	}

	public int getCatchType()
	{
		return catchType;
	}
}
