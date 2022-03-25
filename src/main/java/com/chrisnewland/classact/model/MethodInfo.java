package com.chrisnewland.classact.model;

import com.chrisnewland.classact.model.Attribute;
import com.chrisnewland.classact.model.BytecodeLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodInfo
{

	private int accessFlags;
	private int nameIndex;
	private int descriptorIndex;

	private Map<Attribute, Object> attributeMap = new HashMap<>();

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

	public void setAttribute(Attribute attribute, Object object)
	{
		attributeMap.put(attribute, object);
	}

	public Object getAttribute(Attribute attribute)
	{
		return attributeMap.get(attribute);
	}

	public void addBytecodeLine(BytecodeLine bytecodeLine)
	{
		bytecodeLines.add(bytecodeLine);
	}

	public List<BytecodeLine> getBytecodeLines()
	{
		return bytecodeLines;
	}
}
