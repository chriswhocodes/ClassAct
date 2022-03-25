package com.chrisnewland.classact.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class IntegerIntegerMap extends LinkedHashMap<Integer, Integer>
{
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		for (Map.Entry<Integer, Integer> entry : entrySet())
		{
			builder.append("line ")
				   .append(entry.getValue())
				   .append(" : ")
				   .append(entry.getKey())
				   .append("\n");
		}

		return builder.toString();
	}
}
