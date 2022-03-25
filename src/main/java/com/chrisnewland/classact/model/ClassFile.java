package com.chrisnewland.classact.model;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

public class ClassFile {
    private int magicNumber;
    private int minorVersion;
    private int majorVersion;
    private ConstantPool constantPool;
    private int accessFlags;
    private int thisClass;
    private int superClass;
}
