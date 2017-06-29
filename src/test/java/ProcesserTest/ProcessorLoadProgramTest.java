package ProcesserTest;

import chip8.Processor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yizhu on 6/28/17.
 */
public class ProcessorLoadProgramTest {
    Processor cpu;

    @Before
    public void setup() {
        cpu = new Processor();
        cpu.init();
    }

    @Test
    public void testLoadProgram() {
        char[] expected = {'a', 'b', 'c', 'd', 'e', 'f', 'g'};
        List<Character> actual = new ArrayList<>();
        // successful load
        cpu.loadProgram("./src/test/resources/processor/success");
        for (int i = 0; i < cpu.getMemory().length; i++) {
            if (cpu.getMemory()[512 + i] == 0) break;
            actual.add(cpu.getMemory()[512 + i]);
        }
        char[] actualResult = new char[7];
        for (int i = 0; i < actual.size(); i++) {
            actualResult[i] = actual.get(i);
        }

        Assert.assertArrayEquals(expected, actualResult);

    }

}
