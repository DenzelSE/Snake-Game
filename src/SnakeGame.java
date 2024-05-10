import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


public class SnakeGame extends JPanel{

    private class Tile {
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    Tile snakeHead;

    SnakeGame(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);

        snakeHead = new Tile(5, 5);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
        //grid
        for (int i = 0; i < boardWidth/tileSize; i++){
            g.drawLine(i * tileSize, 0, i*tileSize, boardWidth); //x1,y1 , x2,y2 vertical
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize); //x1,y1 x2,y2 horizontal
        }
        //snake
        g.setColor(Color.GREEN);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize , tileSize, tileSize);
    }
}
