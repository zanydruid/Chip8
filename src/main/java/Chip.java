import lombok.Data;

import java.util.Stack;

/**
 * Created by yizhu on 6/26/17.
 * Chip 8 specifications
 */
@Data
public class Chip {
    // store current operation code
    private short opcode;

    /* System memory map
    0x000-0x1FF - Chip 8 interpreter (contains font set in emu)
    0x050-0x0A0 - Used for the built in 4x5 pixel font set (0-F)
    0x200-0xFFF - Program ROM and work RAM
    */
    private char[] memory; // 4K memory

    // register V0 to VE, VE has 2 bytes for 'carry flag'
    private char[] register;

    // index register
    private short I;

    // program counter
    private short pc;

    // screen 2048 pixels 64 * 32
    private char[] screen;

    // two timer registers. when set above 0, they will count down to 0.
    private char delayTimer;
    private char soundTimer;    // system's buzzer sounds whenever it reaches 0.

    // a stack to store pc when a jump happens.
    // Stack size is 16 which represent 16 levels.
    private Stack<Character> pcStack;

    // keypad with 16 keys, save the current state of key
    private char[] keys;

    /**
     * Initialize chip8 CPU
     */
    public void init() {

        // hardware init
        memory = new char[4096];
        register = new char[16];
        screen = new char[64 * 32];
        pcStack = new Stack<>();
        keys = new char[16];

        // registers init
        I = 0x0;
        pc = 0x200;
        delayTimer = 0;
        soundTimer = 0;
    }

}
