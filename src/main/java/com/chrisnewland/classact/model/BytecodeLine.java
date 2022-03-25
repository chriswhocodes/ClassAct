package com.chrisnewland.classact.model;

public class BytecodeLine
{
	private int bci;
	private Instruction instruction;
	private OperandData operandData;

	public BytecodeLine(int bci, Instruction instruction, OperandData operandData)
	{
		this.bci = bci;
		this.instruction = instruction;
		this.operandData = operandData;
	}

	public int getBci()
	{
		return bci;
	}

	public Instruction getInstruction()
	{
		return instruction;
	}

	public OperandData getOperandData()
	{
		return operandData;
	}
}
