package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;
import com.chrisnewland.classact.model.constantpool.ConstantPoolType;

public abstract class AbstractMemberRef implements ConstantPoolEntry
{
	private final int classIndex;
	private final int nameAndTypeIndex;

	public AbstractMemberRef(int classIndex, int nameAndTypeIndex)
	{
		this.classIndex = classIndex;
		this.nameAndTypeIndex = nameAndTypeIndex;
	}

	public int getClassIndex()
	{
		return classIndex;
	}

	public int getNameAndTypeIndex()
	{
		return nameAndTypeIndex;
	}

	@Override
	public String toString(ConstantPool constantPool)
	{
		EntryClass entryClass = (EntryClass) constantPool.get(classIndex);

		EntryNameAndType entryNameAndType = (EntryNameAndType) constantPool.get(nameAndTypeIndex);

		StringBuilder builder = new StringBuilder();

		builder.append(entryClass.toString(constantPool))
			   .append(' ')
			   .append(entryNameAndType.toString(constantPool));

		return builder.toString();
	}
}