package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;

public class EntryDouble implements ConstantPoolEntry
{
	private double value;

	public EntryDouble(long bits)
	{
		if (bits == 0x7ff0000000000000L)
		{
			this.value = Double.POSITIVE_INFINITY;
		}
		else if (bits == 0xfff0000000000000L)
		{
			this.value = Double.NEGATIVE_INFINITY;
		}
		else if (bits >= 0x7f800001 && bits <= 0x7fffffff)
		{
			this.value = Double.NaN;
		}
		else if (bits >= 0xff800001 && bits <= 0xffffffff)
		{
			this.value = Double.NaN;
		}
		else
		{
			int sign = ((bits >> 63) == 0) ? 1 : -1;
			int exponent = (int) ((bits >> 52) & 0x7ffL);
			long mantissa = (exponent == 0) ? (bits & 0xfffffffffffffL) << 1 : (bits & 0xfffffffffffffL) | 0x10000000000000L;

			this.value = (float) (sign * mantissa * Math.pow(2.0, exponent - 1075));
		}
	}

	public double getValue()
	{
		return value;
	}

	@Override
	public String toString(ConstantPool constantPool)
	{
		return "double " + value + "d";
	}
}
