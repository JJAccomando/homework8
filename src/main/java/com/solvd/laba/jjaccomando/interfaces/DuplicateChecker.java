package com.solvd.laba.jjaccomando.interfaces;

@FunctionalInterface
public interface DuplicateChecker<T> {
    boolean check(T item, T[] array);
}
