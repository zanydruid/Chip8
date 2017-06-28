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

            case 0x0000: // 0NNN
                switch (opcode & 0xFF) {
                    case 0xE0: // 00E0 clear screen
                        //TODO
                        break;
                    case 0xEE: // 00EE return from a subroutine
                        //TODO
                        break;
                    default: // Call program at NNN
                        //TODO
                        break;
                }
                break;

            case 0x1000: // 1NNN jumps to address NNN
                //TODO
                break;

            case 0x2000: // 2NNN calls subroutine at NNN
                //TODO
                break;

            case 0x3000: // 3XNN skips next instruction if VX == NN
                //TODO
                break;

            case 0x4000: // 4XNN skips next instruction if VX != NN
                //TODO
                break;

            case 0x5000: // 5XY0 skips next instruction if VX == VY
                //TODO
                break;

            case 0x6000: // 6XNN sets VX to NN
                //TODO
                break;

            case 0x7000: // 7XNN adds NN to VX
                //TODO
                break;

            case 0x8000:
                switch(opcode & 0xF) {
                    case 0x0: // 8XY0 sets VX to value of VY
                        //TODO
                        break;

                    case 0x1: // 8XY1 Vx = Vx | Vy
                        //TODO
                        break;

                    case 0x2: // 8XY2 Vx = Vx & Vy
                        //TODO
                        break;

                    case 0x3: // 8XY3 Vx = Vx ^ Vy
                        //TODO
                        break;

                    case 0x4: // 8XY4 Vx += Vy
                        //TODO
                        break;

                    case 0x5: // 8XY5 Vx -= Vy
                        //TODO
                        break;

                    case 0x6: // 8XY6 Vx >> 1
                        //TODO
                        break;

                    case 0x7: // 8XY7 Vx = Vy - Vx
                        //TODO
                        break;

                    case 0xE: // 8XYE Vx << 1
                        //TODO
                        break;

                    default:
                        System.out.println("Error opcode " + opcode);
                }
                break;

            case 0x9000: // 9XY0 skips the next instruction if Vx != Vy
                //TODO
                break;

            case 0xA000: // ANNN set I to address NNN
                //TODO
                break;

            case 0xB000: // BNNN jumps to NNN + V0
                //TODO
                break;

            case 0xC000: // CXNN sets VX to bitwise on a random number with NN, Vx = rand()&NN
                //TODO
                break;

            case 0xD000: // DXYN draws a sprite at (VX,VY) with 8 px width and N height, draw(Vx, Vy, N)
                //TODO
                break;

            case 0xE000:
                switch (opcode & 0xF) {
                    case 0xE: // EX9E skips next instruction if key stored in VX is pressed.
                        //TODO
                        break;

                    case 0x1: // EXA1 skips next instruction if key stored in VX isn;t pressed.
                        //TODO
                        break;
                }
                break;

            case 0xF000:
                switch (opcode & 0xFF) {
                    case 0x07: // FX07 sets VX to the value of delay timer
                        //TODO
                        break;

                    case 0x0A: // FX0A A key press is awaited, and then stored in VX. (Blocking Operation. All instruction halted until next key event)
                        //TODO
                        break;

                    case 0x15: // FX15 Sets the delay timer to VX.
                        //TODO
                        break;

                    case 0x18: // FX18 Sets the sound timer to VX.
                        //TODO
                        break;

                    case 0x1E: // FX1E Adds VX to I. I +=Vx
                        //TODO
                        break;

                    case 0x29: // FX29  	I=sprite_addr[Vx]
                        //TODO
                        break;

                    case 0x33: // FX33 set_BCD(Vx); *(I+0)=BCD(3); *(I+1)=BCD(2);*(I+2)=BCD(1);
                        //TODO
                        break;

                    case 0x55: // FX55 Stores V0 to VX (including VX) in memory starting at address I.
                        //TODO
                        break;

                    case 0x65: // FX65 Fills V0 to VX (including VX) with values from memory starting at address I.
                        //TODO
                        break;


                }
                break;

            default:
                System.out.println("Unknown opcode: " + opcode);
                break;
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

