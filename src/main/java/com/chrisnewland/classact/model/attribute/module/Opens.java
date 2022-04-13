package com.chrisnewland.classact.model.attribute.module;

public class Opens {

    private int opensIndex;
    private int opensFlags;
    private int[] opensToIndex;

    public Opens(int opensIndex, int opensFlags, int[] opensToIndex) {
        this.opensIndex = opensIndex;
        this.opensFlags = opensFlags;
        this.opensToIndex = opensToIndex;
    }

    public int getOpensIndex() {
        return opensIndex;
    }

    public int getOpensFlags() {
        return opensFlags;
    }

    public int[] getOpensToIndex() {
        return opensToIndex;
    }
}