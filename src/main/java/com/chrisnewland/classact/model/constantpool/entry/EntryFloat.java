package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;

public class EntryFloat implements ConstantPoolEntry
{
	private float value;

	public EntryFloat(int bits)
	{
		if (bits == 0x7f800000)
		{
			this.value = Float.POSITIVE_INFINITY;
		}
		else if (bits == 0xff800000)
		{
			this.value = Float.NEGATIVE_INFINITY;
		}
		else if (bits >= 0x7f800001 && bits <= 0x7fffffff)
		{
			this.value = Float.NaN;
		}
		else if (bits >= 0xff800001 && bits <= 0xffffffff)
		{
			this.value = Float.NaN;
		}
		else
		{
			int sign = ((bits >> 31) == 0) ? 1 : -1;
			int exponent = ((bits >> 23) & 0xff);
			int mantissa = (exponent == 0) ? (bits & 0x7fffff) << 1 : (bits & 0x7fffff) | 0x800000;

			this.value = (float) (sign * mantissa * Math.pow(2.0, exponent - 150));
		}
	}

	public float getValue()
	{
		return value;
	}

	@Override
	public String toString(ConstantPool constantPool)
	{
		return "float " + value + "f";
	}
}
