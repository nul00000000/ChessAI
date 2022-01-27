package io.github.nul00000000;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JPanel implements Runnable {
	
	public static final String APP_NAME = "i just think they're neat";
	public static final int WIDTH = 720;
	public static final int HEIGHT = 720;
	
	private Thread thread;
	private BufferedImage b;
	private Graphics2D g;
	
	private World world;
	
	private static final long seed = System.currentTimeMillis();
	
	public static void main(String[] args) {
		JFrame w = new JFrame(APP_NAME);
		w.add(new Main());
		w.pack();
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setResizable(true);
		w.setLocationRelativeTo(null);
		w.setBackground(Color.BLACK);
		w.setVisible(true);
	}
	
	private void init() {
		b = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = b.createGraphics();
		g.setStroke(new BasicStroke(4.0f));
		g.setFont(new Font("Consolas", Font.PLAIN, 80));
		
		this.world = new World(seed);
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			this.addKeyListener(new Input());
			this.addMouseMotionListener(new Input());
			this.addMouseListener(new Input());
			thread.start();
		}
	}
	
	public static float TPS = 60;
	private int tick = 0;

	public void run() {
		init();
		
		long a;
		long b = 0;
		
		int FPS_CAP = 60;
		float SPF_MIN = 1f / FPS_CAP * 1000000000;
						
		while(true) {
			a = System.nanoTime();
			update();
			Input.reset();
			Input.setLastMouse();
//			if(Input.down.contains(KeyEvent.VK_S) || tick % 200 == 0) {
				draw();
				drawToScreen();
//			}
			
			b = System.nanoTime() - a;
			
//			if(Input.down.contains(KeyEvent.VK_S) && b < SPF_MIN) {
			if(b < SPF_MIN) {
				try {
					Thread.sleep((long)((SPF_MIN - b) / 1000000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			tick++;
		}
	}
	
	public Main() {
		super();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setFocusable(true);
		this.requestFocus();
	}
	
	private void update() {
		world.update();
	}
	
	private void draw() {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		world.draw(g);
	}
	
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(b, 0, 0, WIDTH, HEIGHT, null);
		g2.dispose();
	}
	
}
