package com.chrisnewland.classact;

import java.util.Arrays;

public class NoOperands implements OperandData {
    @Override
    public String toString(Instruction instruction, String[] tableUTF8) {
        return "";
    }
}
