package ProcesserTest;

import chip8.Processor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by yizhu on 6/27/17.
 */
public class ProcessorInitTest {
    Processor cpu;

    @Before
    public void setup() {
       cpu = new Processor();
    }

    @Test
    public void testInit() {
//        // hardware init
//        memory = new char[4096];
//        register = new char[16];
//        screen = new char[64 * 32];
//        pcStack = new Stack<>();
//        keys = new char[16];
//
//        // load fontset
//        for (int i = 0; i < fontSet.length; i++) {
//            memory[i] = fontSet[i];
//        }
//
//        // registers init
//        I = 0x0;
//        pc = 0x200;  // start at beginning of ROM
//        delayTimer = 0;
//        soundTimer = 0;

        cpu.init();
        Assert.assertEquals(4096, cpu.getMemory().length); // memory 4096
        Assert.assertEquals(16, cpu.getRegister().length); // 16 register
        Assert.assertEquals(2048, cpu.getScreen().length); // 2048 pixels screen
        Assert.assertEquals(16, cpu.getKeys().length); // 16 keys
        char[] expectedMemory = new char[80];
        for (int i = 0; i < expectedMemory.length; i++) {
            expectedMemory[i] = cpu.getMemory()[i];
        }
        Assert.assertArrayEquals(cpu.getFontSet(), expectedMemory); // first 80 are occupied by font set.
        Assert.assertEquals(0x0, cpu.getI()); // index register 0
        Assert.assertEquals(0x200, cpu.getPc()); // program counter 0x200
        Assert.assertEquals(0, cpu.getDelayTimer()); // delay timer 0
        Assert.assertEquals(0, cpu.getSoundTimer()); // sound timer 0

    }
}
