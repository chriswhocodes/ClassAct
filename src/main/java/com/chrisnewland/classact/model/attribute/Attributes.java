package com.chrisnewland.classact.model.attribute;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class Attributes
{
	private Attribute[] attributes;

	public Attributes(int count)
	{
		attributes = new Attribute[count];
	}

	public Attribute[] getAttributes()
	{
		return attributes;
	}

	public void set(int index, Attribute attribute)
	{
		attributes[index] = attribute;
	}

	public String toString(ConstantPool constantPool)
	{
		StringBuilder builder = new StringBuilder();

		if (attributes.length > 0)
		{
			for (Attribute attribute : attributes)
			{
				if (attribute != null) // remove once all attributes are handled
				{
					builder.append(attribute.getType())
						   .append("\n");

					builder.append(attribute.toString(constantPool))
						   .append("\n");
				}
			}
		}

		return builder.toString();
	}

	public Attribute findAttribute(AttributeType type)
	{
		Attribute result = null;

		System.out.println("findAttribute");

		for (Attribute attribute : attributes)
		{
			System.out.println(attribute);

			if (attribute.getType() == type)
			{
				result = attribute;
			}
		}

		return result;
	}
}