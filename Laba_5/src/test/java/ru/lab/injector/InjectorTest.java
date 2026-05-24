package ru.lab.injector;

import org.junit.jupiter.api.Test;
import ru.lab.bean.SomeBean;
import ru.lab.impl.SODoer;
import ru.lab.impl.SomeImpl;
import ru.lab.impl.OtherImpl;
import ru.lab.interfaces.SomeInterface;
import ru.lab.interfaces.SomeOtherInterface;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class InjectorTest {

    // ---------- helper: capture stdout ----------

    private String captureOutput(Runnable action) {
        PrintStream original = System.out;
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buf));
        try {
            action.run();
        } finally {
            System.setOut(original);
        }
        return buf.toString();
    }

    // ---------- core behaviour ----------

    @Test
    void injectReturnsTheSameObject() {
        Injector injector = new Injector();
        SomeBean original = new SomeBean();
        assertSame(original, injector.inject(original));
    }

    @Test
    void injectPopulatesField1WithSomeImpl() throws Exception {
        SomeBean sb = new Injector().inject(new SomeBean());

        Field f = SomeBean.class.getDeclaredField("field1");
        f.setAccessible(true);
        assertInstanceOf(SomeImpl.class, f.get(sb));
    }

    @Test
    void injectPopulatesField2WithSODoer() throws Exception {
        SomeBean sb = new Injector().inject(new SomeBean());

        Field f = SomeBean.class.getDeclaredField("field2");
        f.setAccessible(true);
        assertInstanceOf(SODoer.class, f.get(sb));
    }

    @Test
    void defaultPropertiesProducesOutputAC() {
        SomeBean sb = new Injector().inject(new SomeBean());
        assertEquals("AC", captureOutput(sb::foo));
    }

    @Test
    void alternativePropertiesProducesOutputBC() {
        // injections_other.properties maps SomeInterface → OtherImpl (prints B)
        SomeBean sb = new Injector("injections_other.properties").inject(new SomeBean());
        assertEquals("BC", captureOutput(sb::foo));
    }

    @Test
    void uninjectBeanThrowsNPE() {
        // Verifies that without injection foo() would fail (fields are null)
        assertThrows(NullPointerException.class, new SomeBean()::foo);
    }

    @Test
    void missingPropertiesFileThrowsRuntimeException() {
        assertThrows(RuntimeException.class, () -> new Injector("nonexistent.properties"));
    }
}
