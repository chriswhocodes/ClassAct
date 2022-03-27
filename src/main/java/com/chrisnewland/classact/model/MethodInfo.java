package com.chrisnewland.classact.model;

import com.chrisnewland.classact.model.attribute.Attributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodInfo
{
	private int accessFlags;
	private int nameIndex;
	private int descriptorIndex;
	private Attributes attributes;

	private List<BytecodeLine> bytecodeLines = new ArrayList<>();

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

	public void addBytecodeLine(BytecodeLine bytecodeLine)
	{
		bytecodeLines.add(bytecodeLine);
	}

	public List<BytecodeLine> getBytecodeLines()
	{
		return bytecodeLines;
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
