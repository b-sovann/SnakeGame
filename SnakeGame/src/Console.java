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
    public static void main(String[] args) {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Screen screen = null;
        Random random = new Random();
        // 0 left, 1 up, 2 right, 3 down
        char[] heads = {'<', '^', '>', 'v'};
        int direction = 1;
        try {
            Terminal terminal = defaultTerminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();

            while(true) {
                KeyStroke keyStroke = screen.pollInput();
                if(keyStroke != null) {
                    if(keyStroke.getKeyType() == KeyType.ArrowLeft) {
                        direction = 0;
                    }
                    if(keyStroke.getKeyType() == KeyType.ArrowUp) {
                        direction = 1;
                    }
                    if(keyStroke.getKeyType() == KeyType.ArrowRight) {
                        direction = 2;
                    }
                    if(keyStroke.getKeyType() == KeyType.ArrowDown) {
                        direction = 3;
                    }
                }

                screen.setCharacter(10, 20, new TextCharacter(
                            heads[direction],
                            TextColor.ANSI.DEFAULT,
                            // This will pick a random background color
                            TextColor.ANSI.values()[0]));

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
}