package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public interface Attribute
{
	AttributeType getType();

	String toString(ConstantPool constantPool);
}
