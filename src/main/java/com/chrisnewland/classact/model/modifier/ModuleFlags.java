package com.chrisnewland.classact.model.modifier;

public enum ModuleFlags implements Modifier {

    ACC_OPEN(0x0020),
    ACC_SYNTHETIC(0x1000),
    ACC_MANDATED(0x8000);

    private int flag;

    ModuleFlags(int flag) {
        this.flag = flag;
    }

    @Override
    public int getFlag() {
        return 0;
    }
}
