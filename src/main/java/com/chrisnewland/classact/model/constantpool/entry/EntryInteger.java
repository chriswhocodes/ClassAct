package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;
import com.chrisnewland.classact.model.constantpool.ConstantPoolType;

public class EntryInteger implements ConstantPoolEntry
{
	private int value;

	public EntryInteger(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}

	@Override
	public ConstantPoolType getType() {
		return ConstantPoolType.CONSTANT_Integer;
	}

	@Override
	public String toString(ConstantPool constantPool)
	{
		return "int " + value;
	}
}
