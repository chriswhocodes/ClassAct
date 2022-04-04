package com.chrisnewland.classact.model.attribute.annotations;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class RuntimeVisibleAnnotations extends RuntimeAnnotations
{

	private Annotation[] annotations;

	public RuntimeVisibleAnnotations(int count)
	{
		annotations = new Annotation[count];
	}

	public void set(int index, Annotation entry)
	{
		annotations[index] = entry;
	}

	@Override
	public AttributeType getType()
	{
		return AttributeType.RuntimeVisibleAnnotations;
	}

	@Override
	public String toString(ConstantPool constantPool)
	{
		StringBuilder builder = new StringBuilder();

		for (Annotation annotation : annotations)
		{
			builder.append(annotation.toString(constantPool))
				   .append("\n");
		}

		return builder.toString();
	}

	public Annotation[] getAnnotations()
	{
		return annotations;
	}
}