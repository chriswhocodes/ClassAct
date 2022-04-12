package com.chrisnewland.classact.model;

import com.chrisnewland.classact.model.attribute.*;
import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;
import com.chrisnewland.classact.model.constantpool.entry.EntryClass;
import com.chrisnewland.classact.model.constantpool.entry.EntryMethodRef;
import com.chrisnewland.classact.model.constantpool.entry.EntryNameAndType;

import java.util.ArrayList;
import java.util.List;

/*
ClassFile {
    u4             magic;
    u2             minor_version;
    u2             major_version;
    u2             constant_pool_count;
    cp_info        constant_pool[constant_pool_count-1];
    u2             access_flags;
    u2             this_class;
    u2             super_class;
    u2             interfaces_count;
    u2             interfaces[interfaces_count];
    u2             fields_count;
    field_info     fields[fields_count];
    u2             methods_count;
    method_info    methods[methods_count];
    u2             attributes_count;
    attribute_info attributes[attributes_count];
}
 */

public class ClassFile {
    private int magicNumber;
    private int minorVersion;
    private int majorVersion;
    private ConstantPool constantPool;
    private int accessFlags;
    private int thisClass;
    private int superClass;

    private List<Integer> interfaceList = new ArrayList<>();

    private List<FieldInfo> fieldInfoList = new ArrayList<>();

    private List<MethodInfo> methodInfoList = new ArrayList<>();

    private Attributes attributes;

    //======================================

    public List<Integer> getInterfaceList() {
        return interfaceList;
    }

    public void addInterface(int interfaceRef) {
        interfaceList.add(interfaceRef);
    }

    public List<MethodInfo> getMethodInfoList() {
        return methodInfoList;
    }

    public void addMethodInfo(MethodInfo methodInfo) {
        methodInfoList.add(methodInfo);
    }

    public List<FieldInfo> getFieldInfoList() {
        return fieldInfoList;
    }

    public void addFieldInfo(FieldInfo fieldInfo) {
        fieldInfoList.add(fieldInfo);
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(int minorVersion) {
        this.minorVersion = minorVersion;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public void setAccessFlags(int accessFlags) {
        this.accessFlags = accessFlags;
    }

    public int getThisClass() {
        return thisClass;
    }

    public void setThisClass(int thisClass) {
        this.thisClass = thisClass;
    }

    public int getSuperClass() {
        return superClass;
    }

    public void setSuperClass(int superClass) {
        this.superClass = superClass;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public void dumpInterfaces() {
        System.out.println("dumpInterfaces");

        for (int nameIndex : interfaceList) {
            System.out.println("Implements interface:" + constantPool.toString(nameIndex));
        }
    }

    public void dumpMethods() {
        System.out.println("dumpMethods");

        for (MethodInfo methodInfo : methodInfoList) {
            System.out.println("Method:" + constantPool.get(methodInfo.getNameIndex())
                    .toString(constantPool) + " " + constantPool.get(
                            methodInfo.getDescriptorIndex())
                    .toString(constantPool));

            Code code = (Code) methodInfo.getAttributes()
                    .findAttribute(AttributeType.Code);

            List<BytecodeLine> bytecodeLines = code.getBytecodeLines();

            System.out.println("Bytecode:");

            for (BytecodeLine line : bytecodeLines) {
                Instruction instruction = line.getInstruction();
                System.out.println(line.getBci() + " : " + instruction + " " + line.getOperandData()
                        .toString(line, constantPool));
            }

            System.out.println(methodInfo.getAttributes()
                    .toString(constantPool));
        }
    }


    public void dumpFields() {
        System.out.println("dumpFields");

        for (FieldInfo fieldInfo : fieldInfoList) {
            System.out.println("FieldInfo:" + constantPool.get(fieldInfo.getNameIndex())
                    .toString(constantPool) + " " + constantPool.get(
                            fieldInfo.getDescriptorIndex())
                    .toString(constantPool));

            System.out.println(fieldInfo.getAttributes()
                    .toString(constantPool));
        }
    }
}
