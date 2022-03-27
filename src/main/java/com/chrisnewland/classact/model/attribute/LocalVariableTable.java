package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class LocalVariableTable implements Attribute
{
	@Override
	public AttributeType getType()
	{
		return AttributeType.LocalVariableTable;
	}

	private LocalVariableTableEntry[] entries;

	public LocalVariableTable(int count)
	{
		entries = new LocalVariableTableEntry[count];
	}

	public LocalVariableTableEntry[] getEntries()
	{
		return entries;
	}

	public void setEntry(int index, LocalVariableTableEntry entry)
	{
		this.entries[index] = entry;
	}

	public String toString(ConstantPool constantPool)
	{
		StringBuilder builder = new StringBuilder();

		builder.append("Start  Length  Slot  Name   Signature")
			   .append("\n");

		for (LocalVariableTableEntry entry : entries)
		{
			builder.append(String.format("%5s%8s%6s  %s   %s", entry.getStartPc(), entry.getLength(), entry.getIndex(),
						   constantPool.toString(entry.getNameIndex()), constantPool.toString(entry.getDescriptorIndex())))
				   .append("\n");
		}

		return builder.toString();
	}
}
