package ru.lab;

import ru.lab.bean.SomeBean;
import ru.lab.injector.Injector;

/**
 * Demonstrates the injector: creates SomeBean, injects dependencies,
 * then calls foo(). Expected output: AC
 */
public class Main {
    public static void main(String[] args) {
        SomeBean sb = new Injector().inject(new SomeBean());
        sb.foo();   // prints: AC
    }
}
