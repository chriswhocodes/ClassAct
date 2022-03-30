package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class StackMapTable
{
	private StackMapTableEntry[] entries;

	public StackMapTable(int count)
	{
		entries = new StackMapTableEntry[count];
	}

	public void set(int index, StackMapTableEntry entry)
	{
		entries[index] = entry;
	}

	public String toString(ConstantPool constantPool)
	{
		StringBuilder builder = new StringBuilder();

		builder.append("Start  End  Handler  Type")
			   .append("\n");

		for (StackMapTableEntry entry : entries)
		{
			builder.append(String.format("%10s%10s%10s%10s", entry.getStartPc(), entry.getEndPc(), entry.getHandlerPc(),
						   constantPool.toString(entry.getCatchType())))
				   .append("\n");
		}

		return builder.toString();
	}
}
