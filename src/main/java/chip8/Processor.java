package chip8;

import lombok.Data;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by yizhu on 6/26/17.
 * chip8.Chip8 specifications
 */
@Data
public class Processor {
    // store current operation code
    private char opcode;

    /* System memory map
    0x000-0x1FF - chip8.Processor 8 interpreter (contains font set in emu)
    0x050-0x0A0 - Used for the built in 4x5 pixel font set (0-F)
    0x200-0xFFF - Program ROM and work RAM
    */
    private char[] memory; // 4K memory

    // register V0 to VE, VE has 2 bytes for 'carry flag'
    private char[] register;

    // index register
    private char I;

    // program counter
    private char pc;

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

    // font set, each character is 4 * 5 pixels.
    private char[] fontSet =
            {
                    0xF0, 0x90, 0x90, 0x90, 0xF0, // 0
                    0x20, 0x60, 0x20, 0x20, 0x70, // 1
                    0xF0, 0x10, 0xF0, 0x80, 0xF0, // 2
                    0xF0, 0x10, 0xF0, 0x10, 0xF0, // 3
                    0x90, 0x90, 0xF0, 0x10, 0x10, // 4
                    0xF0, 0x80, 0xF0, 0x10, 0xF0, // 5
                    0xF0, 0x80, 0xF0, 0x90, 0xF0, // 6
                    0xF0, 0x10, 0x20, 0x40, 0x40, // 7
                    0xF0, 0x90, 0xF0, 0x90, 0xF0, // 8
                    0xF0, 0x90, 0xF0, 0x10, 0xF0, // 9
                    0xF0, 0x90, 0xF0, 0x90, 0x90, // A
                    0xE0, 0x90, 0xE0, 0x90, 0xE0, // B
                    0xF0, 0x80, 0x80, 0x80, 0xF0, // C
                    0xE0, 0x90, 0x90, 0x90, 0xE0, // D
                    0xF0, 0x80, 0xF0, 0x80, 0xF0, // E
                    0xF0, 0x80, 0xF0, 0x80, 0x80  // F
            };

    /**
     * Initialize chip8 chip8.Processor
     */
    public void init() {

        // hardware init
        memory = new char[4096];
        register = new char[16];
        screen = new char[64 * 32];
        pcStack = new Stack<>();
        keys = new char[16];

        // load fontset
        for (int i = 0; i < fontSet.length; i++) {
            memory[i] = fontSet[i];
        }

        // registers init
        I = 0x0;
        pc = 0x200;  // start at beginning of ROM
        delayTimer = 0;
        soundTimer = 0;
    }

    /**
     * load program into the memory
     */
    public void loadProgram(String fileName) {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("file: " + fileName + " doesn't exist!!!");
            return;
        }

        if (!file.isFile() || !file.canRead()) {
            System.out.println("file: " + fileName + " cannot be read from!!!");
            return;
        }

        // buffer to store code from file
        List<Character> buffer = new ArrayList<>();
        try {
            FileInputStream stream = new FileInputStream(file);

            // store code in buffer
            while (stream.available() > 0) {
                buffer.add((char)stream.read());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // file is too big
        if (buffer.size() > 3585) {
            System.out.println("file: " + fileName + " is too big to be load in memory!!!");
            System.out.println("file: " + buffer.size() + " memory: 3585.");
            return;
        }

        // load into the memory
        for(int i = 0; i < buffer.size(); i++) {
            memory[512 + i] = buffer.get(i);
        }

    }

    /**
     * Start chip8 chip8.Processor
     */
    public void run() {
        // get operation code, 2 bytes
        opcode = (char) (memory[pc] << 8 | memory[pc + 1]);

        // decode operation code
        switch (opcode & 0xF000) {


            default:
                System.out.println("Unknown opcode: " + opcode);
        }

        // update timers
        if (delayTimer > 0) {
            delayTimer--;
        }

        if (soundTimer > 0) {
            if (soundTimer == 1) {
                System.out.println("BEEP!!!");
                soundTimer--;
            }
        }
    }

}

