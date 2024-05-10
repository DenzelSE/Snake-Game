import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;



public class SnakeGame extends JPanel implements ActionListener, KeyListener{

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
    Tile food;
    Random random;

    //gamelogic
    Timer gameLoop;
    int velocityX;
    int velocityY;

    SnakeGame(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        food = new Tile(10,10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;
        
        gameLoop = new Timer(100, this);
        gameLoop.start();


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

        //Food
        g.setColor(Color.RED);
        g.fillRect(food.x *tileSize, food.y * tileSize, tileSize, tileSize);

        //snake
        g.setColor(Color.GREEN);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize , tileSize, tileSize);
    }

    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardWidth/tileSize);
    }
    
    public void move(){
        //snakeHead
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
       if (e.getKeyCode() == KeyEvent.VK_UP){
        velocityX =0;
        velocityY = -1;
       }
       else if (e.getKeyCode() == KeyEvent.VK_DOWN){
        velocityX = 0;
        velocityY = 1;
       }
       else if (e.getKeyCode() == KeyEvent.VK_LEFT){
        velocityX = -1;
        velocityY = 0;
       }
       else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
        velocityX = 1;
        velocityY = 0;
       }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }
}
