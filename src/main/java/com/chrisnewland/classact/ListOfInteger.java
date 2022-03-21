package com.chrisnewland.classact;

import java.util.ArrayList;
import java.util.Arrays;

public class ListOfInteger extends ArrayList<Integer> implements OperandData {
    @Override
    public String toString(Instruction instruction, String[] tableUTF8) {

        StringBuilder builder = new StringBuilder();

        String decodeExtraBytes = instruction.getDecodeExtraBytes();

        int byteIndex = 0;

        for (int i = 0; i < decodeExtraBytes.length(); i++) {
            char c = decodeExtraBytes.charAt(i);

            builder.append(Arrays.toString(this.toArray())).append(" > ");
            builder.append(c).append(" > ");

            switch (c) {
                case 'L': // local slot (1 byte)
                {
                    int signedByte1 = get(byteIndex);
                    builder.append("local slot ").append(signedByte1);
                    byteIndex++;
                }
                break;
                case 'c': // constant pool (1 byte)
                {
                    int signedByte1 = get(byteIndex);
                    builder.append("constant pool ").append(signedByte1).append(" = ").append(tableUTF8[signedByte1]);
                    byteIndex++;
                }
                break;
                case 'C': // constant pool (2 bytes)
                {
                    int signedByte1 = get(byteIndex);
                    int signedByte2 = get(byteIndex + 1);
                    int index = (signedByte1 << 8) + signedByte2;
                    builder.append("constant pool ").append(index).append(" = ").append(tableUTF8[index]);
                    byteIndex += 2;
                }
                break;
                case 'B': // immediate (1 byte)
                {
                    int signedByte1 = get(byteIndex);
                    builder.append("immediate ").append(signedByte1);
                    byteIndex++;
                }
                break;
                case 'W': // immediate (2 bytes)
                {
                    int signedByte1 = get(byteIndex);
                    int signedByte2 = get(byteIndex + 1);
                    int index = (signedByte1 << 8) + signedByte2;
                    builder.append("immediate ").append(index);
                    byteIndex += 2;
                }
                break;
                case 'i': // BCI (2 bytes)
                {
                    int signedByte1 = get(byteIndex);
                    int signedByte2 = get(byteIndex + 1);
                    int index = (signedByte1 << 8) + signedByte2;
                    builder.append("bci ").append(index);
                    byteIndex += 2;
                }
                break;
                case 'I': // BCI (4 bytes)
                {
                    int signedByte1 = get(byteIndex);
                    int signedByte2 = get(byteIndex + 1);
                    int signedByte3 = get(byteIndex + 2);
                    int signedByte4 = get(byteIndex + 3);
                    int index = (signedByte1 << 24) + (signedByte2 << 16) + (signedByte3 << 8) + signedByte4;
                    builder.append("bci ").append(index);
                    byteIndex += 4;
                }
                break;
                case '-':
                    break;
            }

            builder.append(", ");
        }

        if (builder.length() > 0) {
            builder.delete(builder.length() - 2, builder.length() - 1);
        }

        return builder.toString();
    }
}
