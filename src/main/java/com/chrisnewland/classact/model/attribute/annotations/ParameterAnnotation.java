package com.chrisnewland.classact.model.attribute.annotations;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class ParameterAnnotation
{
	private Annotation[] annotations;

	public ParameterAnnotation(int count)
	{
		annotations = new Annotation[count];
	}

	public void set(int index, Annotation entry)
	{
		annotations[index] = entry;
	}

	public Annotation[] getAnnotations()
	{
		return annotations;
	}

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

}
