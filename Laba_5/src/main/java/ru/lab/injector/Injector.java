package ru.lab.injector;

import ru.lab.annotation.AutoInjectable;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Lightweight dependency injector that populates fields annotated with
 * {@link AutoInjectable} by reading implementation mappings from
 * {@code injections.properties}.
 *
 * <p>The properties file maps fully-qualified interface names to
 * fully-qualified implementation class names, for example:
 * <pre>
 * ru.lab.interfaces.SomeInterface=ru.lab.impl.SomeImpl
 * ru.lab.interfaces.SomeOtherInterface=ru.lab.impl.SODoer
 * </pre>
 */
public class Injector {

    private static final String PROPERTIES_FILE = "injections.properties";

    private final Properties properties;

    /**
     * Creates an {@code Injector} loading mappings from {@value #PROPERTIES_FILE}
     * on the classpath.
     *
     * @throws RuntimeException if the properties file cannot be found or read
     */
    public Injector() {
        this(PROPERTIES_FILE);
    }

    /**
     * Creates an {@code Injector} loading mappings from the given classpath resource.
     * This constructor is primarily used in tests to supply a custom properties file.
     *
     * @param propertiesFile classpath-relative path to the properties file
     */
    public Injector(String propertiesFile) {
        properties = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(propertiesFile)) {
            if (in == null) {
                throw new RuntimeException("Properties resource not found: " + propertiesFile);
            }
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + propertiesFile, e);
        }
    }

    /**
     * Injects dependencies into all {@link AutoInjectable}-annotated fields of {@code object}.
     *
     * <p>For each such field the method:
     * <ol>
     *   <li>Reads the field's declared type (expected to be an interface).</li>
     *   <li>Looks up the implementation class name in the properties file.</li>
     *   <li>Instantiates that class via its no-arg constructor.</li>
     *   <li>Sets the field value using reflection.</li>
     * </ol>
     *
     * @param <T>    type of the target object
     * @param object object whose fields should be injected, must not be {@code null}
     * @return the same {@code object} with all annotated fields populated
     * @throws RuntimeException if a mapping is missing or instantiation fails
     */
    public <T> T inject(T object) {
        Class<?> clazz = object.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(AutoInjectable.class)) {
                continue;
            }

            String interfaceName = field.getType().getName();
            String implName = properties.getProperty(interfaceName);
            if (implName == null) {
                throw new RuntimeException(
                        "No implementation configured for interface: " + interfaceName);
            }

            try {
                Class<?> implClass = Class.forName(implName.trim());
                Object instance = implClass.getDeclaredConstructor().newInstance();
                field.setAccessible(true);
                field.set(object, instance);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(
                        "Failed to inject field '" + field.getName() + "': " + e.getMessage(), e);
            }
        }

        return object;
    }
}
