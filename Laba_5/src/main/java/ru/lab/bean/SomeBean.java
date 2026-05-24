package ru.lab.bean;

import ru.lab.annotation.AutoInjectable;
import ru.lab.interfaces.SomeInterface;
import ru.lab.interfaces.SomeOtherInterface;

/**
 * Example bean with two injectable fields.
 *
 * <p>Fields are intentionally left uninitialized — they are populated by
 * {@link ru.lab.injector.Injector#inject(Object)} at runtime.</p>
 */
public class SomeBean {

    @AutoInjectable
    private SomeInterface field1;

    @AutoInjectable
    private SomeOtherInterface field2;

    /**
     * Calls both injected dependencies.
     * Prints "A" then "C" with the default properties configuration.
     */
    public void foo() {
        field1.doSomething();
        field2.doSomeOther();
    }
}
