package com.chrisnewland.classact.model;

import com.chrisnewland.classact.model.BytecodeLine;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public interface OperandData
{
	String toString(BytecodeLine bytecodeLine, ConstantPool constantPool);
}
