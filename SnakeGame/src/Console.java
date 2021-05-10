import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Random;

public class Console {
    static Screen screen = null;
    public static void main(String[] args) {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Screen screen = null;
        Random random = new Random();
        // 0 left, 1 up, 2 right, 3 down
        char[] heads = {'<', '^', '>', 'v'};
        int direction = 1;
        int x = 20;
        int y = 20;
        try {
            Terminal terminal = defaultTerminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            Console.screen = screen;
            screen.startScreen();

            while(true) {
                KeyStroke keyStroke = screen.pollInput();
                if(keyStroke != null) {
                    if(keyStroke.getKeyType() == KeyType.ArrowLeft) {
                        direction = 0;
                        Console.putCharrEmpty(x, y);
                        x--;
                    }
                    if(keyStroke.getKeyType() == KeyType.ArrowUp) {
                        direction = 1;
                        Console.putCharrEmpty(x, y);
                        y--;
                    }
                    if(keyStroke.getKeyType() == KeyType.ArrowRight) {
                        direction = 2;
                        Console.putCharrEmpty(x, y);
                        x++;
                    }
                    if(keyStroke.getKeyType() == KeyType.ArrowDown) {
                        direction = 3;
                        Console.putCharrEmpty(x, y);
                        y++;
                    }
                }
                Console.putCharr(x, y, heads[direction]);

                screen.refresh();
                Thread.yield();
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            if(screen != null) {
                try {
                    screen.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static void putCharrEmpty(int x, int y) {
        Console.putCharr(x, y, ' ');
    }
    private static void putCharr(int x, int y, char c) {
        Console.screen.setCharacter(x, y, new TextCharacter(
                            c,
                            TextColor.ANSI.DEFAULT,
                            // This will pick a random background color
                            TextColor.ANSI.values()[0]));
    }
}