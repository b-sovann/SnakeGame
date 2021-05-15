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

public class WallConflict {
    static Screen screen = null;
    public static void main(String[] args) {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Screen screen = null;

        // 0 left, 1 up, 2 right, 3 down
        char[] heads = {'<', '^', '>', 'v'};
        int direction = 1;
        int x = 20;
        int y = 20;
        try {
            Terminal terminal = defaultTerminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            WallConflict.screen = screen;
            screen.startScreen();

            TerminalSize terminalSize = terminal.getTerminalSize();

            int width  = terminalSize.getColumns();
            int height = terminalSize.getRows();
            int[] fruitCoordinate = WallConflict.getFruit(width, height);

            while(true) {
                KeyStroke keyStroke = screen.pollInput();
                if(keyStroke != null) {
                    if(keyStroke.getKeyType() == KeyType.ArrowLeft) {
                        direction = 0;
                        WallConflict.putCharrEmpty(x, y);
                        x--;
                        if (x == fruitCoordinate[0] && y == fruitCoordinate[1]) {
                            fruitCoordinate = WallConflict.getFruit(width, height);
                        }
                    }
                    if(keyStroke.getKeyType() == KeyType.ArrowUp) {
                        direction = 1;
                        WallConflict.putCharrEmpty(x, y);
                        y--;
                        if (x == fruitCoordinate[0] && y == fruitCoordinate[1]) {
                            fruitCoordinate = WallConflict.getFruit(width, height);
                        }
                    }
                    if(keyStroke.getKeyType() == KeyType.ArrowRight) {
                        direction = 2;
                        WallConflict.putCharrEmpty(x, y);
                        x++;
                        if (x == fruitCoordinate[0] && y == fruitCoordinate[1]) {
                            fruitCoordinate = WallConflict.getFruit(width, height);
                        }
                    }
                    if(keyStroke.getKeyType() == KeyType.ArrowDown) {
                        direction = 3;
                        WallConflict.putCharrEmpty(x, y);
                        y++;
                            if (x == fruitCoordinate[0] && y == fruitCoordinate[1]) {
                                fruitCoordinate = WallConflict.getFruit(width, height);
                            }
                    }
                }

                WallConflict.drawWall(width, height);
                WallConflict.putCharr(x, y, heads[direction]);
                WallConflict.putCharr( fruitCoordinate[0], fruitCoordinate[1], '@');



                if (WallConflict.hitWall(x, y, width, height)){
                    System.out.println("Hit");
                }

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
        WallConflict.putCharr(x, y, ' ');
    }
    private static void putCharr(int x, int y, char c) {
        WallConflict.screen.setCharacter(x, y, new TextCharacter(
                            c,
                            TextColor.ANSI.DEFAULT,
                            // This will pick a random background color
                            TextColor.ANSI.values()[0]));
    }

    private static void drawWall(int width, int height){

        for (int i =0; i < width; i++){
            WallConflict.putCharr(i, 0, '*');
        }
        for (int i =0; i < width; i++){
            WallConflict.putCharr(i, height-1, '*');
        }
        for (int i =0; i < height; i++){
            WallConflict.putCharr(0, i, '*');
        }
        for (int i =0; i < height; i++){
            WallConflict.putCharr(width-1, i, '*');
        }
    }

    private static boolean hitWall(int x, int y, int width, int height){
        boolean isHit = false;
        if (x == width-1 || y == height -1 || x == 0 || y == 0)
            isHit = true;
        return isHit;
    }

    private static int[] getFruit(int width, int height){
        int[] fruit = new int[2];
        Random random = new Random();

            fruit[0] = random.nextInt(width);
            fruit[1] = random.nextInt(height);
        return fruit;
    }



}