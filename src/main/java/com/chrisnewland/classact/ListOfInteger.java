package com.chrisnewland.classact;

import java.util.ArrayList;
import java.util.Arrays;

public class ListOfInteger extends ArrayList<Integer> implements OperandData {
    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }
}
