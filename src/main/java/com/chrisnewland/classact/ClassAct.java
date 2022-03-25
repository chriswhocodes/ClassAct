package com.chrisnewland.classact;

import com.chrisnewland.classact.model.*;
import com.chrisnewland.classact.model.attribute.Exceptions;
import com.chrisnewland.classact.model.attribute.LocalVariableTable;
import com.chrisnewland.classact.model.attribute.LocalVariableTableEntry;
import com.chrisnewland.classact.model.attribute.Signature;
import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolTag;
import com.chrisnewland.classact.model.constantpool.entry.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// https://docs.oracle.com/javase/specs/jvms/se17/html/jvms-4.html

// https://docs.oracle.com/javase/specs/jvms/se17/html/jvms-6.html#jvms-6.5

public class ClassAct
{
	private DataInputStream dis;

	private int currentPoolIndex = 0;

	private int currentMethodBCI = 0;

	private ClassFile classFile;

	public static void main(String[] args) throws Exception
	{
		File classFile = new File(args[0]);

		int count = 1;

		for (int i = 0; i < count; i++)
		{
			long start = System.nanoTime();

			new ClassAct(classFile);

			long stop = System.nanoTime();

			double elapsed = (stop - start) / 1_000_000d;

			System.out.println("elapsed: " + elapsed + "ms");
		}
	}

	public ClassAct(File classFileName) throws Exception
	{
		classFile = new ClassFile();

		dis = new DataInputStream(new BufferedInputStream(new FileInputStream(classFileName)));

		classFile.setMagicNumber(dis.readInt());
		classFile.setMinorVersion(dis.readUnsignedShort());
		classFile.setMinorVersion(dis.readUnsignedShort());

		int constantPoolCount = dis.readUnsignedShort();

		debug("constantPoolCount: " + constantPoolCount);

		classFile.setConstantPool(new ConstantPool(constantPoolCount));

		processConstantPool(constantPoolCount);

		int accessFlags = dis.readUnsignedShort();

		debug("accessFlags: " + Integer.toBinaryString(accessFlags));

		int thisClass = dis.readUnsignedShort();

		debug("thisClass: " + thisClass);

		int superClass = dis.readUnsignedShort();

		debug("superClass: " + superClass);

		int interfacesCount = dis.readUnsignedShort();

		debug("interfacesCount: " + interfacesCount);

		processInterfaces(interfacesCount);

		int fieldsCount = dis.readUnsignedShort();

		debug("fieldsCount: " + fieldsCount);

		processFields(fieldsCount);

		int methodsCount = dis.readUnsignedShort();

		debug("methodsCount: " + methodsCount);

		processMethods(methodsCount);

		int attributesCount = dis.readUnsignedShort();

		debug("attributesCount: " + attributesCount);

		processAttributes(attributesCount);

		classFile.dumpMethods();
	}

	public ClassFile getClassFile()
	{
		return classFile;
	}

	private void debug(String message)
	{
		System.out.println(message);
	}

	private void info(String message)
	{
		System.out.println(message);
	}

	private void processConstantPool(int constantPoolCount) throws IOException
	{
		for (int i = 0; i < constantPoolCount - 1; i++)
		{
			int tagByte = dis.readUnsignedByte();

			currentPoolIndex = i + 1;

			ConstantPoolTag tag = ConstantPoolTag.valueOf(tagByte)
												 .get();

			debug("Constant[" + currentPoolIndex + "] tagByte " + tagByte + " tag " + tag);

			switch (tag)
			{
			case CONSTANT_Class:
				processConstantClass(dis);
				break;
			case CONSTANT_Fieldref:
				processConstantFieldRef(dis);
				break;
			case CONSTANT_Methodref:
				processConstantMethodRef(dis);
				break;
			case CONSTANT_InterfaceMethodref:
				processConstantInterfaceMethodRef(dis);
				break;
			case CONSTANT_String:
				processConstantString(dis);
				break;
			case CONSTANT_Integer:
				processConstantInteger(dis);
				break;
			case CONSTANT_Float:
				processConstantFloat(dis);
				break;
			case CONSTANT_Long:
				processConstantLong(dis);
				i++; // 8 byte constants use 2 pool slots
				break;
			case CONSTANT_Double:
				processConstantDouble(dis);
				i++; // 8 byte constants use 2 pool slots
				break;
			case CONSTANT_NameAndType:
				processConstantNameAndType(dis);
				break;
			case CONSTANT_Utf8:
				processConstantUTF8(dis);
				break;
			case CONSTANT_MethodHandle:
				throw new UnsupportedOperationException();
			case CONSTANT_MethodType:
				throw new UnsupportedOperationException();
			case CONSTANT_Dynamic:
				throw new UnsupportedOperationException();
			case CONSTANT_InvokeDynamic:
				throw new UnsupportedOperationException();
			case CONSTANT_Module:
				throw new UnsupportedOperationException();
			case CONSTANT_Package:
				throw new UnsupportedOperationException();
			}
		}
	}

	private void processConstantClass(DataInputStream dis) throws IOException
	{
		int nameIndex = dis.readUnsignedShort();

		debug("processConstantClass nameIndex " + nameIndex);

		EntryClass entryClass = new EntryClass(nameIndex);

		classFile.getConstantPool()
				 .set(currentPoolIndex, entryClass);
	}

	private void processConstantMethodRef(DataInputStream dis) throws IOException
	{
		int classIndex = dis.readUnsignedShort();

		debug("processConstantMethodRef classIndex " + classIndex);

		int nameAndTypeIndex = dis.readUnsignedShort();

		debug("processConstantMethodRef nameAndTypeIndex " + nameAndTypeIndex);

		EntryMethodRef entryMethodRef = new EntryMethodRef(classIndex, nameAndTypeIndex);

		classFile.getConstantPool()
				 .set(currentPoolIndex, entryMethodRef);
	}

	private void processConstantInterfaceMethodRef(DataInputStream dis) throws IOException
	{
		int classIndex = dis.readUnsignedShort();

		debug("processConstantInterfaceMethodRef classIndex " + classIndex);

		int nameAndTypeIndex = dis.readUnsignedShort();

		debug("processConstantInterfaceMethodRef nameAndTypeIndex " + nameAndTypeIndex);

		EntryMethodRef entryMethodRef = new EntryMethodRef(classIndex, nameAndTypeIndex);

		classFile.getConstantPool()
				 .set(currentPoolIndex, entryMethodRef);
	}

	private void processConstantFieldRef(DataInputStream dis) throws IOException
	{
		int classIndex = dis.readUnsignedShort();

		debug("processConstantFieldRef classIndex " + classIndex);

		int nameAndTypeIndex = dis.readUnsignedShort();

		debug("processConstantFieldRef nameAndTypeIndex " + nameAndTypeIndex);

		EntryFieldRef entryFieldRef = new EntryFieldRef(classIndex, nameAndTypeIndex);

		classFile.getConstantPool()
				 .set(currentPoolIndex, entryFieldRef);
	}

	private void processConstantString(DataInputStream dis) throws IOException
	{
		int stringIndex = dis.readUnsignedShort();

		debug("processConstantString stringIndex " + stringIndex);

		EntryString entryString = new EntryString(stringIndex);

		classFile.getConstantPool()
				 .set(currentPoolIndex, entryString);
	}

	private void processConstantInteger(DataInputStream dis) throws IOException
	{
		int value = dis.readInt();

		debug("processConstantInteger bytes " + value);

		EntryInteger entryInteger = new EntryInteger(value);

		classFile.getConstantPool()
				 .set(currentPoolIndex, entryInteger);
	}

	private void processConstantFloat(DataInputStream dis) throws IOException
	{
		int bits = dis.readInt();

		debug("processConstantFloat #" + currentPoolIndex + "  = " + bits);

		EntryFloat entryFloat = new EntryFloat(bits);

		classFile.getConstantPool()
				 .set(currentPoolIndex, entryFloat);
	}

	private void processConstantLong(DataInputStream dis) throws IOException
	{
		int high_bytes = dis.readInt();

		debug("processConstantLong high_bytes " + high_bytes);

		int low_bytes = dis.readInt();

		debug("processConstantLong low_bytes " + low_bytes);

		long value = ((long) high_bytes << 32) + low_bytes;

		EntryLong entryLong = new EntryLong(value);

		classFile.getConstantPool()
				 .set(currentPoolIndex, entryLong);
	}

	private void processConstantDouble(DataInputStream dis) throws IOException
	{
		int high_bytes = dis.readInt();

		debug("processConstantDouble high_bytes " + high_bytes);

		int low_bytes = dis.readInt();

		debug("processConstantDouble low_bytes " + low_bytes);

		long bits = ((long) high_bytes << 32) + low_bytes;

		EntryDouble entryDouble = new EntryDouble(bits);

		classFile.getConstantPool()
				 .set(currentPoolIndex, entryDouble);
	}

	private void processConstantUTF8(DataInputStream dis) throws IOException
	{
		int length = dis.readUnsignedShort();

		debug("processConstantUTF8 length " + length);

		byte[] utf8bytes = new byte[length];

		dis.read(utf8bytes);

		String utf8String = new String(utf8bytes);

		debug("processConstantUTF8 bytes '" + utf8String + "'");

		EntryUTF8 entryUTF8 = new EntryUTF8(utf8String);

		classFile.getConstantPool()
				 .set(currentPoolIndex, entryUTF8);
	}

	private void processConstantNameAndType(DataInputStream dis) throws IOException
	{
		int nameIndex = dis.readUnsignedShort();

		debug("processConstantNameAndType nameIndex " + nameIndex);

		int descriptorIndex = dis.readUnsignedShort();

		debug("processConstantNameAndType descriptorIndex " + descriptorIndex);

		EntryNameAndType entryNameAndType = new EntryNameAndType(nameIndex, descriptorIndex);

		classFile.getConstantPool()
				 .set(currentPoolIndex, entryNameAndType);
	}

	private void processInterfaces(int interfacesCount) throws IOException
	{
		for (int i = 0; i < interfacesCount; i++)
		{
			int nameIndex = dis.readUnsignedShort();

			debug("processInterfaces[" + i + "] nameIndex " + nameIndex);

			//            EntryClass entry_class = array_Entry_Classes[nameIndex];
			//
			//            String interfaceName = tableUTF8[entry_class.nameIndex];
			//
			//            debug("processInterfaces[" + i + "] interfaceName " + interfaceName);
		}
	}

	private void processFields(int fieldsCount) throws IOException
	{
		for (int i = 0; i < fieldsCount; i++)
		{
			int accessFlags = dis.readUnsignedShort();

			debug("processFields[" + i + "] accessFlags " + accessFlags);

			int nameIndex = dis.readUnsignedShort();

			debug("processFields[" + i + "] nameIndex " + nameIndex);

			int descriptorIndex = dis.readUnsignedShort();

			debug("processFields[" + i + "] descriptorIndex " + descriptorIndex);

			int attributesCount = dis.readUnsignedShort();

			debug("processFields[" + i + "] attributesCount " + attributesCount);

			processAttributes(attributesCount);
		}
	}

	private void processMethods(int methodsCount) throws IOException
	{
		for (int i = 0; i < methodsCount; i++)
		{
			int accessFlags = dis.readUnsignedShort();

			debug("processMethods[" + i + "] accessFlags " + accessFlags);

			int nameIndex = dis.readUnsignedShort();

			debug("processMethods[" + i + "] nameIndex " + nameIndex);

			int descriptorIndex = dis.readUnsignedShort();

			debug("processMethods[" + i + "] descriptorIndex " + descriptorIndex);

			int attributesCount = dis.readUnsignedShort();

			debug("processMethods[" + i + "] attributesCount " + attributesCount);

			info("METHOD: " + MethodAccessFlag.getFlagsString(accessFlags) + " " + classFile.getConstantPool()
																							.toString(nameIndex) + " "
					+ classFile.getConstantPool()
							   .toString(descriptorIndex));

			MethodInfo methodInfo = new MethodInfo(accessFlags, nameIndex, descriptorIndex);

			classFile.getMethodInfoList()
					 .add(methodInfo);

			processAttributes(attributesCount);
		}
	}

	private void processAttributes(int attributesCount) throws IOException
	{
		for (int i = 0; i < attributesCount; i++)
		{
			int attributeNameIndex = dis.readUnsignedShort();

			debug("processAttributes[" + i + "] attributeNameIndex " + attributeNameIndex);

			int attributeLength = dis.readInt();

			debug("processAttributes[" + i + "] attributeLength " + attributeLength);

			String attributeName = classFile.getConstantPool()
											.toString(attributeNameIndex);

			debug("processAttributes attributeName: " + attributeName);

			processSingleAttribute(attributeName, attributeLength);
		}
	}

	private void processSingleAttribute(String attributeName, int attributeLength) throws IOException
	{
		debug("processSingleAttribute " + attributeName + " attributeLength " + attributeLength);

		Attribute attribute = Attribute.valueOf(attributeName);

		switch (attribute)
		{

		case Code:
			processAttributeCode(dis);
			break;

		case LineNumberTable:
			processAttributeLineNumberTable(dis);
			break;

		case InnerClasses:
			processInnerClasses(dis);
			break;

		case Signature:
			processSignature(dis);
			break;

		case Synthetic:
			processSynthetic(dis);
			break;

		case ConstantValue:
			processConstantValue(dis);
			break;

		case SourceFile:
			processSourceFile(dis);
			break;

		case Exceptions:
			processExceptions(dis);
			break;

		case EnclosingMethod:
			processEnclosingMethod(dis);
			break;

		case LocalVariableTable:
			processLocalVariableTable(dis);
			break;

		default:
			byte[] attributeInfo = new byte[attributeLength];

			dis.read(attributeInfo);
			break;

		//		case StackMapTable:
		//			break;
		//		case SourceDebugExtension:
		//			break;

		//		case LocalVariableTypeTable:
		//			break;
		//		case Deprecated:
		//			break;
		//		case RuntimeVisibleAnnotations:
		//			break;
		//		case RuntimeInvisibleAnnotations:
		//			break;
		//		case RuntimeVisibleParameterAnnotations:
		//			break;
		//		case RuntimeInvisibleParameterAnnotations:
		//			break;
		//		case AnnotationDefault:
		//			break;
		//		case BootstrapMethods:
		//			break;
		}

	}

	private void processAttributeCode(DataInputStream dis) throws IOException
	{
		int max_stack = dis.readUnsignedShort();

		debug("processAttributeCode max_stack: " + max_stack);

		int max_locals = dis.readUnsignedShort();

		debug("processAttributeCode max_locals: " + max_locals);

		int code_length = dis.readInt();

		debug("processAttributeCode code_length: " + code_length);

		processCode(code_length);

		int exception_table_length = dis.readUnsignedShort();

		debug("processAttributeCode exception_table_length: " + exception_table_length);

		processCodeExceptionTable(exception_table_length);

		int attributes_count = dis.readUnsignedShort();

		debug("processAttributeCode attributes_count: " + attributes_count);

		processAttributes(attributes_count);
	}

	private void processCode(int codeLength) throws IOException
	{
		currentMethodBCI = 0;

		while (currentMethodBCI < codeLength)
		{
			int opcode = dis.readUnsignedByte();

			Instruction instruction = Instruction.forOpcode(opcode)
												 .get();

			info("processCode opcode at BCI [" + currentMethodBCI + "] " + opcode + " (" + instruction + ")");

			int extraBytes = instruction.getExtraBytes();

			if (extraBytes > 0)
			{

				ListOfInteger operandData = new ListOfInteger();

				BytecodeLine bytecodeLine = new BytecodeLine(currentMethodBCI, instruction, operandData);

				classFile.getCurrentMethodInfo()
						 .addBytecodeLine(bytecodeLine);

				debug("processCode instruction: " + instruction + " has extra bytes " + extraBytes);

				for (int b = 0; b < extraBytes; b++)
				{
					int param = dis.readUnsignedByte();

					info("processCode extraByte[" + b + "] " + param);

					operandData.add(param);

					currentMethodBCI++;
				}
			}
			else if (extraBytes == -1)
			{
				processVariableLengthInstruction(instruction);
			}
			else
			{
				BytecodeLine bytecodeLine = new BytecodeLine(currentMethodBCI, instruction, new NoOperands());
				classFile.getCurrentMethodInfo()
						 .addBytecodeLine(bytecodeLine);
			}

			currentMethodBCI++;
		}
	}

	private void processVariableLengthInstruction(Instruction instruction) throws IOException
	{
		switch (instruction)
		{
		case LOOKUPSWITCH:
		{
			SwitchTable operandData = new SwitchTable();
			BytecodeLine bytecodeLine = new BytecodeLine(currentMethodBCI, instruction, operandData);
			classFile.getCurrentMethodInfo()
					 .addBytecodeLine(bytecodeLine);
			processLookupSwitch(dis, operandData);
		}
		break;
		case TABLESWITCH:
		{
			SwitchTable operandData = new SwitchTable();
			BytecodeLine bytecodeLine = new BytecodeLine(currentMethodBCI, instruction, operandData);
			classFile.getCurrentMethodInfo()
					 .addBytecodeLine(bytecodeLine);
			processTableSwitch(dis, operandData);
		}
		break;
		case WIDE:
		{
			ListOfInteger operandData = new ListOfInteger();
			BytecodeLine bytecodeLine = new BytecodeLine(currentMethodBCI, instruction, operandData);
			classFile.getCurrentMethodInfo()
					 .addBytecodeLine(bytecodeLine);
			processWide(dis, operandData);
		}
		break;
		}
	}

	private void processWide(DataInputStream dis, ListOfInteger operandData) throws IOException
	{
		int nextOpcode = dis.readUnsignedByte();

		operandData.add(nextOpcode);

		currentMethodBCI++;

		if (nextOpcode == Instruction.IINC.getOpcode())
		{
			int indexByte1 = dis.readUnsignedByte();
			int indexByte2 = dis.readUnsignedByte();
			int countByte1 = dis.readUnsignedByte();
			int countByte2 = dis.readUnsignedByte();

			operandData.add(indexByte1);
			operandData.add(indexByte2);
			operandData.add(countByte1);
			operandData.add(countByte2);

			debug("processWide iinc: " + indexByte1 + ", " + indexByte2 + ", " + countByte1 + ", " + countByte2);

			currentMethodBCI += 4;
		}
		else
		{
			int indexByte1 = dis.readUnsignedByte();
			int indexByte2 = dis.readUnsignedByte();

			operandData.add(indexByte1);
			operandData.add(indexByte2);

			debug("processWide " + nextOpcode + ": " + indexByte1 + ", " + indexByte2);

			currentMethodBCI += 2;
		}
	}

	// lookupswitch contains pairs of (key -> BCI)
	private void processLookupSwitch(DataInputStream dis, SwitchTable operandData) throws IOException
	{
		int baseBCI = currentMethodBCI;

		int padding = 3 - (currentMethodBCI % 4);

		debug("processLookupSwitch bci " + currentMethodBCI + " padding " + padding);

		for (int p = 0; p < padding; p++)
		{
			int pad = dis.readUnsignedByte();
			currentMethodBCI++;
		}

		int defaultByte1 = dis.readUnsignedByte();
		int defaultByte2 = dis.readUnsignedByte();
		int defaultByte3 = dis.readUnsignedByte();
		int defaultByte4 = dis.readUnsignedByte();

		currentMethodBCI += 4;

		int defaultValue = (defaultByte1 << 24) | (defaultByte2 << 16) | (defaultByte3 << 8) | defaultByte4;
		defaultValue += baseBCI;

		debug("processLookupSwitch defaultBytes: " + defaultByte1 + ", " + defaultByte2 + ", " + defaultByte3 + ", " + defaultByte4
				+ " base: " + baseBCI + " = " + defaultValue);

		operandData.put(-1, defaultValue);

		int npairs1 = dis.readUnsignedByte();
		int npairs2 = dis.readUnsignedByte();
		int npairs3 = dis.readUnsignedByte();
		int npairs4 = dis.readUnsignedByte();

		currentMethodBCI += 4;

		int npairs = (npairs1 << 24) | (npairs2 << 16) | (npairs3 << 8) | npairs4;

		debug("processLookupSwitch npairs      : " + npairs1 + ", " + npairs2 + ", " + npairs3 + ", " + npairs4 + " = " + npairs);

		for (int p = 0; p < npairs; p++)
		{
			int match = dis.readInt();

			currentMethodBCI += 4;

			int offsetByte1 = dis.readUnsignedByte();
			int offsetByte2 = dis.readUnsignedByte();
			int offsetByte3 = dis.readUnsignedByte();
			int offsetByte4 = dis.readUnsignedByte();

			currentMethodBCI += 4;

			int offset = (offsetByte1 << 24) | (offsetByte2 << 16) | (offsetByte3 << 8) | offsetByte4;
			offset += baseBCI;

			debug("processLookupSwitch offset      : " + offsetByte1 + ", " + offsetByte2 + ", " + offsetByte3 + ", " + offsetByte4
					+ " base: " + baseBCI + " = " + offset);

			debug("processLookupSwitch pair[" + p + "] match: " + match + " offset: " + offset);

			operandData.put(match, offset);
		}
	}

	// entries = high - low + default, switch (x) looks up an index in the table
	private void processTableSwitch(DataInputStream dis, SwitchTable operandData) throws IOException
	{
		int baseBCI = currentMethodBCI;

		int padding = 3 - (currentMethodBCI % 4);

		debug("processTableSwitch bci " + currentMethodBCI + " padding " + padding);

		for (int p = 0; p < padding; p++)
		{
			int pad = dis.readUnsignedByte();
			currentMethodBCI++;
		}

		int defaultByte1 = dis.readUnsignedByte();
		int defaultByte2 = dis.readUnsignedByte();
		int defaultByte3 = dis.readUnsignedByte();
		int defaultByte4 = dis.readUnsignedByte();

		currentMethodBCI += 4;

		int defaultValue = (defaultByte1 << 24) | (defaultByte2 << 16) | (defaultByte3 << 8) | defaultByte4;
		defaultValue += baseBCI;

		debug("processTableSwitch defaultBytes: " + defaultByte1 + ", " + defaultByte2 + ", " + defaultByte3 + ", " + defaultByte4
				+ " base: " + baseBCI + " = " + defaultValue);

		operandData.put(-1, defaultValue);

		int lowByte1 = dis.readUnsignedByte();
		int lowByte2 = dis.readUnsignedByte();
		int lowByte3 = dis.readUnsignedByte();
		int lowByte4 = dis.readUnsignedByte();

		currentMethodBCI += 4;

		int low = (lowByte1 << 24) | (lowByte2 << 16) | (lowByte3 << 8) | lowByte4;

		debug("processTableSwitch lowBytes    : " + lowByte1 + ", " + lowByte2 + ", " + lowByte3 + ", " + lowByte4 + " = " + low);

		int highByte1 = dis.readUnsignedByte();
		int highByte2 = dis.readUnsignedByte();
		int highByte3 = dis.readUnsignedByte();
		int highByte4 = dis.readUnsignedByte();

		currentMethodBCI += 4;

		int high = (highByte1 << 24) | (highByte2 << 16) | (highByte3 << 8) | highByte4;

		debug("processTableSwitch highBytes   : " + highByte1 + ", " + highByte2 + ", " + highByte3 + ", " + highByte4 + " = "
				+ high);

		int offsetCount = high - low + 1;

		for (int off = 0; off < offsetCount; off++)
		{
			int offsetByte1 = dis.readUnsignedByte();
			int offsetByte2 = dis.readUnsignedByte();
			int offsetByte3 = dis.readUnsignedByte();
			int offsetByte4 = dis.readUnsignedByte();

			currentMethodBCI += 4;

			int offset = (offsetByte1 << 24) | (offsetByte2 << 16) | (offsetByte3 << 8) | offsetByte4;
			offset += baseBCI;

			debug("processTableSwitch offset      : " + offsetByte1 + ", " + offsetByte2 + ", " + offsetByte3 + ", " + offsetByte4
					+ " base:" + baseBCI + " = " + offset);

			operandData.put(low + off, offset);
		}
	}

	private void foo()
	{
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	private void processCodeExceptionTable(int exceptionTableLength) throws IOException
	{
		for (int i = 0; i < exceptionTableLength; i++)
		{
			int start_pc = dis.readUnsignedShort();

			debug("processCodeExceptionTable[" + i + "] start_pc: " + start_pc);

			int end_pc = dis.readUnsignedShort();

			debug("processCodeExceptionTable[" + i + "] end_pc: " + end_pc);

			int handler_pc = dis.readUnsignedShort();

			debug("processCodeExceptionTable[" + i + "] handler_pc: " + handler_pc);

			int catch_type = dis.readUnsignedShort();

			debug("processCodeExceptionTable[" + i + "] catch_type: " + catch_type);

			//TODO finish this
		}
	}

	private void processAttributeLineNumberTable(DataInputStream dis) throws IOException
	{
		int line_number_table_length = dis.readUnsignedShort();

		debug("processAttributeLineNumberTable line_number_table_length: " + line_number_table_length);

		IntegerIntegerMap lineNumberTable = new IntegerIntegerMap();

		for (int l = 0; l < line_number_table_length; l++)
		{
			int start_pc = dis.readUnsignedShort();
			int line_number = dis.readUnsignedShort();

			lineNumberTable.put(start_pc, line_number);

			debug("processAttributeLineNumberTable mapping: " + start_pc + "->" + line_number);
		}

		classFile.getCurrentMethodInfo()
				 .setAttribute(Attribute.LineNumberTable, lineNumberTable);
	}

	private void processLocalVariableTable(DataInputStream dis) throws IOException
	{
		int local_variable_table_length = dis.readUnsignedShort();

		debug("processLocalVariableTable local_variable_table_length: " + local_variable_table_length);

		LocalVariableTable localVariableTable = new LocalVariableTable(local_variable_table_length);

		for (int l = 0; l < local_variable_table_length; l++)
		{
			int start_pc = dis.readUnsignedShort();
			int length = dis.readUnsignedShort();
			int nameIndex = dis.readUnsignedShort();
			int descriptorIndex = dis.readUnsignedShort();
			int index = dis.readUnsignedShort();

			localVariableTable.setEntry(l, new LocalVariableTableEntry(start_pc, length, nameIndex, descriptorIndex, index));
		}

		classFile.getCurrentMethodInfo()
				 .setAttribute(Attribute.LocalVariableTable, localVariableTable);

	}

	private void processExceptions(DataInputStream dis) throws IOException
	{
		int number_of_exceptions = dis.readUnsignedShort();

		debug("processExceptions number_of_exceptions: " + number_of_exceptions);

		Exceptions exceptions = new Exceptions(number_of_exceptions);

		for (int ex = 0; ex < number_of_exceptions; ex++)
		{

			int exception_index = dis.readUnsignedShort();

			debug("exception_index[" + ex + "]:" + exception_index);

			exceptions.set(ex, exception_index);
		}

		classFile.getCurrentMethodInfo()
				 .setAttribute(Attribute.Exceptions, exceptions);
	}

	private class Inner1
	{
		public String pubString;
	}

	public <Q extends Object> List<Q> fooGenerics() throws RuntimeException, NumberFormatException
	{
		return new ArrayList<>();
	}

	private static class StaticInner1
	{
		public int bar;
	}

	private void processInnerClasses(DataInputStream dis) throws IOException
	{
		int number_of_classes = dis.readUnsignedShort();

		debug("processInnerClasses number_of_classes: " + number_of_classes);

		for (int inner = 0; inner < number_of_classes; inner++)
		{
			int inner_class_info_index = dis.readUnsignedShort();
			int outer_class_info_index = dis.readUnsignedShort();
			int inner_name_index = dis.readUnsignedShort();
			int inner_class_access_flags = dis.readUnsignedShort();

			debug("processInnerClasses inner_class_info_index  : " + inner_class_info_index);
			debug("processInnerClasses outer_class_info_index  : " + outer_class_info_index);
			debug("processInnerClasses inner_name_index        : " + inner_name_index);
			debug("processInnerClasses inner_class_access_flags: " + inner_class_access_flags);

			// TODO finish this
		}
	}

	private void processSignature(DataInputStream dis) throws IOException
	{
		int signature_index = dis.readUnsignedShort();

		debug("processSignature signature_index: " + signature_index);

		Signature signature = new Signature(signature_index);

		classFile.getCurrentMethodInfo()
				 .setAttribute(Attribute.Signature, signature);
	}

	private void processSynthetic(DataInputStream dis) throws IOException
	{
		debug("current item is synthetic");

		// TODO finish this
	}

	private static final String fooConstantValueString = "constantfoo";
	private static final double fooConstantValueDouble = 0.125d;
	private static final float fooConstantValueFloat = 0.456f;
	private static final int fooConstantValueInt = 10;
	private static final long fooConstantValueLong = 100L;

	private double getPrimitiveDouble()
	{
		return fooConstantValueDouble;
	}

	private float getPrimitiveFloat()
	{
		return fooConstantValueFloat;
	}

	private int getPrimitiveInt()
	{
		return fooConstantValueInt;
	}

	private long getPrimitiveLong()
	{
		return fooConstantValueLong;
	}

	private void processConstantValue(DataInputStream dis) throws IOException
	{
		int constantvalue_index = dis.readUnsignedShort();

		debug("processConstantValue constantvalue_index: " + constantvalue_index);

		// TODO finish this
	}

	private void processSourceFile(DataInputStream dis) throws IOException
	{
		int sourcefile_index = dis.readUnsignedShort();

		debug("processSourceFile sourcefile_index: " + sourcefile_index);

		// TODO finish this
	}

	private void encloser()
	{
		Runnable r = new Runnable()
		{
			@Override
			public void run()
			{
				debug("running!");
			}
		};
	}

	private void processEnclosingMethod(DataInputStream dis) throws IOException
	{
		int class_index = dis.readUnsignedShort();
		int method_index = dis.readUnsignedShort();
		debug("processEnclosingMethod class_index: " + class_index + " method_index:" + method_index);

		// TODO finish this
	}
}