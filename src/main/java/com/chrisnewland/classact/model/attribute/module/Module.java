package com.chrisnewland.classact.model.attribute.module;

import com.chrisnewland.classact.model.AttributeType;
import com.chrisnewland.classact.model.attribute.Attribute;
import com.chrisnewland.classact.model.constantpool.ConstantPool;
import com.chrisnewland.classact.model.modifier.Modifier;
import com.chrisnewland.classact.model.modifier.ModuleFlags;

public class Module implements Attribute {

    private int moduleNameIndex;
    private int moduleFlags;
    private int moduleVersionIndex;

    private Requires[] requires;

    private Exports[] exports;

    private int[] usesIndex;

    private Provides[] provides;

    public Module(int moduleNameIndex, int moduleFlags, int moduleVersionIndex, Requires[] requires, Exports[] exports, int[] usesIndex, Provides[] provides) {
        this.moduleNameIndex = moduleNameIndex;
        this.moduleFlags = moduleFlags;
        this.moduleVersionIndex = moduleVersionIndex;
        this.requires = requires;
        this.exports = exports;
        this.usesIndex = usesIndex;
        this.provides = provides;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.Module;
    }

    public int getModuleNameIndex() {
        return moduleNameIndex;
    }

    public int getModuleFlags() {
        return moduleFlags;
    }

    public int getModuleVersionIndex() {
        return moduleVersionIndex;
    }

    public Requires[] getRequires() {
        return requires;
    }

    public Exports[] getExports() {
        return exports;
    }

    public int[] getUsesIndex() {
        return usesIndex;
    }

    public Provides[] getProvides() {
        return provides;
    }

    @Override
    public String toString(ConstantPool constantPool) {
        StringBuilder builder = new StringBuilder();

        builder.append("ModuleName: ").append(constantPool.toString(moduleNameIndex)).append("\n")
                .append("ModuleFlags: ").append(Modifier.getAccessString(ModuleFlags.values(), moduleFlags)).append("\n")
                .append("ModuleVersionIndex: ").append(constantPool.toString(moduleVersionIndex)).append("\n");

        builder.append("Requires:\n");
        for (Requires r : requires) {
            builder.append(r.toString(constantPool)).append("\n");
        }

        builder.append("Exports:\n");
        for (Exports e : exports) {
            builder.append(e.toString(constantPool)).append("\n");
        }

        builder.append("Uses:\n");
        for (int i : usesIndex) {
            builder.append(constantPool.toString(i)).append("\n");
        }

        builder.append("Provides:\n");
        for (Provides p : provides) {
            builder.append(p.toString(constantPool)).append("\n");
        }

        return builder.toString();
    }
}