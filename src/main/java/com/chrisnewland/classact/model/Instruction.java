package com.chrisnewland.classact.model;

import java.util.Arrays;
import java.util.Optional;

public enum Instruction
{
    AALOAD(50),
    AASTORE(83),
    ACONST_NULL(1),
    ALOAD(25, 1, "L"),
    ALOAD_0(42, "aload_n"),
    ALOAD_1(43, "aload_n"),
    ALOAD_2(44, "aload_n"),
    ALOAD_3(45, "aload_n"),
    ANEWARRAY(189, 2, "C"),
    ARETURN(176),
    ARRAYLENGTH(190),
    ASTORE(58, 1, "L"),
    ASTORE_0(75, "astore_n"),
    ASTORE_1(76, "astore_n"),
    ASTORE_2(77, "astore_n"),
    ASTORE_3(78, "astore_n"),
    ATHROW(191),
    BALOAD(51),
    BASTORE(84),
    BIPUSH(16, 1, "B"),
    CALOAD(52),
    CASTORE(85),
    CHECKCAST(192, 2, "C"),
    D2F(144),
    D2I(142),
    D2L(143),
    DADD(99),
    DALOAD(49),
    DASTORE(82),
    DCMPG(152),
    DCMPL(151),
    DCONST_0(14, "dconst_d"),
    DCONST_1(15, "dconst_d"),
    DDIV(111),
    DLOAD(24, 1, "L"),
    DLOAD_0(38, "dload_n"),
    DLOAD_1(39, "dload_n"),
    DLOAD_2(40, "dload_n"),
    DLOAD_3(41, "dload_n"),
    DMUL(107),
    DNEG(119),
    DREM(115),
    DRETURN(175),
    DSTORE(57, 1, "L"),
    DSTORE_0(71, "dstore_n"),
    DSTORE_1(72, "dstore_n"),
    DSTORE_2(73, "dstore_n"),
    DSTORE_3(74, "dstore_n"),
    DSUB(103),
    DUP(89),
    DUP_X1(90),
    DUP_X2(91),
    DUP2(92),
    DUP2_X1(93),
    DUP2_X2(94),
    F2D(141),
    F2I(139),
    F2L(140),
    FADD(98),
    FALOAD(48),
    FASTORE(81),
    FCMPG(150),
    FCMPL(149),
    FCONST_0(11, "fconst_f"),
    FCONST_1(12, "fconst_f"),
    FCONST_2(13, "fconst_f"),
    FDIV(110),
    FLOAD(23, 1, "L"),
    FLOAD_0(34, "fload_n"),
    FLOAD_1(35, "fload_n"),
    FLOAD_2(36, "fload_n"),
    FLOAD_3(37, "fload_n"),
    FMUL(106),
    FNEG(118),
    FREM(114),
    FRETURN(174),
    FSTORE(56, 1, "L"),
    FSTORE_0(67, "fstore_n"),
    FSTORE_1(68, "fstore_n"),
    FSTORE_2(69, "fstore_n"),
    FSTORE_3(70, "fstore_n"),
    FSUB(102),
    GETFIELD(180, 2, "C"),
    GETSTATIC(178, 2, "C"),
    GOTO(167, 2, "i"),
    GOTO_W(200, 4, "I"),
    I2B(145),
    I2C(146),
    I2D(135),
    I2F(134),
    I2L(133),
    I2S(147),
    IADD(96),
    IALOAD(46),
    IAND(126),
    IASTORE(79),
    ICONST_M1(2, "iconst_i"),
    ICONST_0(3, "iconst_i"),
    ICONST_1(4, "iconst_i"),
    ICONST_2(5, "iconst_i"),
    ICONST_3(6, "iconst_i"),
    ICONST_4(7, "iconst_i"),
    ICONST_5(8, "iconst_i"),
    IDIV(108),
    IF_ACMPEQ(165, 2, "i", "if_acmp_cond"),
    IF_ACMPNE(166, 2, "i", "if_acmp_cond"),
    IF_ICMPEQ(159, 2, "i", "if_icmp_cond"),
    IF_ICMPNE(160, 2, "i", "if_icmp_cond"),
    IF_ICMPLT(161, 2, "i", "if_icmp_cond"),
    IF_ICMPGE(162, 2, "i", "if_icmp_cond"),
    IF_ICMPGT(163, 2, "i", "if_icmp_cond"),
    IF_ICMPLE(164, 2, "i", "if_icmp_cond"),
    IFEQ(153, 2, "i", "if_cond"),
    IFNE(154, 2, "i", "if_cond"),
    IFLT(155, 2, "i", "if_cond"),
    IFGE(156, 2, "i", "if_cond"),
    IFGT(157, 2, "i", "if_cond"),
    IFLE(158, 2, "i", "if_cond"),
    IFNONNULL(199, 2, "i"),
    IFNULL(198, 2, "i"),
    IINC(132, 2, "LB"),
    ILOAD(21, 1, "L"),
    ILOAD_0(26, "iload_n"),
    ILOAD_1(27, "iload_n"),
    ILOAD_2(28, "iload_n"),
    ILOAD_3(29, "iload_n"),
    IMUL(104),
    INEG(116),
    INSTANCEOF(193, 2, "C"),
    INVOKEDYNAMIC(186, 2, "C"),
    INVOKEINTERFACE(185, 4, "CB0"),
    INVOKESPECIAL(183, 2, "C"),
    INVOKESTATIC(184, 2, "C"),
    INVOKEVIRTUAL(182, 2, "C"),
    IOR(128),
    IREM(112),
    IRETURN(172),
    ISHL(120),
    ISHR(122),
    ISTORE(54, 1, "L"),
    ISTORE_0(59, "istore_n"),
    ISTORE_1(60, "istore_n"),
    ISTORE_2(61, "istore_n"),
    ISTORE_3(62, "istore_n"),
    ISUB(100),
    IUSHR(124),
    IXOR(130),
    JSR(168, 2, "i"),
    JSR_W(201, 4, "I"),
    L2D(138),
    L2F(137),
    L2I(136),
    LADD(97),
    LALOAD(47),
    LAND(127),
    LASTORE(80),
    LCMP(148),
    LCONST_0(9, "lconst_l"),
    LCONST_1(10, "lconst_l"),
    LDC(18, 1, "c"),
    LDC_W(19, 2, "C"),
    LDC2_W(20, 2, "C"),
    LDIV(109),
    LLOAD(22, 1, "L"),
    LLOAD_0(30, "lload_n"),
    LLOAD_1(31, "lload_n"),
    LLOAD_2(32, "lload_n"),
    LLOAD_3(33, "lload_n"),
    LMUL(105),
    LNEG(117),
    LOOKUPSWITCH(171, -1, "-"), // 8+
    LOR(129),
    LREM(113),
    LRETURN(173),
    LSHL(121),
    LSHR(123),
    LSTORE(55, 1, "L"),
    LSTORE_0(63, "lstore_n"),
    LSTORE_1(64, "lstore_n"),
    LSTORE_2(65, "lstore_n"),
    LSTORE_3(66, "lstore_n"),
    LSUB(101),
    LUSHR(125),
    LXOR(131),
    MONITORENTER(194),
    MONITOREXIT(195),
    MULTIANEWARRAY(197, 3, "CB"),
    NEW(187, 2, "C"),
    NEWARRAY(188),
    NOP(0),
    POP(87),
    POP2(88),
    PUTFIELD(181, 2, "C"),
    PUTSTATIC(179, 2, "C"),
    RET(169, 1, "L"),
    RETURN(177),
    SALOAD(53),
    SASTORE(86),
    SIPUSH(17, 2, "W"),
    SWAP(95),
    TABLESWITCH(170, -1, "-"), // 16+
    WIDE(196, -1, "-"); // 3 (opcode) or 5 (inc)

    private final int opcode;
    private final int extraBytes;
    private final String decodeExtraBytes;

    private final String jvmsAnchor;

    Instruction(int opcode, int extraBytes, String decodeExtraBytes)
    {
        this.opcode = opcode;
        this.extraBytes = extraBytes;
        this.decodeExtraBytes = decodeExtraBytes;
        this.jvmsAnchor = toString().toLowerCase();
    }

    Instruction(int opcode)
    {
        this.opcode = opcode;
        this.extraBytes = 0;
        this.decodeExtraBytes = null;
        this.jvmsAnchor = toString().toLowerCase();
    }

    Instruction(int opcode, int extraBytes, String decodeExtraBytes, String jvmsAnchor)
    {
        this.opcode = opcode;
        this.extraBytes = extraBytes;
        this.decodeExtraBytes = decodeExtraBytes;
        this.jvmsAnchor = jvmsAnchor;
    }

    Instruction(int opcode, String jvmsAnchor)
    {
        this.opcode = opcode;
        this.extraBytes = 0;
        this.decodeExtraBytes = null;
        this.jvmsAnchor = jvmsAnchor;
    }

    public static Optional<Instruction> forOpcode(int opcode)
    {
        return Arrays.stream(values()).filter(cp -> cp.opcode == opcode).findFirst();
    }

    public int getExtraBytes()
    {
        return extraBytes;
    }

    public String getDecodeExtraBytes()
    {
        return decodeExtraBytes;
    }

    public int getOpcode()
    {
        return opcode;
    }

    public String getJvmsAnchor()
    {
        return jvmsAnchor;
    }

    public boolean isInvoke()
    {
        return this == INVOKEDYNAMIC || this == INVOKEINTERFACE || this == INVOKESPECIAL || this == INVOKESTATIC || this == INVOKEVIRTUAL;
    }
}