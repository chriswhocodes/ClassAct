package com.chrisnewland.classact.model;

import com.chrisnewland.classact.model.attribute.Attributes;

public class MethodInfo
{
	private int accessFlags;
	private int nameIndex;
	private int descriptorIndex;
	private Attributes attributes;

	public MethodInfo(int accessFlags, int nameIndex, int descriptorIndex)
	{
		this.accessFlags = accessFlags;
		this.nameIndex = nameIndex;
		this.descriptorIndex = descriptorIndex;
	}

	public int getAccessFlags()
	{
		return accessFlags;
	}

	public int getNameIndex()
	{
		return nameIndex;
	}

	public int getDescriptorIndex()
	{
		return descriptorIndex;
	}

	public Attributes getAttributes()
	{
		return attributes;
	}

	public void setAttributes(Attributes attributes)
	{
		this.attributes = attributes;
	}
}
