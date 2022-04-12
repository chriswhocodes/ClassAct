package com.chrisnewland.classact;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;

public class TestClass implements Runnable {

    private int getTheValue() {
        return 42;
    }

    private MethodType methodType;

    private MethodHandle methodHandle;

    private void methodHandle() {
        try {
            methodType = MethodType.methodType(int.class);

            methodHandle = MethodHandles.lookup()
                    .findVirtual(getClass(), "getTheValue", methodType);

            methodHandle.invokeExact();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @java.lang.Deprecated
    public void deprecatedMethod() {
        System.out.println("foo");
    }

    @interface MyAnnotation {
        String foo();
    }

    public void testParameterAnnotations(@MyAnnotation(foo = "bar") Object param) {
        System.out.println(param);
    }

    public interface TestInterface {
        void bar();
    }

    public void tryCatch() {
        try {
            int value = Integer.parseInt("123");
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }

    private void encloser() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("running!");
            }
        };
    }

    @Override
    public void run() {
        System.out.println("I ran!");
    }

    private static final String fooConstantValueString = "constantfoo";
    private static final double fooConstantValueDouble = 0.125d;
    private static final float fooConstantValueFloat = 0.456f;
    private static final int fooConstantValueInt = 10;
    private static final long fooConstantValueLong = 100L;

    private double getPrimitiveDouble() {
        return fooConstantValueDouble;
    }

    private float getPrimitiveFloat() {
        return fooConstantValueFloat;
    }

    private int getPrimitiveInt() {
        return fooConstantValueInt;
    }

    private long getPrimitiveLong() {
        return fooConstantValueLong;
    }

    private class Inner1 {
        public String pubString;
    }

    public <Q extends Object> List<Q> fooGenerics() throws RuntimeException, NumberFormatException {
        return new ArrayList<>();
    }

    private static class StaticInner1 {
        public int bar;
    }

    private void foo() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}