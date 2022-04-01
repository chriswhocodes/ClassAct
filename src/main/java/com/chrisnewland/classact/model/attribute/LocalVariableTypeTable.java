package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class LocalVariableTypeTable implements Attribute
{
	@Override
	public AttributeType getType()
	{
		return AttributeType.LocalVariableTypeTable;
	}

	private LocalVariableTypeTableEntry[] entries;

	public LocalVariableTypeTable(int count)
	{
		entries = new LocalVariableTypeTableEntry[count];
	}

	public LocalVariableTypeTableEntry[] getEntries()
	{
		return entries;
	}

	public void setEntry(int index, LocalVariableTypeTableEntry entry)
	{
		this.entries[index] = entry;
	}

	public String toString(ConstantPool constantPool)
	{
		StringBuilder builder = new StringBuilder();

		builder.append("Start  Length  Slot  Name   Signature")
			   .append("\n");

		for (LocalVariableTypeTableEntry entry : entries)
		{
			builder.append(String.format("%5s%8s%6s  %s   %s", entry.getStartPc(), entry.getLength(), entry.getIndex(),
						   constantPool.toString(entry.getNameIndex()), constantPool.toString(entry.getSignatureIndex())))
				   .append("\n");
		}

		return builder.toString();
	}
}
