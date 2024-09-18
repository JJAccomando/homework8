package com.solvd.laba.jjaccomando.interfaces;

@FunctionalInterface
public interface ApplyFilter<T> {
    boolean filter(T t);
}
