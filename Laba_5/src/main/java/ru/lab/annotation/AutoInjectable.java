package ru.lab.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field for automatic dependency injection by {@link ru.lab.injector.Injector}.
 *
 * <p>The field type must be an interface whose implementation class is declared
 * in the {@code injections.properties} resource file.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoInjectable {
}
