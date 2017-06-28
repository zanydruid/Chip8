package chip8;

/**
 * Created by yizhu on 6/27/17.
 */
public class Chip8 {

    /**
     * The main program to start emulator.
     * @param args file name to run on emulator.
     */
    public static void main(String[] args) {
        // setup render system and register input callbacks
        // TODO

        // Initialize chip8.Processor
        Processor chip8 = new Processor();
        chip8.init();
        chip8.loadProgram(args[0]);

        // emulation loop
        for (;;) {
            // emulate one cycle
            chip8.run();

            // if draw flag is set, update screen
            // TODO

            // store key press state (press and release)
            // TODO
        }

    }
}
