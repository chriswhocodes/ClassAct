package com.chrisnewland.classact.model.constantpool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ConstantPoolType {
    CONSTANT_Utf8(1),
    CONSTANT_Integer(3),
    CONSTANT_Float(4),
    CONSTANT_Long(5),
    CONSTANT_Double(6),
    CONSTANT_Class(7),
    CONSTANT_String(8),
    CONSTANT_FieldRef(9),
    CONSTANT_MethodRef(10),
    CONSTANT_InterfaceMethodRef(11),
    CONSTANT_NameAndType(12),
    CONSTANT_MethodHandle(15),
    CONSTANT_MethodType(16),
    CONSTANT_Dynamic(17),
    CONSTANT_InvokeDynamic(18),
    CONSTANT_Module(19),
    CONSTANT_Package(20);

    private static ConstantPoolType[] lookup = null;

    private final int value;

    ConstantPoolType(int value) {
        this.value = value;
    }

    public static ConstantPoolType valueOf(int value) {

        if (lookup == null) {
            lookup = new ConstantPoolType[20+1];

            for (ConstantPoolType constantPoolType : values()) {
                lookup[constantPoolType.value] = constantPoolType;
            }
        }

        if (value >= lookup.length) {
            throw new RuntimeException("No such ConstantPoolType " + value);
        }

        ConstantPoolType result = lookup[value];

        if (result == null) {
            throw new RuntimeException("No such ConstantPoolType " + value);
        }

        return result;
    }
}