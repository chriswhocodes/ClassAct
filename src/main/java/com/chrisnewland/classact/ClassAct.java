package com.chrisnewland.classact;

import com.chrisnewland.classact.model.*;
import com.chrisnewland.classact.model.attribute.Deprecated;
import com.chrisnewland.classact.model.attribute.Record;
import com.chrisnewland.classact.model.attribute.*;
import com.chrisnewland.classact.model.attribute.annotations.*;
import com.chrisnewland.classact.model.attribute.module.Module;
import com.chrisnewland.classact.model.attribute.module.*;
import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolType;
import com.chrisnewland.classact.model.constantpool.entry.*;
import com.chrisnewland.classact.model.modifier.MethodAccess;
import com.chrisnewland.classact.model.modifier.Modifier;

import java.io.*;

// https://docs.oracle.com/javase/specs/jvms/se20/html/jvms-4.html

// https://docs.oracle.com/javase/specs/jvms/se20/html/jvms-6.html#jvms-6.5

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

            InputStream inputStream = new BufferedInputStream(new FileInputStream(classFile));

            ClassAct classAct = new ClassAct(inputStream);

            ClassFile result = classAct.getClassFile();

            result.dumpInterfaces();

            result.dumpFields();

            result.dumpMethods();

            System.out.println(result.getConstantPool());

            System.out.println(result.getAttributes().toString(result.getConstantPool()));

            long stop = System.nanoTime();

            double elapsed = (stop - start) / 1_000_000d;

            System.out.println("elapsed: " + elapsed + "ms");
        }
    }

    public ClassAct(InputStream inputStream) throws Exception
    {
        classFile = new ClassFile();

        dis = new DataInputStream(inputStream);

        int magicNumber = dis.readInt();

        classFile.setMagicNumber(magicNumber);

        if (magicNumber != 0xCAFEBABE)
        {
            throw new Exception("This does not look like a class file (magic number:" + magicNumber + ")");
        }

        classFile.setMinorVersion(dis.readUnsignedShort());
        classFile.setMajorVersion(dis.readUnsignedShort());

        int constantPoolCount = dis.readUnsignedShort();

        debug("constantPoolCount: " + constantPoolCount);

        classFile.setConstantPool(new ConstantPool(constantPoolCount));

        processConstantPool(constantPoolCount);

        int accessFlags = dis.readUnsignedShort();
        classFile.setAccessFlags(accessFlags);

        debug("accessFlags: " + Integer.toBinaryString(accessFlags));

        int thisClass = dis.readUnsignedShort();

        debug("thisClass: " + thisClass);

        classFile.setThisClass(thisClass);

        int superClass = dis.readUnsignedShort();

        debug("superClass: " + superClass);

        classFile.setSuperClass(superClass);

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

        Attributes attributes = new Attributes(attributesCount);

        classFile.setAttributes(attributes);

        processAttributes(attributes, attributesCount);

        debug("done");
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

            ConstantPoolType tag = ConstantPoolType.valueOf(tagByte);

            debug("Constant[" + currentPoolIndex + "] tagByte " + tagByte + " tag " + tag);

            switch (tag)
            {
                case CONSTANT_Class:
                    processConstantClass(dis);
                    break;
                case CONSTANT_FieldRef:
                    processConstantFieldRef(dis);
                    break;
                case CONSTANT_MethodRef:
                    processConstantMethodRef(dis);
                    break;
                case CONSTANT_InterfaceMethodRef:
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
                    processConstantMethodHandle(dis);
                    break;
                case CONSTANT_MethodType:
                    processConstantMethodType(dis);
                    break;
                case CONSTANT_Dynamic:
                    processConstantDynamic(dis);
                    break;
                case CONSTANT_InvokeDynamic:
                    processConstantInvokeDynamic(dis);
                    break;
                case CONSTANT_Module:
                    processConstantModule(dis);
                    break;
                case CONSTANT_Package:
                    processConstantPackage(dis);
                    break;
            }
        }
    }

    private void processConstantClass(DataInputStream dis) throws IOException
    {
        int nameIndex = dis.readUnsignedShort();

        debug("processConstantClass nameIndex " + nameIndex);

        EntryClass entryClass = new EntryClass(nameIndex);

        classFile.getConstantPool().set(currentPoolIndex, entryClass);
    }

    private void processConstantMethodRef(DataInputStream dis) throws IOException
    {
        int classIndex = dis.readUnsignedShort();

        debug("processConstantMethodRef classIndex " + classIndex);

        int nameAndTypeIndex = dis.readUnsignedShort();

        debug("processConstantMethodRef nameAndTypeIndex " + nameAndTypeIndex);

        EntryMethodRef entryMethodRef = new EntryMethodRef(classIndex, nameAndTypeIndex);

        classFile.getConstantPool().set(currentPoolIndex, entryMethodRef);
    }

    private void processConstantMethodHandle(DataInputStream dis) throws IOException
    {
        int referenceKind = dis.readUnsignedByte();

        debug("processConstantMethodHandle referenceKind " + referenceKind);

        int referenceIndex = dis.readUnsignedShort();

        debug("processConstantMethodRef referenceIndex " + referenceIndex);

        EntryMethodHandle entryMethodHandle = new EntryMethodHandle(referenceKind, referenceIndex);

        classFile.getConstantPool().set(currentPoolIndex, entryMethodHandle);
    }

    private void processConstantMethodType(DataInputStream dis) throws IOException
    {
        int descriptorIndex = dis.readUnsignedShort();

        debug("processConstantMethodType descriptorIndex " + descriptorIndex);

        EntryMethodType entryMethodType = new EntryMethodType(descriptorIndex);

        classFile.getConstantPool().set(currentPoolIndex, entryMethodType);
    }

    private void processConstantDynamic(DataInputStream dis) throws IOException
    {
        int bootstrapMethodAttrIndex = dis.readUnsignedShort();

        debug("processConstantDynamic bootstrapMethodAttrIndex " + bootstrapMethodAttrIndex);

        int nameAndTypeIndex = dis.readUnsignedShort();

        debug("processConstantDynamic nameAndTypeIndex " + nameAndTypeIndex);

        EntryDynamic entryDynamic = new EntryDynamic(bootstrapMethodAttrIndex, nameAndTypeIndex);

        classFile.getConstantPool().set(currentPoolIndex, entryDynamic);
    }

    private void processConstantInvokeDynamic(DataInputStream dis) throws IOException
    {
        int bootstrapMethodAttrIndex = dis.readUnsignedShort();

        debug("processConstantInvokeDynamic bootstrapMethodAttrIndex " + bootstrapMethodAttrIndex);

        int nameAndTypeIndex = dis.readUnsignedShort();

        debug("processConstantInvokeDynamic nameAndTypeIndex " + nameAndTypeIndex);

        EntryInvokeDynamic entryInvokeDynamic = new EntryInvokeDynamic(bootstrapMethodAttrIndex, nameAndTypeIndex);

        classFile.getConstantPool().set(currentPoolIndex, entryInvokeDynamic);
    }

    private void processConstantModule(DataInputStream dis) throws IOException
    {
        int nameIndex = dis.readUnsignedShort();

        debug("processConstantModule nameIndex " + nameIndex);

        EntryModule entryModule = new EntryModule(nameIndex);

        classFile.getConstantPool().set(currentPoolIndex, entryModule);
    }

    private void processConstantPackage(DataInputStream dis) throws IOException
    {
        int nameIndex = dis.readUnsignedShort();

        debug("processConstantPackage nameIndex " + nameIndex);

        EntryPackage entryPackage = new EntryPackage(nameIndex);

        classFile.getConstantPool().set(currentPoolIndex, entryPackage);
    }

    private void processConstantInterfaceMethodRef(DataInputStream dis) throws IOException
    {
        int classIndex = dis.readUnsignedShort();

        debug("processConstantInterfaceMethodRef classIndex " + classIndex);

        int nameAndTypeIndex = dis.readUnsignedShort();

        debug("processConstantInterfaceMethodRef nameAndTypeIndex " + nameAndTypeIndex);

        EntryInterfaceMethodRef entryInterfaceMethodRef = new EntryInterfaceMethodRef(classIndex, nameAndTypeIndex);

        classFile.getConstantPool().set(currentPoolIndex, entryInterfaceMethodRef);
    }

    private void processConstantFieldRef(DataInputStream dis) throws IOException
    {
        int classIndex = dis.readUnsignedShort();

        debug("processConstantFieldRef classIndex " + classIndex);

        int nameAndTypeIndex = dis.readUnsignedShort();

        debug("processConstantFieldRef nameAndTypeIndex " + nameAndTypeIndex);

        EntryFieldRef entryFieldRef = new EntryFieldRef(classIndex, nameAndTypeIndex);

        classFile.getConstantPool().set(currentPoolIndex, entryFieldRef);
    }

    private void processConstantString(DataInputStream dis) throws IOException
    {
        int stringIndex = dis.readUnsignedShort();

        debug("processConstantString stringIndex " + stringIndex);

        EntryString entryString = new EntryString(stringIndex);

        classFile.getConstantPool().set(currentPoolIndex, entryString);
    }

    private void processConstantInteger(DataInputStream dis) throws IOException
    {
        int value = dis.readInt();

        debug("processConstantInteger bytes " + value);

        EntryInteger entryInteger = new EntryInteger(value);

        classFile.getConstantPool().set(currentPoolIndex, entryInteger);
    }

    private void processConstantFloat(DataInputStream dis) throws IOException
    {
        int bits = dis.readInt();

        debug("processConstantFloat #" + currentPoolIndex + "  = " + bits);

        EntryFloat entryFloat = new EntryFloat(bits);

        classFile.getConstantPool().set(currentPoolIndex, entryFloat);
    }

    private void processConstantLong(DataInputStream dis) throws IOException
    {
        int high_bytes = dis.readInt();

        debug("processConstantLong high_bytes " + high_bytes);

        int low_bytes = dis.readInt();

        debug("processConstantLong low_bytes " + low_bytes);

        long value = ((long) high_bytes << 32) + low_bytes;

        EntryLong entryLong = new EntryLong(value);

        classFile.getConstantPool().set(currentPoolIndex, entryLong);
    }

    private void processConstantDouble(DataInputStream dis) throws IOException
    {
        int high_bytes = dis.readInt();

        debug("processConstantDouble high_bytes " + high_bytes);

        int low_bytes = dis.readInt();

        debug("processConstantDouble low_bytes " + low_bytes);

        long bits = ((long) high_bytes << 32) + low_bytes;

        EntryDouble entryDouble = new EntryDouble(bits);

        classFile.getConstantPool().set(currentPoolIndex, entryDouble);
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

        classFile.getConstantPool().set(currentPoolIndex, entryUTF8);
    }

    private void processConstantNameAndType(DataInputStream dis) throws IOException
    {
        int nameIndex = dis.readUnsignedShort();

        debug("processConstantNameAndType nameIndex " + nameIndex);

        int descriptorIndex = dis.readUnsignedShort();

        debug("processConstantNameAndType descriptorIndex " + descriptorIndex);

        EntryNameAndType entryNameAndType = new EntryNameAndType(nameIndex, descriptorIndex);

        classFile.getConstantPool().set(currentPoolIndex, entryNameAndType);
    }

    private void processInterfaces(int interfacesCount) throws IOException
    {
        for (int i = 0; i < interfacesCount; i++)
        {
            int nameIndex = dis.readUnsignedShort();

            debug("processInterfaces[" + i + "] nameIndex " + nameIndex);

            classFile.addInterface(nameIndex);
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

            Attributes attributes = new Attributes(attributesCount);

            FieldInfo fieldInfo = new FieldInfo(accessFlags, nameIndex, descriptorIndex);

            fieldInfo.setAttributes(attributes);

            processAttributes(attributes, attributesCount);

            classFile.addFieldInfo(fieldInfo);
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

            info("METHOD: " + Modifier.getAccessString(MethodAccess.values(), accessFlags) + " " + classFile.getConstantPool().toString(nameIndex) + " " + classFile.getConstantPool().toString(descriptorIndex));

            MethodInfo methodInfo = new MethodInfo(accessFlags, nameIndex, descriptorIndex);

            Attributes attributes = new Attributes(attributesCount);

            methodInfo.setAttributes(attributes);

            classFile.addMethodInfo(methodInfo);

            processAttributes(attributes, attributesCount);
        }
    }

    private void processAttributes(Attributes attributes, int attributesCount) throws IOException
    {
        for (int i = 0; i < attributesCount; i++)
        {
            int attributeNameIndex = dis.readUnsignedShort();

            debug("processAttributes[" + i + "] attributeNameIndex " + attributeNameIndex);

            int attributeLength = dis.readInt();

            debug("processAttributes[" + i + "] attributeLength " + attributeLength);

            String attributeName = classFile.getConstantPool().toString(attributeNameIndex);

            debug("processAttributes attributeName: " + attributeName);

            attributes.set(i, processSingleAttribute(attributeName, attributeLength));
        }
    }

    private Attribute processSingleAttribute(String attributeName, int attributeLength) throws IOException
    {
        debug("processSingleAttribute " + attributeName + " attributeLength " + attributeLength);

        AttributeType attributeType = AttributeType.valueOf(attributeName);

        switch (attributeType)
        {
            case Code:
                return processAttributeCode(dis);

            case LineNumberTable:
                return processAttributeLineNumberTable(dis);

            case InnerClasses:
                return processInnerClasses(dis);

            case Signature:
                return processSignature(dis);

            case Synthetic:
                return processSynthetic(dis);

            case ConstantValue:
                return processConstantValue(dis);

            case SourceFile:
                return processSourceFile(dis);

            case Exceptions:
                return processExceptions(dis);

            case EnclosingMethod:
                return processEnclosingMethod(dis);

            case LocalVariableTable:
                return processLocalVariableTable(dis);

            case LocalVariableTypeTable:
                return processLocalVariableTypeTable(dis);

            case SourceDebugExtension:
                return processSourceDebugExtension(attributeLength, dis);

            case Deprecated:
                return processDeprecated(dis);

            case BootstrapMethods:
                return processBootstrapMethods(dis);

            case RuntimeVisibleAnnotations:
                return processRuntimeVisibleAnnotations(dis);

            case RuntimeInvisibleAnnotations:
                return processRuntimeInvisibleAnnotations(dis);

            case RuntimeVisibleParameterAnnotations:
                return processRuntimeVisibleParameterAnnotations(dis);

            case RuntimeInvisibleParameterAnnotations:
                return processRuntimeInvisibleParameterAnnotations(dis);

            case AnnotationDefault:
                return processAnnotationDefault(dis);

            case MethodParameters:
                return processMethodParameters(dis);

            case Module:
                return processModule(dis);

            case ModulePackages:
                return processModulePackages(dis);

            case ModuleMainClass:
                return processModuleMainClass(dis);

            case NestHost:
                return processNestHost(dis);

            case NestMembers:
                return processNestMembers(dis);

            case Record:
                return processRecord(dis);

            case PermittedSubclasses:
                return processPermittedSubclasses(dis);

            // skips the correct number of bytes
            default:
                System.out.println("unhandled: " + attributeType);
                byte[] attributeInfo = new byte[attributeLength];
                dis.read(attributeInfo);
                return null;

            //  TODO implement these!
            //	StackMapTable: // used for bytecode verification
            //	RuntimeVisibleTypeAnnotations, // woo, complex!
            //	RuntimeInvisibleTypeAnnotations, // woo, complex!
        }
    }

    private Code processAttributeCode(DataInputStream dis) throws IOException
    {
        Code code = new Code();

        int max_stack = dis.readUnsignedShort();

        code.setMaxStack(max_stack);

        debug("processAttributeCode max_stack: " + max_stack);

        int max_locals = dis.readUnsignedShort();

        code.setMaxLocals(max_locals);

        debug("processAttributeCode max_locals: " + max_locals);

        int code_length = dis.readInt();

        debug("processAttributeCode code_length: " + code_length);

        processCode(code, code_length);

        int exception_table_length = dis.readUnsignedShort();

        debug("processAttributeCode exception_table_length: " + exception_table_length);

        ExceptionTable exceptionTable = processCodeExceptionTable(exception_table_length);

        code.setExceptionTable(exceptionTable);

        int attributes_count = dis.readUnsignedShort();

        debug("processAttributeCode attributes_count: " + attributes_count);

        Attributes attributes = new Attributes(attributes_count);
        code.setAttributes(attributes);

        processAttributes(attributes, attributes_count);

        return code;
    }

    private void processCode(Code code, int codeLength) throws IOException
    {
        currentMethodBCI = 0;

        while (currentMethodBCI < codeLength)
        {
            int opcode = dis.readUnsignedByte();

            Instruction instruction = Instruction.forOpcode(opcode).get();

            info("processCode opcode at BCI [" + currentMethodBCI + "] " + opcode + " (" + instruction + ")");

            int extraBytes = instruction.getExtraBytes();

            if (extraBytes > 0)
            {
                ListOfInteger operandData = new ListOfInteger();

                BytecodeLine bytecodeLine = new BytecodeLine(currentMethodBCI, instruction, operandData);

                code.addBytecodeLine(bytecodeLine);

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
                processVariableLengthInstruction(code, instruction);
            }
            else
            {
                BytecodeLine bytecodeLine = new BytecodeLine(currentMethodBCI, instruction, new NoOperands());

                code.addBytecodeLine(bytecodeLine);
            }

            currentMethodBCI++;
        }
    }

    private void processVariableLengthInstruction(Code code, Instruction instruction) throws IOException
    {
        switch (instruction)
        {
            case LOOKUPSWITCH:
            {
                SwitchTable operandData = new SwitchTable();
                BytecodeLine bytecodeLine = new BytecodeLine(currentMethodBCI, instruction, operandData);

                code.addBytecodeLine(bytecodeLine);

                processLookupSwitch(dis, operandData);
            }
            break;
            case TABLESWITCH:
            {
                SwitchTable operandData = new SwitchTable();
                BytecodeLine bytecodeLine = new BytecodeLine(currentMethodBCI, instruction, operandData);

                code.addBytecodeLine(bytecodeLine);

                processTableSwitch(dis, operandData);
            }
            break;
            case WIDE:
            {
                ListOfInteger operandData = new ListOfInteger();
                BytecodeLine bytecodeLine = new BytecodeLine(currentMethodBCI, instruction, operandData);

                code.addBytecodeLine(bytecodeLine);

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

        debug("processLookupSwitch defaultBytes: " + defaultByte1 + ", " + defaultByte2 + ", " + defaultByte3 + ", " + defaultByte4 + " base: " + baseBCI + " = " + defaultValue);

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

            debug("processLookupSwitch offset      : " + offsetByte1 + ", " + offsetByte2 + ", " + offsetByte3 + ", " + offsetByte4 + " base: " + baseBCI + " = " + offset);

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

        debug("processTableSwitch defaultBytes: " + defaultByte1 + ", " + defaultByte2 + ", " + defaultByte3 + ", " + defaultByte4 + " base: " + baseBCI + " = " + defaultValue);

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

        debug("processTableSwitch highBytes   : " + highByte1 + ", " + highByte2 + ", " + highByte3 + ", " + highByte4 + " = " + high);

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

            debug("processTableSwitch offset      : " + offsetByte1 + ", " + offsetByte2 + ", " + offsetByte3 + ", " + offsetByte4 + " base:" + baseBCI + " = " + offset);

            operandData.put(low + off, offset);
        }
    }

    private ExceptionTable processCodeExceptionTable(int exceptionTableLength) throws IOException
    {
        ExceptionTable exceptionTable = new ExceptionTable(exceptionTableLength);

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

            exceptionTable.set(i, new ExceptionTableEntry(start_pc, end_pc, handler_pc, catch_type));
        }

        return exceptionTable;
    }

    private LineNumberTable processAttributeLineNumberTable(DataInputStream dis) throws IOException
    {
        int line_number_table_length = dis.readUnsignedShort();

        debug("processAttributeLineNumberTable line_number_table_length: " + line_number_table_length);

        LineNumberTable lineNumberTable = new LineNumberTable();

        for (int l = 0; l < line_number_table_length; l++)
        {
            int start_pc = dis.readUnsignedShort();
            int line_number = dis.readUnsignedShort();

            lineNumberTable.put(start_pc, line_number);

            debug("processAttributeLineNumberTable mapping: " + start_pc + "->" + line_number);
        }

        return lineNumberTable;
    }

    private LocalVariableTable processLocalVariableTable(DataInputStream dis) throws IOException
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

        return localVariableTable;
    }

    private LocalVariableTypeTable processLocalVariableTypeTable(DataInputStream dis) throws IOException
    {
        int local_variable_type_table_length = dis.readUnsignedShort();

        debug("processLocalVariableTypeTable local_variable_table_length: " + local_variable_type_table_length);

        LocalVariableTypeTable localVariableTypeTable = new LocalVariableTypeTable(local_variable_type_table_length);

        for (int l = 0; l < local_variable_type_table_length; l++)
        {
            int start_pc = dis.readUnsignedShort();
            int length = dis.readUnsignedShort();
            int nameIndex = dis.readUnsignedShort();
            int signatureIndex = dis.readUnsignedShort();
            int index = dis.readUnsignedShort();

            localVariableTypeTable.setEntry(l, new LocalVariableTypeTableEntry(start_pc, length, nameIndex, signatureIndex, index));
        }

        return localVariableTypeTable;
    }

    private SourceDebugExtension processSourceDebugExtension(int attributeLength, DataInputStream dis) throws IOException
    {
        SourceDebugExtension sourceDebugExtension = new SourceDebugExtension(attributeLength);

        dis.read(sourceDebugExtension.getDebugExtension());

        return sourceDebugExtension;
    }

    private Deprecated processDeprecated(DataInputStream dis)
    {
        return new Deprecated();
    }

    private BootstrapMethods processBootstrapMethods(DataInputStream dis) throws IOException
    {

        int numBootstrapMethods = dis.readUnsignedShort();

        BootstrapMethods bootstrapMethods = new BootstrapMethods(numBootstrapMethods);

        for (int i = 0; i < numBootstrapMethods; i++)
        {
            int bootstrapMethodRef = dis.readUnsignedShort();

            int numBootstrapArguments = dis.readUnsignedShort();

            BootstrapMethodsEntry bootstrapMethodsEntry = new BootstrapMethodsEntry(bootstrapMethodRef, numBootstrapArguments);

            for (int j = 0; j < numBootstrapArguments; j++)
            {
                int argumentIndex = dis.readUnsignedShort();

                bootstrapMethodsEntry.setArgument(j, argumentIndex);
            }

            bootstrapMethods.set(i, bootstrapMethodsEntry);
        }

        return bootstrapMethods;
    }

    private RuntimeVisibleAnnotations processRuntimeVisibleAnnotations(DataInputStream dis) throws IOException
    {
        int numAnnotations = dis.readUnsignedShort();

        RuntimeVisibleAnnotations runtimeVisibleAnnotations = new RuntimeVisibleAnnotations(numAnnotations);

        for (int i = 0; i < numAnnotations; i++)
        {
            runtimeVisibleAnnotations.set(i, processAnnotation(dis));
        }

        return runtimeVisibleAnnotations;
    }

    private RuntimeInvisibleAnnotations processRuntimeInvisibleAnnotations(DataInputStream dis) throws IOException
    {
        int numAnnotations = dis.readUnsignedShort();

        RuntimeInvisibleAnnotations runtimeInvisibleAnnotations = new RuntimeInvisibleAnnotations(numAnnotations);

        for (int i = 0; i < numAnnotations; i++)
        {
            runtimeInvisibleAnnotations.set(i, processAnnotation(dis));
        }

        return runtimeInvisibleAnnotations;
    }

    private Annotation processAnnotation(DataInputStream dis) throws IOException
    {
        int typeIndex = dis.readUnsignedShort();

        int numElementValues = dis.readUnsignedShort();

        Annotation annotation = new Annotation(typeIndex, numElementValues);

        for (int i = 0; i < numElementValues; i++)
        {
            int elementNameIndex = dis.readUnsignedShort();

            ElementValue elementValue = processElementValue(dis);

            ElementValuePair elementValuePair = new ElementValuePair(elementNameIndex, elementValue);

            annotation.setElementValuePair(i, elementValuePair);
        }

        return annotation;
    }

    private ElementValue processElementValue(DataInputStream dis) throws IOException
    {
        int tag = dis.readUnsignedByte();

        switch (tag)
        {
            case 'B'://    B 	byte 	const_value_index 	CONSTANT_Integer
            case 'C'://    C 	char 	const_value_index 	CONSTANT_Integer
            case 'D'://    D 	double 	const_value_index 	CONSTANT_Double
            case 'F'://    F 	float 	const_value_index 	CONSTANT_Float
            case 'I'://    I 	int 	const_value_index 	CONSTANT_Integer
            case 'J'://    J 	long 	const_value_index 	CONSTANT_Long
            case 'S'://    S 	short 	const_value_index 	CONSTANT_Integer
            case 'Z'://    Z 	boolean 	const_value_index 	CONSTANT_Integer
            case 's'://    s 	String 	const_value_index 	CONSTANT_Utf8
                return processElementValueConstant(dis);

            case 'e'://    e 	Enum class 	enum_const_value 	Not applicable
                return processElementValueEnum(dis);

            case 'c'://    c 	Class 	class_info_index 	Not applicable
                return processElementValueClass(dis);

            case '@'://    @ 	Annotation interface 	annotation_value 	Not applicable
                return processAnnotation(dis);

            case '['://    [ 	Array type 	array_value 	Not applicable
                return processElementValueArray(dis);
        }

        throw new RuntimeException("Unexpected tag: " + tag);
    }

    private ElementValueConstant processElementValueConstant(DataInputStream dis) throws IOException
    {
        return new ElementValueConstant(dis.readUnsignedShort());
    }

    private ElementValueEnum processElementValueEnum(DataInputStream dis) throws IOException
    {
        return new ElementValueEnum(dis.readUnsignedShort(), dis.readUnsignedShort());
    }

    private ElementValueClass processElementValueClass(DataInputStream dis) throws IOException
    {
        return new ElementValueClass(dis.readUnsignedShort());
    }

    private ElementValueArray processElementValueArray(DataInputStream dis) throws IOException
    {
        int numValues = dis.readUnsignedShort();

        ElementValueArray elementValueArray = new ElementValueArray(numValues);

        for (int i = 0; i < numValues; i++)
        {
            elementValueArray.set(i, processElementValue(dis));
        }

        return elementValueArray;
    }

    private AnnotationDefault processAnnotationDefault(DataInputStream dis) throws IOException
    {
        ElementValue elementValue = processElementValue(dis);

        return new AnnotationDefault(elementValue);
    }

    private Exceptions processExceptions(DataInputStream dis) throws IOException
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

        return exceptions;
    }

    private MethodParameters processMethodParameters(DataInputStream dis) throws IOException
    {
        int parameters_count = dis.readUnsignedByte();

        debug("processMethodParameters parameters_count: " + parameters_count);

        MethodParameters methodParameters = new MethodParameters(parameters_count);

        for (int i = 0; i < parameters_count; i++)
        {

            int nameIndex = dis.readUnsignedShort();
            int accessFlags = dis.readUnsignedShort();

            MethodParametersEntry methodParametersEntry = new MethodParametersEntry(nameIndex, accessFlags);

            methodParameters.set(i, methodParametersEntry);
        }

        return methodParameters;
    }

    private Module processModule(DataInputStream dis) throws IOException
    {
        int module_name_index = dis.readUnsignedShort();
        int module_flags = dis.readUnsignedShort();
        int module_version_index = dis.readUnsignedShort();

        int requires_count = dis.readUnsignedShort();

        Requires[] requires = new Requires[requires_count];

        for (int r = 0; r < requires_count; r++)
        {
            int requires_index = dis.readUnsignedShort();
            int requires_flags = dis.readUnsignedShort();
            int requires_version_index = dis.readUnsignedShort();

            requires[r] = new Requires(requires_index, requires_flags, requires_version_index);
        }

        int exports_count = dis.readUnsignedShort();

        Exports[] exports = new Exports[exports_count];

        for (int e = 0; e < exports_count; e++)
        {
            int exports_index = dis.readUnsignedShort();
            int exports_flags = dis.readUnsignedShort();
            int exports_to_count = dis.readUnsignedShort();

            int[] exports_to_index = new int[exports_to_count];

            for (int ei = 0; ei < exports_to_count; ei++)
            {
                exports_to_index[ei] = dis.readUnsignedShort();
            }

            exports[e] = new Exports(exports_index, exports_flags, exports_to_index);
        }

        int opens_count = dis.readUnsignedShort();

        Opens[] opens = new Opens[opens_count];

        for (int o = 0; o < opens_count; o++)
        {
            int opens_index = dis.readUnsignedShort();
            int opens_flags = dis.readUnsignedShort();
            int opens_to_count = dis.readUnsignedShort();
            int[] opens_to_index = new int[opens_to_count];

            for (int oi = 0; oi < opens_to_count; oi++)
            {
                opens_to_index[oi] = dis.readUnsignedShort();
            }

            opens[o] = new Opens(opens_index, opens_flags, opens_to_index);
        }

        int uses_count = dis.readUnsignedShort();
        int[] uses_index = new int[uses_count];

        for (int u = 0; u < uses_count; u++)
        {
            uses_index[u] = dis.readUnsignedShort();
        }

        int provides_count = dis.readUnsignedShort();

        Provides[] provides = new Provides[provides_count];

        for (int p = 0; p < provides_count; p++)
        {
            int provides_index = dis.readUnsignedShort();
            int provides_with_count = dis.readUnsignedShort();
            int[] provides_with_index = new int[provides_with_count];

            for (int pi = 0; pi < provides_with_count; pi++)
            {
                provides_with_index[pi] = dis.readUnsignedShort();
            }

            provides[p] = new Provides(provides_index, provides_with_index);
        }

        return new Module(module_name_index, module_flags, module_version_index, requires, exports, uses_index, provides);
    }

    private ModulePackages processModulePackages(DataInputStream dis) throws IOException
    {
        int package_count = dis.readUnsignedShort();

        int[] package_index = new int[package_count];

        for (int i = 0; i < package_count; i++)
        {
            package_index[i] = dis.readUnsignedShort();
        }

        return new ModulePackages(package_index);
    }

    private ModuleMainClass processModuleMainClass(DataInputStream dis) throws IOException
    {
        int main_class_index = dis.readUnsignedShort();

        return new ModuleMainClass(main_class_index);
    }

    private NestHost processNestHost(DataInputStream dis) throws IOException
    {
        int host_class_index = dis.readUnsignedShort();

        return new NestHost(host_class_index);
    }

    private NestMembers processNestMembers(DataInputStream dis) throws IOException
    {
        int numClasses = dis.readUnsignedShort();

        int[] classes = new int[numClasses];

        for (int i = 0; i < numClasses; i++)
        {
            classes[i] = dis.readUnsignedShort();
        }

        return new NestMembers(classes);
    }

    private Record processRecord(DataInputStream dis) throws IOException
    {
        int numComponents = dis.readUnsignedShort();

        Record record = new Record(numComponents);

        for (int i = 0; i < numComponents; i++)
        {
            int nameIndex = dis.readUnsignedShort();
            int descriptorIndex = dis.readUnsignedShort();

            int attributesCount = dis.readUnsignedShort();

            Attributes attributes = new Attributes(attributesCount);

            processAttributes(attributes, attributesCount);

            record.set(i, new RecordComponentInfo(nameIndex, descriptorIndex, attributes));
        }

        return record;
    }

    private PermittedSubclasses processPermittedSubclasses(DataInputStream dis) throws IOException
    {
        int numClasses = dis.readUnsignedShort();

        int[] classes = new int[numClasses];

        for (int i = 0; i < numClasses; i++)
        {
            classes[i] = dis.readUnsignedShort();
        }

        return new PermittedSubclasses(classes);
    }

    private RuntimeVisibleParameterAnnotations processRuntimeVisibleParameterAnnotations(DataInputStream dis) throws IOException
    {
        int numParameters = dis.readUnsignedByte();

        RuntimeVisibleParameterAnnotations runtimeVisibleParameterAnnotations = new RuntimeVisibleParameterAnnotations(numParameters);

        for (int i = 0; i < numParameters; i++)
        {
            runtimeVisibleParameterAnnotations.set(i, processParameterAnnotation(dis));
        }

        return runtimeVisibleParameterAnnotations;
    }

    private RuntimeInvisibleParameterAnnotations processRuntimeInvisibleParameterAnnotations(DataInputStream dis) throws IOException
    {
        int numParameters = dis.readUnsignedByte();

        RuntimeInvisibleParameterAnnotations runtimeInvisibleParameterAnnotations = new RuntimeInvisibleParameterAnnotations(numParameters);

        for (int i = 0; i < numParameters; i++)
        {
            runtimeInvisibleParameterAnnotations.set(i, processParameterAnnotation(dis));
        }

        return runtimeInvisibleParameterAnnotations;
    }

    private ParameterAnnotation processParameterAnnotation(DataInputStream dis) throws IOException
    {
        int numAnnotations = dis.readUnsignedShort();

        ParameterAnnotation parameterAnnotation = new ParameterAnnotation(numAnnotations);

        for (int i = 0; i < numAnnotations; i++)
        {
            Annotation annotation = processAnnotation(dis);

            parameterAnnotation.set(i, annotation);
        }

        return parameterAnnotation;
    }

    private InnerClasses processInnerClasses(DataInputStream dis) throws IOException
    {
        int number_of_classes = dis.readUnsignedShort();

        debug("processInnerClasses number_of_classes: " + number_of_classes);

        InnerClasses innerClasses = new InnerClasses(number_of_classes);

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

            innerClasses.set(inner, new InnerClassEntry(inner_class_info_index, outer_class_info_index, inner_name_index, inner_class_access_flags));
        }

        return innerClasses;
    }

    private Signature processSignature(DataInputStream dis) throws IOException
    {
        int signature_index = dis.readUnsignedShort();

        debug("processSignature signature_index: " + signature_index);

        return new Signature(signature_index);
    }

    private Synthetic processSynthetic(DataInputStream dis) throws IOException
    {
        debug("current item is synthetic");

        return new Synthetic();
    }

    private ConstantValue processConstantValue(DataInputStream dis) throws IOException
    {
        int constantvalue_index = dis.readUnsignedShort();

        debug("processConstantValue constantvalue_index: " + constantvalue_index);

        return new ConstantValue(constantvalue_index);
    }

    private SourceFile processSourceFile(DataInputStream dis) throws IOException
    {
        int sourcefile_index = dis.readUnsignedShort();

        debug("processSourceFile sourcefile_index: " + sourcefile_index);

        return new SourceFile(sourcefile_index);
    }

    private EnclosingMethod processEnclosingMethod(DataInputStream dis) throws IOException
    {
        int class_index = dis.readUnsignedShort();
        int method_index = dis.readUnsignedShort();
        debug("processEnclosingMethod class_index: " + class_index + " method_index:" + method_index);

        return new EnclosingMethod(class_index, method_index);
    }
}