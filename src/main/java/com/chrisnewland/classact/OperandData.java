package com.chrisnewland.classact;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public interface OperandData {
    String toString(Instruction instruction, ConstantPool constantPool);
}
