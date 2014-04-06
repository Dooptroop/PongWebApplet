import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



@SuppressWarnings("serial")
public class PongApplet extends Applet implements Runnable, KeyListener {
	// screen object variables
	private int screenHeight=600, screenWidth=800;
	//Score Keeping
	private int p1=0;
	private int p2=0;
	private int consecutiveBounces=0;
	private Image[] images = new Image[10];
	private Image i;
	private Graphics doubleG; 
	//private String path = "C:\Users\MRD\Documents\workspace\PongApplet\src\Images\";
	
	//settings for the ball
	int bX=400,bY=25,dx=4,dy=2,radius=4;
	private Color ballColor;
	// master padle speed
	private int pdlSpd=5;
	//settings for paddle 1
	int rx=100, ry=300, padelWidth=5, padelHeight=50;
	private Color PaddleColor;
	
	//settings for paddle 2
	int px=700, py=300;
	private Color Paddle2Color;
	
	// settings for keyboard input
	private boolean[] keys = new boolean[120];
	public boolean up,down,up2,down2;
	
	
	public void init() { // called first time you view applet
		setSize(800, 600);
	}
	public void start() {
		Thread thread = new Thread(this);
		thread.start();//start the thread
		addKeyListener(this);
	
		// use this run method for the thread
	}
	@Override // from Runnable 
	public void run() {
		
		
		while(true){ // infinite loop always true
			keyUpdate();//checks key update
			if(checkHit()){Revert();}// check if ball is in contact with player
			keyCheck();// checks for user input
			wallCheck();// checks for wall collision

			
			
			repaint();//repaints the screen
			try { // try to sleep. if not throw exception to stack trace.
				Thread.sleep(17);// try sleep for 17 millis
			} catch (InterruptedException e) {
				e.printStackTrace();// print to stack
			}
		}
		
	}
	public void populateImages(){
		for(int i=0; i<images.length; i++){
			images[i]=new image(path+i+".jpg")
		}
	}
	public void stop() {
		
	}
	public void destroy() {
		
	}
	
	public void update(Graphics g) {
	//this block gets rid of the flicker problem
		
		if(i==null) {
			i=createImage(this.getSize().width, this.getSize().height);
			doubleG = i.getGraphics();
		}
		doubleG.setColor(getBackground());// sets to background and clears screen
		doubleG.fillRect(0, 0, this.getSize().width, this.getSize().height);
		
		doubleG.setColor(getForeground());// sets back to paint color
		paint(doubleG);// paints
		
		g.drawImage(i, 0, 0, this);
		// end of double buffer paint
	

		
	}
	
	public void paint(Graphics g) {
		drawBall(g);
		drawPaddles(g);
		drawDivider(g);
		drawBoard(g);
	}
	private void drawBoard(Graphics g) {
		g.drawRect(300, 10, 200, 100);
		
	}
	private void drawBall(Graphics g) {
		g.setColor(Color.black);
		g.fillOval(bX-radius, bY-radius, radius*2, radius*2);
		
	}
	private void drawPaddles(Graphics g) {
		g.fillRect(rx, ry, padelWidth, padelHeight);
		g.fillRect(px, py, padelWidth, padelHeight);
		
	}
	private void drawDivider(Graphics g) {
		g.drawLine(screenWidth/2, 0, screenWidth/2, screenHeight);
		
	}
	public boolean checkHit(){
		if(bX>rx&&bX<rx+padelWidth||bX>px&&bX<px+padelWidth){
			if(bY>ry&&bY<ry+padelHeight||bY>py&&bY<py+padelHeight){
				return true;
			}
		}
		return false;
	}
	public void keyCheck() {
		if(up) ry-=pdlSpd;
		if(down)ry+=pdlSpd;
		if(up2) py-=pdlSpd;
		if(down2) py+=pdlSpd;
	}
	public void Revert(){
		dx=-dx;
		//dy=-dy;
	}
	public void wallCheck(){
		if(ry==bX && ry==bY){
			dx = -dx;// revert path
		}
		// wall collisions check x
		if(bX+dx>this.getWidth() -radius-1){
			bX = this.getWidth() - radius - 1;// ball will stop at border
			dx = -dx;// makes it bounce off the wall -RIGHTWall-
			// will need to count wall taps for pong score
		} else if(bX+dx<0+radius) {// this if makes the ball bounce off the left
			bX = 0+radius;
			dx = -dx;// revert path
		}
		else {
			bX+=dx;
		}
		
		// now for y 
		if(bY+dy>this.getHeight() -radius-1){
			bY = this.getHeight() - radius - 1;// ball will stop at border
			dy = -dy;// makes it bounce off the wall -LeftWall-
			
		} else if(bY+dy<0+radius) {// this if makes the ball bounce off the left
			bY = 0+radius;
			dy = -dy;// revert path Y
		}
		else {
			bY+=dy;
		}
	}
	
	public void keyUpdate() {//-------------------------------------------update()
		up =  keys[KeyEvent.VK_W];
		up2 = keys[KeyEvent.VK_UP]; 
		down = keys[KeyEvent.VK_S];
		down2 = keys[KeyEvent.VK_DOWN];
//		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
//		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		
		for(int i=0; i<keys.length; i++) {
			if (keys[i]) {
				System.out.println("KEY:" + i);
			}
		}
	}
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		//e.getKeyCode();
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
