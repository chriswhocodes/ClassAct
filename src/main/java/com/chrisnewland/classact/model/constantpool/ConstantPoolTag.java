package com.chrisnewland.classact.model.constantpool;

import java.util.Arrays;
import java.util.Optional;

public enum ConstantPoolTag {
    CONSTANT_Utf8(1),
    CONSTANT_Integer(3),
    CONSTANT_Float(4),
    CONSTANT_Long(5),
    CONSTANT_Double(6),
    CONSTANT_Class(7),
    CONSTANT_String(8),
    CONSTANT_Fieldref(9),
    CONSTANT_Methodref(10),
    CONSTANT_InterfaceMethodref(11),
    CONSTANT_NameAndType(12),
    CONSTANT_MethodHandle(15),
    CONSTANT_MethodType(16),
    CONSTANT_Dynamic(17),
    CONSTANT_InvokeDynamic(18),
    CONSTANT_Module(19),
    CONSTANT_Package(20);

    private final int value;

    ConstantPoolTag(int value) {
        this.value = value;
    }

    public static Optional<ConstantPoolTag> valueOf(int value) {

        System.out.println("lookup:" + value);
        return Arrays.stream(values()).filter(cp -> cp.value == value).findFirst();
    }
}