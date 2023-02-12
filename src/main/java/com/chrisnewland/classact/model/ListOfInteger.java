package com.chrisnewland.classact.model;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListOfInteger extends ArrayList<Integer> implements OperandData
{

	public List<Integer> getConstantPoolIndices(BytecodeLine bytecodeLine)
	{
		List<Integer> result = new ArrayList<>();

		String decodeExtraBytes = bytecodeLine.getInstruction().getDecodeExtraBytes();

		int byteIndex = 0;

		for (int i = 0; i < decodeExtraBytes.length(); i++)
		{
			switch (decodeExtraBytes.charAt(i))
			{
			case 'c': // constant pool (1 byte)
			{
				int signedByte1 = get(byteIndex);
				result.add(signedByte1);
				byteIndex++;
			}
			break;
			case 'C': // constant pool (2 bytes)
			{
				int signedByte1 = get(byteIndex);
				int signedByte2 = get(byteIndex + 1);
				int index = (signedByte1 << 8) + signedByte2;

				result.add(index);
				byteIndex += 2;
			}
			break;
			}
		}

		return result;
	}

	@Override
	public String toString(BytecodeLine bytecodeLine, ConstantPool constantPool)
	{
		StringBuilder builder = new StringBuilder();

		String decodeExtraBytes = bytecodeLine.getInstruction().getDecodeExtraBytes();

		int byteIndex = 0;

		builder.append(Arrays.toString(this.toArray())).append("\n");

		for (int i = 0; i < decodeExtraBytes.length(); i++)
		{
			char c = decodeExtraBytes.charAt(i);

			builder.append(c).append(" > ");

			switch (c)
			{
			case 'L': // local slot (1 byte)
			{
				int signedByte1 = get(byteIndex);
				builder.append("local slot ").append(signedByte1);
				byteIndex++;
			}
			break;
			case 'c': // constant pool (1 byte)
			{
				int signedByte1 = get(byteIndex);

				builder.append("constant pool #").append(signedByte1).append(" // ").append(constantPool.toString(signedByte1));
				byteIndex++;
			}
			break;
			case 'C': // constant pool (2 bytes)
			{
				int signedByte1 = get(byteIndex);
				int signedByte2 = get(byteIndex + 1);
				int index = (signedByte1 << 8) + signedByte2;

				builder.append("constant pool ").append(index).append(" // ").append(constantPool.toString(index));
				byteIndex += 2;
			}
			break;
			case 'B': // immediate (1 byte)
			{
				int signedByte1 = get(byteIndex);
				builder.append("immediate ").append(signedByte1);
				byteIndex++;
			}
			break;
			case 'W': // immediate (2 bytes)
			{
				int signedByte1 = get(byteIndex);
				int signedByte2 = get(byteIndex + 1);
				int index = (signedByte1 << 8) + signedByte2;
				builder.append("immediate ").append(index);
				byteIndex += 2;
			}
			break;
			case 'i': // BCI (2 bytes signed offset from instruction BCI)
			{
				int bci = bytecodeLine.getBci();

				int signedByte1 = get(byteIndex);
				int signedByte2 = get(byteIndex + 1);
				int index = (signedByte1 << 8) | signedByte2;

				index = (bci + (short) index);

				builder.append("bci ").append(index);
				byteIndex += 2;
			}
			break;
			case 'I': // BCI (4 bytes signed offset from instruction BCI)
			{
				int bci = bytecodeLine.getBci();

				int signedByte1 = get(byteIndex);
				int signedByte2 = get(byteIndex + 1);
				int signedByte3 = get(byteIndex + 2);
				int signedByte4 = get(byteIndex + 3);
				int index = (signedByte1 << 24) + (signedByte2 << 16) + (signedByte3 << 8) + signedByte4;

				index = (bci + (short) index);

				builder.append("bci ").append(index);
				byteIndex += 4;
			}
			break;
			case '-':
				break;
			}

			builder.append("\n");
		}

		if (builder.length() > 0)
		{
			builder.deleteCharAt(builder.length() - 1);
		}

		return builder.toString();
	}
}
