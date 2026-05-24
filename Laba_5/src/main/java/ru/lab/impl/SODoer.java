package ru.lab.impl;

import ru.lab.interfaces.SomeOtherInterface;

/** Implementation C of {@link SomeOtherInterface}. */
public class SODoer implements SomeOtherInterface {
    @Override
    public void doSomeOther() { System.out.print("C"); }
}
