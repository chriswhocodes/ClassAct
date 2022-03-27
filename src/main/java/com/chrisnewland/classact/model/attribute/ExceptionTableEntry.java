package com.chrisnewland.classact.model.attribute;

public class ExceptionTableEntry
{
	private int startPc; // inclusive
	private int endPc; // exclusive
	private int handlerPc;
	private int catchType;

	public ExceptionTableEntry(int startPc, int endPc, int handlerPc, int catchType)
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
