package com.chrisnewland.classact.model.constantpool.entry;

import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.constantpool.ConstantPoolEntry;
import com.chrisnewland.classact.model.constantpool.ConstantPoolType;

public class EntryMethodHandle implements ConstantPoolEntry {
    public enum ReferenceKind {
        REF_getField(1), REF_getStatic(2), REF_putField(3), REF_putStatic(4),
        REF_invokeVirtual(5), REF_invokeStatic(6), REF_invokeSpecial(7), REF_newInvokeSpecial(8), REF_invokeInterface(9);

        private int value;

        ReferenceKind(int value) {
            this.value = value;
        }

        public static ReferenceKind valueOf(int value) {
            for (ReferenceKind referenceKind : values()) {
                if (referenceKind.value == value) {
                    return referenceKind;
                }
            }

            throw new RuntimeException("Unexpected value " + value);
        }
    }

    private final int referenceKind;
    private final int referenceIndex;

    public EntryMethodHandle(int referenceKind, int referenceIndex) {
        this.referenceKind = referenceKind;
        this.referenceIndex = referenceIndex;
    }

    public int getReferenceKind() {
        return referenceKind;
    }

    public int getReferenceIndex() {
        return referenceIndex;
    }

    @Override
    public ConstantPoolType getType() {
        return ConstantPoolType.CONSTANT_MethodHandle;
    }

    @Override
    public String toString(ConstantPool constantPool) {

        ReferenceKind referenceKindValue = ReferenceKind.valueOf(referenceKind);

        ConstantPoolEntry entry = constantPool.get(referenceIndex);

        StringBuilder builder = new StringBuilder();

        builder.append(entry.toString(constantPool));

        return builder.toString();
    }
}