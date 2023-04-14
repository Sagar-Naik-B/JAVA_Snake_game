package sanke;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener
{
	static final int SCREEN_WIDTH = 1000;
	static final int SCREEN_HEIGHT = 700;
	static final int UNIT_SIZE = 20;
	static final int GAME_UNITS =(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE ;
	static final int DELAY = 95; //speed of the object
	final int x[] = new int[GAME_UNITS];// to hold the x axis of the object(snake)
	final int y[] = new int[GAME_UNITS];// to hold the y axis of the object(snake)
	int bodyParts = 6; //Intaially body size of snake
	int applesEaten ;
	int appleX; //x co-ordinate of the apple located
	int appleY; //Y co-ordinate of the apple located
	char direction = 'R';//it the direction of the object R,L,U,D
	boolean running = false;
	Timer timer;
	Random random;
	
	
	GamePanel()
	{
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame()
	{
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);		
		
	}
	public void draw(Graphics g)
	{
		if(running)
		{
			 /*for(int i=1;i<SCREEN_HEIGHT/UNIT_SIZE;i++)
			 {
				 g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				 g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			 }*/
			 g.setColor(Color.red);//color of apple
			 g.fillOval(appleX ,appleY ,UNIT_SIZE ,UNIT_SIZE);
			 
			 for(int i =0;i<bodyParts;i++)
			 {
				 if(i==0)
				 {
					 g.setColor(Color.green);
					 g.fillRect(x[i],y[i],UNIT_SIZE ,UNIT_SIZE);
				 }
				 else
				 {
					 g.setColor(new Color(14,180,0));//Snake Color (14,180,0)
					 g.fillRect(x[i],y[i],UNIT_SIZE ,UNIT_SIZE);
				 }
			 }
			 g.setColor(Color.red);
			 g.setFont(new Font("Ink Free",Font.BOLD,40));
			 FontMetrics metrics = getFontMetrics(g.getFont());
			 g.drawString("SCORE :"+applesEaten,(SCREEN_WIDTH - metrics.stringWidth("SCORE :"+applesEaten))/2,g.getFont().getSize());
		}
		else
		{
			gameOver(g);
		}
	}
	public void newApple()
	{
		appleX =random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY =random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	public void move()
	{
		  for(int i = bodyParts;i>0;i--)
		  {
			  x[i] = x[i-1];
			  y[i] = y[i-1];
		  }
		  
		  switch(direction)
		  {
		  case 'U' :
			  y[0] = y[0] - UNIT_SIZE;
			  break;
		  case 'D' :
			  y[0] = y[0] + UNIT_SIZE;
			  break;
		  case 'L' :
			  x[0] = x[0] - UNIT_SIZE;
			  break;
		  case 'R' :
			  x[0] = x[0] + UNIT_SIZE;
			  break; 
		  }
	}
	public void checkApple()
	{
		if((x[0] == appleX) && (y[0] == appleY))
		{
			bodyParts++;
			applesEaten++;
			newApple();
			
		}
	}
	public void checkColllisions()
	{
		//check if head Collides with Body
		for(int i=bodyParts;i>0;i--)
		{
			if((x[0] == x[i])&&(y[0] == y[i]))
			{
				running = false;
			}
		}
		//check if body touches LEFT BORDER
		if(x[0] < 0)
		{
			running = false;
		}
		//check if body touches RIGHT BORDER
		if(x[0] > SCREEN_WIDTH)
		{
			running = false;
		}
		//check if body touches TOP BORDER
		if(y[0] < 0)
		{
			running = false;
		}
		//check if body touches BOTTOM BORDER
		if(y[0] > SCREEN_HEIGHT)
		{
			running = false;
		}
		if(!running)
		{
			timer.stop();
		}
	}
	public void gameOver(Graphics g)
	{
		//displace the score
		 g.setColor(Color.red);
		 g.setFont(new Font("Ink Free",Font.BOLD,40));
		 FontMetrics metrics1 = getFontMetrics(g.getFont());
		 g.drawString("SCORE :"+applesEaten,(SCREEN_WIDTH - metrics1.stringWidth("SCORE :"+applesEaten))/2,g.getFont().getSize());
		 
		//game Over text
		g.setColor(Color.blue);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("GAME OVER",(SCREEN_WIDTH - metrics2.stringWidth("GAME OVER"))/2,SCREEN_HEIGHT/2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(running)
		{
			move();
			checkApple();
			checkColllisions();
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			 switch(e.getKeyCode())
			 {
			 case KeyEvent.VK_LEFT :
				 if(direction != 'R')
				 {
					 direction = 'L';
				 }
				break;
			 case KeyEvent.VK_RIGHT :
				 if(direction != 'L')
				 {
					 direction = 'R';
				 }
				break;
			 case KeyEvent.VK_UP :
				 if(direction != 'D')
				 {
					 direction = 'U';
				 }
				break;
			 case KeyEvent.VK_DOWN :
				 if(direction != 'U')
				 {
					 direction = 'D';
				 }
				break;
			 }
		}
	}

}
