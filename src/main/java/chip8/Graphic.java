package chip8;

import lombok.Data;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yizhu on 6/27/17.
 * Handle display and update screen.
 */
@Data
public class Graphic extends JFrame {
    private Processor chip;
    private int keyMap[];
    private MyPanel panel;

    /**
     * constructor
     * @param chip
     */
    public Graphic (Processor chip) {
        setPreferredSize(new Dimension(640, 320));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Chip 8");
        panel = new MyPanel();
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        pack();
        this.chip = chip;
        keyMap = new int[256];
        fillMap();
    }

    class MyPanel extends JPanel {

    }

    private void fillMap() {
        for(int i = 0; i < keyMap.length; i++) {
            keyMap[i] = -1;
        }
        keyMap['1'] = 1;
        keyMap['2'] = 2;
        keyMap['3'] = 3;
        keyMap['Q'] = 4;
        keyMap['W'] = 5;
        keyMap['E'] = 6;
        keyMap['A'] = 7;
        keyMap['S'] = 8;
        keyMap['D'] = 9;
        keyMap['Z'] = 0xA;
        keyMap['X'] = 0;
        keyMap['C'] = 0xB;
        keyMap['4'] = 0xC;
        keyMap['R'] = 0xD;
        keyMap['F'] = 0xE;
        keyMap['V'] = 0xF;
    }
}
