import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Random;

public class Wall {
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
            Wall.screen = screen;
            screen.startScreen();

            TerminalSize terminalSize = terminal.getTerminalSize();

            int width  = terminalSize.getColumns();
            int height = terminalSize.getRows();


            while(true) {
                KeyStroke keyStroke = screen.pollInput();
                if(keyStroke != null) {
                    if(keyStroke.getKeyType() == KeyType.ArrowLeft) {
                        direction = 0;
                        Wall.putCharrEmpty(x, y);
                        x--;
                    }
                    if(keyStroke.getKeyType() == KeyType.ArrowUp) {
                        direction = 1;
                        Wall.putCharrEmpty(x, y);
                        y--;
                    }
                    if(keyStroke.getKeyType() == KeyType.ArrowRight) {
                        direction = 2;
                        Wall.putCharrEmpty(x, y);
                        x++;
                    }
                    if(keyStroke.getKeyType() == KeyType.ArrowDown) {
                        direction = 3;
                        Wall.putCharrEmpty(x, y);
                        y++;
                    }
                }

                Wall.putCharr(x, y, heads[direction]);
                Wall.drawWall(width, height);

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
        Wall.putCharr(x, y, ' ');
    }
    private static void putCharr(int x, int y, char c) {
        Wall.screen.setCharacter(x, y, new TextCharacter(
                            c,
                            TextColor.ANSI.DEFAULT,
                            // This will pick a random background color
                            TextColor.ANSI.values()[0]));
    }

    private static void drawWall(int width, int height){

        for (int i =0; i < width; i++){
            Wall.putCharr(i, 0, '*');
        }
        for (int i =0; i < width; i++){
            Wall.putCharr(i, height-1, '*');
        }
        for (int i =0; i < height; i++){
            Wall.putCharr(0, i, '*');
        }
        for (int i =0; i < height; i++){
            Wall.putCharr(width-1, i, '*');
        }

    }
}