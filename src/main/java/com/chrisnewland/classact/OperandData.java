package com.chrisnewland.classact;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public interface OperandData {
    String toString(BytecodeLine bytecodeLine, ConstantPool constantPool);
}
