package com.chrisnewland.classact;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class NoOperands implements OperandData {
    @Override
    public String toString(Instruction instruction, ConstantPool constantPool) {
        return "";
    }
}
