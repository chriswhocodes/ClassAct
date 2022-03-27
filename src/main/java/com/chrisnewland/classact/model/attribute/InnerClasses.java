package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class InnerClasses implements Attribute
{
	@Override
	public AttributeType getType()
	{
		return AttributeType.InnerClasses;
	}

	private InnerClassEntry[] entries;

	public InnerClasses(int count)
	{
		entries = new InnerClassEntry[count];
	}

	public void set(int index, InnerClassEntry entry)
	{
		entries[index] = entry;
	}

	public InnerClassEntry[] getEntries()
	{
		return entries;
	}

	public String toString(ConstantPool constantPool)
	{
		StringBuilder builder = new StringBuilder();

		for (InnerClassEntry entry : entries)
		{
			builder.append(decodeAccessFlags(entry.getInnerClassAccessFlags()))
				   .append(" ")
				   .append(entry.getInnerNameIndex() == 0 ? "" : constantPool.toString(entry.getInnerNameIndex()))
				   .append(" ")
				   .append(constantPool.toString(entry.getInnerClassInfoIndex()))
				   .append(" ")
				   .append(entry.getOuterClassInfoIndex() == 0 ? "" : constantPool.toString(entry.getOuterClassInfoIndex()))
				   .append("\n");
		}

		return builder.toString();
	}

	private String decodeAccessFlags(int flags)
	{
		StringBuilder builder = new StringBuilder();

		if ((flags & 0x0001) > 0)
		{
			builder.append(" public");
		}
		if ((flags & 0x0002) > 0)
		{
			builder.append(" private");
		}
		if ((flags & 0x0004) > 0)
		{
			builder.append(" protected");
		}
		if ((flags & 0x0008) > 0)
		{
			builder.append(" static");
		}
		if ((flags & 0x0010) > 0)
		{
			builder.append(" final");
		}
		if ((flags & 0x0200) > 0)
		{
			builder.append(" interface");
		}
		if ((flags & 0x0400) > 0)
		{
			builder.append(" abstract");
		}
		if ((flags & 0x1000) > 0)
		{
			builder.append(" synthetic");
		}
		if ((flags & 0x2000) > 0)
		{
			builder.append(" annotation");
		}
		if ((flags & 0x4000) > 0)
		{
			builder.append(" enum");
		}

		return builder.toString()
					  .trim();
	}
}
