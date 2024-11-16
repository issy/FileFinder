package com.issy.compiler;

@FunctionalInterface
public interface FileMatcher {

    boolean matches(String filepath);

}
