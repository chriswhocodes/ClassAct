package com.chrisnewland.classact.model.attribute.annotations;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class RuntimeInvisibleParameterAnnotations extends RuntimeAnnotations
{
	private ParameterAnnotation[] parameterAnnotations;

	public RuntimeInvisibleParameterAnnotations(int count)
	{
		parameterAnnotations = new ParameterAnnotation[count];
	}

	public void set(int index, ParameterAnnotation entry)
	{
		parameterAnnotations[index] = entry;
	}

	@Override
	public AttributeType getType()
	{
		return AttributeType.RuntimeInvisibleParameterAnnotations;
	}

	@Override
	public String toString(ConstantPool constantPool)
	{
		StringBuilder builder = new StringBuilder();

		for (ParameterAnnotation parameterAnnotation : parameterAnnotations)
		{
			builder.append(parameterAnnotation.toString(constantPool))
				   .append("\n");
		}

		return builder.toString();
	}

	public ParameterAnnotation[] getParameterAnnotations()
	{
		return parameterAnnotations;
	}
}