package com.solvd.laba.jjaccomando.interfaces;

@FunctionalInterface
public interface PrintInfo<T> {
    String getInfo(T item);
}
