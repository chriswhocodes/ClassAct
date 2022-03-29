package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.BytecodeLine;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

import java.util.ArrayList;
import java.util.List;

public class Code implements Attribute
{
	@Override
	public AttributeType getType()
	{
		return AttributeType.Code;
	}

	private int maxStack;
	private int maxLocals;

	private byte[] code;
	private List<BytecodeLine> bytecodeLines = new ArrayList<>();

	private ExceptionTable exceptionTable;
	private Attributes attributes;

	public int getMaxStack()
	{
		return maxStack;
	}

	public void setMaxStack(int maxStack)
	{
		this.maxStack = maxStack;
	}

	public int getMaxLocals()
	{
		return maxLocals;
	}

	public void setMaxLocals(int maxLocals)
	{
		this.maxLocals = maxLocals;
	}

	public byte[] getCode()
	{
		return code;
	}

	public void setCode(byte[] code)
	{
		this.code = code;
	}

	public ExceptionTable getExceptionTable()
	{
		return exceptionTable;
	}

	public void setExceptionTable(ExceptionTable exceptionTable)
	{
		this.exceptionTable = exceptionTable;
	}

	public Attributes getAttributes()
	{
		return attributes;
	}

	public void setAttributes(Attributes attributes)
	{
		this.attributes = attributes;
	}

	public void addBytecodeLine(BytecodeLine bytecodeLine)
	{
		bytecodeLines.add(bytecodeLine);
	}

	public List<BytecodeLine> getBytecodeLines()
	{
		return bytecodeLines;
	}

	@Override
	public String toString(ConstantPool constantPool)
	{
		StringBuilder builder = new StringBuilder();

		builder.append("MaxStack: ").append(maxStack).append("\n");
		builder.append("MaxLocals: ").append(maxLocals).append("\n");
		builder.append("Exceptions:").append("\n").append(exceptionTable.toString(constantPool));
		builder.append("Attributes:").append("\n").append(attributes.toString(constantPool));

		return builder.toString();
	}
}
