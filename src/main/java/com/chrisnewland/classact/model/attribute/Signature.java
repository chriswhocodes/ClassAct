package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class Signature implements Attribute
{
	@Override
	public AttributeType getType()
	{
		return AttributeType.Signature;
	}

	private int signatureIndex;

	public Signature(int signatureIndex)
	{
		this.signatureIndex = signatureIndex;
	}

	public int getSignatureIndex()
	{
		return signatureIndex;
	}

	public String toString(ConstantPool constantPool)
	{
		return constantPool.toString(signatureIndex);
	}
}
