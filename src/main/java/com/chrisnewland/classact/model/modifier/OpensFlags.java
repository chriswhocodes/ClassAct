package com.chrisnewland.classact.model.modifier;

public enum OpensFlags implements Modifier {

    ACC_SYNTHETIC(0x1000),
    ACC_MANDATED(0x8000);

    private int flag;

    OpensFlags(int flag) {
        this.flag = flag;
    }

    @Override
    public int getFlag() {
        return 0;
    }
}