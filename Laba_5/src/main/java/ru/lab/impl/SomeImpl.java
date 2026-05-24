package ru.lab.impl;

import ru.lab.interfaces.SomeInterface;

/** Implementation A of {@link SomeInterface}. */
public class SomeImpl implements SomeInterface {
    @Override
    public void doSomething() { System.out.print("A"); }
}
