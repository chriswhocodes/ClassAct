package com.chrisnewland.classact;

import com.chrisnewland.classact.model.constantpool.ConstantPool;

import java.util.Map;

public class SwitchTable extends IntegerIntegerMap implements OperandData {
    @Override
    public String toString(Instruction instruction, ConstantPool constantPool) {
        StringBuilder builder = new StringBuilder();

        int defaultBCI = -1;
        builder.append(" {");
        for (Map.Entry<Integer, Integer> entry : entrySet()) {

            int key = entry.getKey();

            int value = entry.getValue();

            if (key == -1) {
                defaultBCI = value;
            } else {
                builder.append(key).append(" : ").append(value).append("\n");
            }
        }

        if (defaultBCI != -1) {
            builder.append("default").append(" : ").append(defaultBCI).append("\n");
        }

        builder.append("}");

        return builder.toString();
    }
}
