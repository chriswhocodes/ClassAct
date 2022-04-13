package com.chrisnewland.classact.model.modifier;

public enum RequiresFlags implements Modifier {

    ACC_TRANSITIVE(0x0020),
    ACC_STATIC_PHASE(0x0040),
    ACC_SYNTHETIC(0x1000),
    ACC_MANDATED(0x8000);

    RequiresFlags(int flag) {
        this.flag = flag;
    }

    private int flag;

    @Override
    public int getFlag() {
        return flag;
    }
}
