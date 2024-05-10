import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
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
    ArrayList<Tile> snakeBody;

    Tile food;
    Random random;

    //gamelogic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    SnakeGame(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

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

        //snake head
        g.setColor(Color.GREEN);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize , tileSize, tileSize);

        //snake body
        for(int i = 0; i<snakeBody.size() ;i++){
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x *tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }
    }

    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardWidth/tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }
    
    public void move(){
        //eat food
        if (collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //snakebody
        for (int i = snakeBody.size()-1; i >= 0; i--){
            Tile snakePart = snakeBody.get(i);
            if (i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //snakeHead
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //gameover
        for (int i = 0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);

            if (collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }

        if(snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth ||
            snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeight){
                gameOver = true;
            }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        move();
        repaint();

        if (gameOver) {
            gameLoop.stop();
            
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
       if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
        velocityX =0;
        velocityY = -1;
       }
       else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != 1){
        velocityX = 0;
        velocityY = 1;
       }
       else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
        velocityX = -1;
        velocityY = 0;
       }
       else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
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
