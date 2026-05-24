package ru.lab.impl;

import ru.lab.interfaces.SomeInterface;

/** Alternative implementation B of {@link SomeInterface}. */
public class OtherImpl implements SomeInterface {
    @Override
    public void doSomething() { System.out.print("B"); }
}
