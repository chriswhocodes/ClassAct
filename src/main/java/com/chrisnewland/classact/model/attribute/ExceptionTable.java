package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class ExceptionTable
{
	private ExceptionTableEntry[] entries;

	public ExceptionTable(int count)
	{
		entries = new ExceptionTableEntry[count];
	}

	public void set(int index, ExceptionTableEntry entry)
	{
		entries[index] = entry;
	}

	public String toString(ConstantPool constantPool)
	{
		StringBuilder builder = new StringBuilder();

		builder.append("Start  End  Handler  Type")
			   .append("\n");

		for (ExceptionTableEntry entry : entries)
		{
			builder.append(String.format("%10s%10s%10s%10s", entry.getStartPc(), entry.getEndPc(), entry.getHandlerPc(),
						   constantPool.toString(entry.getCatchType())))
				   .append("\n");
		}

		return builder.toString();
	}
}
