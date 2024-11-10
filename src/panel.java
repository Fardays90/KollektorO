import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.File;


public class panel extends JPanel implements ActionListener{

    final static int SCREEN_WIDTH = 600;
    final static int SCREEN_HEIGHT = 600;
    final static int UNIT_SIZE = 30;
    int lives = 10;
    int UNIT_NUMBERS = (SCREEN_HEIGHT * SCREEN_WIDTH)/ UNIT_SIZE;
    int[] ballX = new int[UNIT_NUMBERS];
    int[] ballY = new int[UNIT_NUMBERS];
    int[] ball1X = new int[UNIT_NUMBERS];
    int[] ball1Y = new int[UNIT_NUMBERS];
    int[] pointX = new int[UNIT_NUMBERS];
    int[] pointY = new int[UNIT_NUMBERS];
    char direction;
    char directionP = 'D';
    int DELAY;
    boolean inBounds1L = true;
    boolean inBounds2L = true;
    boolean inBounds1R = true;
    boolean inBounds2R = true;
    boolean running;
    boolean retry = false;
    boolean play = false;
    Timer timer;
    Timer timer1;
    Image image = Toolkit.getDefaultToolkit().createImage("sp.jfif");
    Image end = Toolkit.getDefaultToolkit().createImage("end.png");
    Image down = Toolkit.getDefaultToolkit().createImage("down2.png");
    Random random = new Random();
    int score = 0;
   
    
   panel(){

    this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
    this.setVisible(true);
    this.setBackground(Color.black);
    this.setFocusable(true);
    this.addKeyListener(new key());
    start();

   }

   public void start(){
        DELAY = 29;
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
   }

   public void points(){
        if(directionP == 'D'){
          pointY[0] = pointY[0] + UNIT_SIZE/2;
        }
   }

   public void checkPoint(){
    if(ballX[0] == pointX[0] && ballY[0] == pointY[0]){
          pointX[0] = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
          pointY[0] = 0;
          score++;

    }
    if(ball1X[0] == pointX[0] && ball1Y[0] == pointY[0]){
        pointX[0] = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        pointY[0] = 0;
        score++;

  }
    if(pointY[0] > SCREEN_HEIGHT){
        pointX[0] = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        pointY[0] = 0;
       lives--;
    }
    if(lives == 0){
        running = false;
    }
   }

   public void gameOver(Graphics g){
      g.setColor(Color.red);
      g.setFont(new Font("IMPACT",Font.PLAIN,70));
      FontMetrics yo = getFontMetrics(g.getFont());
      g.drawString("Game over",(SCREEN_WIDTH - yo.stringWidth("Game over"))/2,SCREEN_HEIGHT/2);
      g.setColor(Color.green);
      g.setFont(new Font("IMPACT",Font.PLAIN,40));
      FontMetrics halo = getFontMetrics(g.getFont());
      g.drawString("SCORE: "+String.valueOf(score),(SCREEN_WIDTH - halo.stringWidth("SCORE: "+String.valueOf(score))-14)/2,SCREEN_HEIGHT - 100);
      g.drawImage(end,10,10,this);
      g.setColor(Color.CYAN);
      g.setFont(new Font("IMPACT",Font.PLAIN,20));
      FontMetrics RETRY = getFontMetrics(g.getFont());
      g.drawString("Press P to play again!",(SCREEN_WIDTH - RETRY.stringWidth("Press P to play again!"))-222,SCREEN_HEIGHT-180);
   }

   public void paintComponent(Graphics g){
    super.paintComponent(g);
    draw(g);
   }
   public void draw(Graphics g){
        if(running){

               g.setColor(new Color(182,10,130));
               for(int i = 0 ; i < UNIT_NUMBERS; i++){
                g.drawLine(i*UNIT_SIZE,0,i,15*UNIT_SIZE);
                g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
               
            }
            g.drawImage(image,SCREEN_WIDTH/2-120,SCREEN_HEIGHT/2-69,this);
            g.setColor(Color.white);
       /*  for(int i = 0 ; i < UNIT_NUMBERS; i++){
          int starX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE);
          int starY = random.nextInt(15*UNIT_SIZE,(int)(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE);
          g.fillOval(starX,starY,UNIT_SIZE/2,UNIT_SIZE/2);
        }*/
            g.drawImage(down,0,15*UNIT_SIZE,this);
            g.setColor(Color.white);
            g.fillArc(ballX[0],ballY[0],UNIT_SIZE,UNIT_SIZE,120,300);
            g.setColor(Color.white);
            g.fillArc(ball1X[0],ball1Y[0],UNIT_SIZE,UNIT_SIZE,120,300);
            g.setColor(Color.cyan);
            g.fillOval(pointX[0],pointY[0],UNIT_SIZE/2,UNIT_SIZE/2);
            g.setFont(new Font("Impact",Font.PLAIN,30));
            g.setColor(new Color(218,74,242));
            g.drawString("Points: "+String.valueOf(score),10,UNIT_SIZE);
            g.drawString("Health: "+String.valueOf(lives),15*UNIT_SIZE+20,UNIT_SIZE);
    
    }
    else if(running == false){
        gameOver(g);
        }
    }

   public void checkCollision(){
    if(ballY[0] == 15*UNIT_SIZE || ball1Y[0] == 15*UNIT_SIZE){
        running = false;
    }
    if(ballX[0] == 0){
        inBounds1L = false;
    }
    else{
        inBounds1L = true;
    }
    if(ballX[0] == SCREEN_WIDTH-UNIT_SIZE){
        inBounds1R = false;
    }
    else{
        inBounds1R =true;
    }
    if(ball1X[0] == 0){
        inBounds2L = false;
    }
    else{
        inBounds2L = true;
    }
    if(ball1X[0] == SCREEN_WIDTH-UNIT_SIZE){
        inBounds2R = false;
    }
    else{
        inBounds2R =true;
    }
   }
   public void playAgain(){
     if(retry == true){
        running = true;
        retry = false;
        score = 0;
     }
     ball1X[0] = SCREEN_WIDTH/2;
     ball1Y[0] = SCREEN_HEIGHT/4;
     ballX[0] = SCREEN_WIDTH/2;
     ballY[0] = SCREEN_HEIGHT/4;
     lives = 10;
   }
   @Override
   public void actionPerformed(ActionEvent a){
    if(running){
        points();
        checkCollision();
        checkPoint();
    }
    else{
        playAgain();
    }
    repaint();
   }
   public class key extends KeyAdapter{
    public void keyPressed(KeyEvent a){
        if(running){
        switch(a.getKeyCode()){
                case KeyEvent.VK_A:
                if(inBounds1L == true){
                    ballX[0] = ballX[0] - (UNIT_SIZE);
                }
                break;
                case KeyEvent.VK_D:
                if(inBounds1R == true){
                    ballX[0] = ballX[0] + (UNIT_SIZE);
                }
                break;
                case KeyEvent.VK_W:
                ballY[0] = ballY[0] - (UNIT_SIZE);
                break;
                case KeyEvent.VK_S:
                ballY[0] = ballY[0] + (UNIT_SIZE);
                break;
                case KeyEvent.VK_LEFT:
                if(inBounds2L == true){
                    ball1X[0] = ball1X[0] - (UNIT_SIZE);
                }
                break;
                case KeyEvent.VK_DOWN:
                ball1Y[0] = ball1Y[0] + UNIT_SIZE;
                break;
                case KeyEvent.VK_UP:
                ball1Y[0] = ball1Y[0] - (UNIT_SIZE);
                break;
                case KeyEvent.VK_RIGHT:
                if(inBounds2R == true){
                    ball1X[0] = ball1X[0] + (UNIT_SIZE);
                    break;
                }
              }
            }
            else if(running == false){
                if(a.getKeyCode() == KeyEvent.VK_P){
                    retry = true;
                }
            }
        }
    }
   }


