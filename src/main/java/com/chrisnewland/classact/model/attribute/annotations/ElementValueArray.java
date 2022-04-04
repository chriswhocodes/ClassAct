package com.chrisnewland.classact.model.attribute.annotations;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class ElementValueArray implements ElementValue
{
	private ElementValue[] values;

	public ElementValueArray(int count)
	{
		this.values = new ElementValue[count];
	}

	public void set(int index, ElementValue elementValue)
	{
		values[index] = elementValue;
	}

	public ElementValue[] getValues()
	{
		return values;
	}

	public String toString(ConstantPool constantPool)
	{
		StringBuilder builder = new StringBuilder();

		for (ElementValue value : values)
		{
			builder.append(value.toString(constantPool))
				   .append("\n");
		}

		return builder.toString();
	}
}
