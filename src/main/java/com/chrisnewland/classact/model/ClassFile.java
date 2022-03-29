package com.chrisnewland.classact.model;

import com.chrisnewland.classact.model.attribute.*;
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

	private Attributes attributes;

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

	public Attributes getAttributes()
	{
		return attributes;
	}

	public void setAttributes(Attributes attributes)
	{
		this.attributes = attributes;
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

			Code code = (Code) methodInfo.getAttributes()
										 .findAttribute(AttributeType.Code);

			List<BytecodeLine> bytecodeLines = code.getBytecodeLines();

			System.out.println("Bytecode:");

			for (BytecodeLine line : bytecodeLines)
			{
				Instruction instruction = line.getInstruction();
				System.out.println(line.getBci() + " : " + instruction + " " + line.getOperandData()
																				   .toString(line, constantPool));
			}

			System.out.println(methodInfo.getAttributes()
										 .toString(constantPool));
		}
	}
}
