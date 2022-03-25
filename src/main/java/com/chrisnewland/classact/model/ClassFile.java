package com.chrisnewland.classact.model;

import com.chrisnewland.classact.model.attribute.Exceptions;
import com.chrisnewland.classact.model.attribute.LocalVariableTable;
import com.chrisnewland.classact.model.attribute.Signature;
import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;
import com.chrisnewland.classact.model.constantpool.entry.EntryClass;
import com.chrisnewland.classact.model.constantpool.entry.EntryMethodRef;
import com.chrisnewland.classact.model.constantpool.entry.EntryNameAndType;

import java.util.ArrayList;
import java.util.List;

public class ClassFile
{
	private int magicNumber;
	private int minorVersion;
	private int majorVersion;
	private ConstantPool constantPool;
	private int accessFlags;
	private int thisClass;
	private int superClass;

	private List<MethodInfo> methodInfoList = new ArrayList<>();

	public List<MethodInfo> getMethodInfoList()
	{
		return methodInfoList;
	}

	public MethodInfo getCurrentMethodInfo()
	{
		return methodInfoList.get(methodInfoList.size() - 1);
	}

	public int getMagicNumber()
	{
		return magicNumber;
	}

	public void setMagicNumber(int magicNumber)
	{
		this.magicNumber = magicNumber;
	}

	public int getMinorVersion()
	{
		return minorVersion;
	}

	public void setMinorVersion(int minorVersion)
	{
		this.minorVersion = minorVersion;
	}

	public int getMajorVersion()
	{
		return majorVersion;
	}

	public void setMajorVersion(int majorVersion)
	{
		this.majorVersion = majorVersion;
	}

	public ConstantPool getConstantPool()
	{
		return constantPool;
	}

	public void setConstantPool(ConstantPool constantPool)
	{
		this.constantPool = constantPool;
	}

	public int getAccessFlags()
	{
		return accessFlags;
	}

	public void setAccessFlags(int accessFlags)
	{
		this.accessFlags = accessFlags;
	}

	public int getThisClass()
	{
		return thisClass;
	}

	public void setThisClass(int thisClass)
	{
		this.thisClass = thisClass;
	}

	public int getSuperClass()
	{
		return superClass;
	}

	public void setSuperClass(int superClass)
	{
		this.superClass = superClass;
	}

	public void dumpMethods()
	{
		System.out.println("dumpMethods");

		for (int i = 0; i < constantPool.size(); i++)
		{
			ConstantPoolEntry entry = constantPool.get(i);

			if (entry instanceof EntryMethodRef)
			{
				EntryMethodRef entryMethodRef = (EntryMethodRef) entry;

				EntryClass entryClass = (EntryClass) constantPool.get(entryMethodRef.getClassIndex());

				String className = constantPool.toString(entryClass.getNameIndex());

				EntryNameAndType entryNameAndType = (EntryNameAndType) constantPool.get(entryMethodRef.getNameAndTypeIndex());

				String methodName = constantPool.toString(entryNameAndType.getNameIndex());

				String descriptor = constantPool.toString(entryNameAndType.getDescriptorIndex());

				System.out.println("Class:" + className + " method:" + methodName + " descriptor:" + descriptor);
			}
		}

		for (MethodInfo methodInfo : methodInfoList)
		{
			System.out.println("Method:" + constantPool.get(methodInfo.getNameIndex())
													   .toString(constantPool) + " " + constantPool.get(
																										   methodInfo.getDescriptorIndex())
																								   .toString(constantPool));

			List<BytecodeLine> bytecodeLines = methodInfo.getBytecodeLines();

			System.out.println("Bytecode:");

			for (BytecodeLine line : bytecodeLines)
			{
				Instruction instruction = line.getInstruction();
				System.out.println(line.getBci() + " : " + instruction + " " + line.getOperandData()
																				   .toString(line, constantPool));
			}

			IntegerIntegerMap lineNumberTable = (IntegerIntegerMap) methodInfo.getAttribute(Attribute.LineNumberTable);

			if (lineNumberTable != null)
			{
				System.out.println("LineNumberTable:");
				System.out.println(lineNumberTable.toString());
			}

			LocalVariableTable localVariableTable = (LocalVariableTable) methodInfo.getAttribute(Attribute.LocalVariableTable);

			if (localVariableTable != null)
			{
				System.out.println("localVariableTable:");
				System.out.println(localVariableTable.toString(constantPool));
			}

			Exceptions exceptions = (Exceptions) methodInfo.getAttribute(Attribute.Exceptions);

			if (exceptions != null)
			{
				System.out.println("Exceptions:");
				System.out.println(exceptions.toString(constantPool));
			}

			Signature signature = (Signature) methodInfo.getAttribute(Attribute.Signature);

			if (signature != null)
			{
				System.out.println("Signature:");
				System.out.println(signature.toString(constantPool));
			}
		}
	}
}
