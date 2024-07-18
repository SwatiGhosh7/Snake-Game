import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class Snakegame extends JPanel implements ActionListener, KeyListener{
    private class Tile{
        int x;
        int y;
        Tile(int x,int y){
            this.x=x;
            this.y=y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize=25;

    //for snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    //for food
    Tile food;
    
    //to randomly place the food
    Random random;

    //logic
    Timer gameLoop;
    
    int velocityX;
    int velocityY;

    boolean gameOver=false;

    Snakegame(int boardWidth, int boardHeight){
        this.boardWidth=boardWidth;
        this.boardHeight=boardHeight;
        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead=new Tile(5 , 5);
        snakeBody=new ArrayList<Tile>();

        food=new Tile(10,10);
        random=new Random();
        placeFood();
        
        velocityX=0;
        velocityY=0;

        gameLoop=new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //for grid
       for(int i=0;i<boardWidth/tileSize;i++){
        //(x1,y1,x2,y2)
        g.drawLine(i*tileSize,0,i*tileSize,boardHeight);
        g.drawLine(0,i*tileSize,boardWidth,i*tileSize);
       }

       //for food
       g.setColor(Color.red);
       g.fillOval(food.x*tileSize,food.y*tileSize,tileSize,tileSize);

        //for snakehead
        g.setColor(Color.green);
        g.fillRoundRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize,tileSize-12,tileSize);

        //snake body
        for(int i=0;i<snakeBody.size();i++){
            Tile snakePart=snakeBody.get(i);
            g.fill3DRect(snakePart.x*tileSize,snakePart.y*tileSize,tileSize,tileSize,true);
        }
            g.setFont(new Font("Broadway",Font.BOLD,16));
            if(gameOver){
                g.setColor(Color.red);
                g.drawString("GAME OVER!    Score:"+String.valueOf(snakeBody.size()),tileSize-16,tileSize);
            }
          else{
            g.drawString("SCORE: "+String.valueOf(snakeBody.size()),tileSize-16,tileSize);
           }
    }
    

    public void placeFood(){
        food.x=random.nextInt(boardWidth/tileSize);
        food.y=random.nextInt(boardHeight/tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x==tile2.x && tile1.y==tile2.y;
    }

    public void move(){
       
        //for eating food
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x,food.y));
            placeFood();
        }
        
        //Snake body
        for(int i=snakeBody.size()-1;i>=0;i--){
            Tile snakePart=snakeBody.get(i);
            if(i==0){
                snakePart.x=snakeHead.x;
                snakePart.y=snakeHead.y;
            }
            else{
                Tile prevSnakePart=snakeBody.get(i-1);
                snakePart.x=prevSnakePart.x;
                snakePart.y=prevSnakePart.y;
            }
        }

        //snake head
        snakeHead.x+=velocityX;
        snakeHead.y+=velocityY;

        //for game over
         for(int i=0;i< snakeBody.size();i++){
            Tile snakePart=snakeBody.get(i);
            //if collides with snake head
            if(collision(snakeHead, snakePart)){
                gameOver=true;
            }
         }
        if(snakeHead.x*tileSize<0 || snakeHead.x*tileSize>boardWidth||snakeHead.y*tileSize<0||snakeHead.y*tileSize>boardHeight){
        gameOver=true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();//to update the x and y position of the snake
        repaint();// calls draw() over and over again
        if(gameOver){
            gameLoop.stop();
        }
    }
   @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP && velocityY!=1){
        velocityX=0;
        velocityY=-1;
       }
        else if(e.getKeyCode()== KeyEvent.VK_DOWN && velocityY!=-1){
        velocityX=0;
        velocityY=1;
       }
        else if(e.getKeyCode()== KeyEvent.VK_LEFT && velocityX!=1){
        velocityX=-1;
        velocityY=0;
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX!=-1){
            velocityX=1;
            velocityY=0;
        }
    }

    //no need
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
