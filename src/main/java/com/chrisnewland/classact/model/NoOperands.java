package com.chrisnewland.classact.model;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class NoOperands implements OperandData
{
	@Override
	public String toString(BytecodeLine bytecodeLine, ConstantPool constantPool)
	{
		return "";
	}
}
