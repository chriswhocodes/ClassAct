package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;

public class EntryLong implements ConstantPoolEntry
{
	private long value;

	public EntryLong(long value)
	{
		this.value = value;
	}

	public long getValue()
	{
		return value;
	}

	@Override
	public String toString(ConstantPool constantPool)
	{
		return "long " + value + "l";
	}
}
