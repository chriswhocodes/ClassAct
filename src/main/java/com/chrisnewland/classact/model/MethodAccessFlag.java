package com.chrisnewland.classact.model;

import java.util.*;

public enum MethodAccessFlag
{
	ACC_PUBLIC(0x0001),
	ACC_PRIVATE(0x0002),
	ACC_PROTECTED(0x0004),
	ACC_STATIC(0x0008),
	ACC_FINAL(0x0010),
	ACC_SYNCHRONIZED(0x0020),
	ACC_BRIDGE(0x0040),
	ACC_VARARGS(0x0080),
	ACC_NATIVE(0x0100),
	ACC_ABSTRACT(0x0400),
	ACC_STRICT(0x0800),
	ACC_SYNTHETIC(0x1000);

	private int value;

	MethodAccessFlag(int value)
	{
		this.value = value;
	}

	public static List<MethodAccessFlag> getFlags(int value)
	{
		List<MethodAccessFlag> result = new ArrayList<>();

		for (MethodAccessFlag flag : values())
		{
			if ((value & flag.value) == flag.value)
			{
				result.add(flag);
			}
		}

		return result;
	}

	public static String getFlagsString(int value)
	{
		List<MethodAccessFlag> flags = getFlags(value);

		Collections.sort(flags);

		StringBuilder builder = new StringBuilder();

		for (MethodAccessFlag flag : flags)
		{
			builder.append(flag.toString()).append('|');
		}

		if (builder.length() > 0)
		{
			builder.deleteCharAt(builder.length() - 1);
		}

		return builder.toString();
	}
}