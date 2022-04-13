package com.chrisnewland.classact.model.modifier;

public enum MethodParametersAccess implements Modifier {

    ACC_FINAL(0x0010),
    ACC_SYNTHETIC(0x1000),
    ACC_MANDATED(0x8000);

    private int flag;

    MethodParametersAccess(int flag) {
        this.flag = flag;
    }

    @Override
    public int getFlag() {
        return flag;
    }
}