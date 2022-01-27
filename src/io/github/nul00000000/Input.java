package io.github.nul00000000;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

public class Input implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {
	
	public static ArrayList<Integer> down = new ArrayList<>();
	public static ArrayList<Integer> jDown = new ArrayList<>();
	public static ArrayList<Integer> buttons = new ArrayList<>();
	public static ArrayList<Integer> jButtons = new ArrayList<>();
	public static ArrayList<Integer> jUpButtons = new ArrayList<>();
	public static int mouseX, mouseY, mouseDragX, mouseDragY;
	public static int lastMouseX, lastMouseY, lastMouseDragX, lastMouseDragY;
	public static int mouseWheelState;
	
	public static final Integer LEFT_CLICK = MouseEvent.BUTTON1;
	public static final Integer MIDDLE_CLICK = MouseEvent.BUTTON2;
	public static final Integer RIGHT_CLICK = MouseEvent.BUTTON3;

	public static void reset() {
		jDown.clear();
		jButtons.clear();
		jUpButtons.clear();
		mouseWheelState = 0;
	}
	
	public static void setLastMouse() {
		lastMouseX = mouseX;
		lastMouseY = mouseY;
		lastMouseDragX = mouseDragX;
		lastMouseDragY = mouseDragY;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(!down.contains(e.getKeyCode())) {
			down.add(e.getKeyCode());
			jDown.add(e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		down.remove((Integer) e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseDragX = mouseX = e.getX();
		mouseDragY = mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!buttons.contains(e.getButton())) {
			buttons.add(e.getButton());
			jButtons.add(e.getButton());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(buttons.contains(e.getButton())) {
			jUpButtons.add(e.getButton());
		}
		buttons.remove((Integer) e.getButton());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseWheelState = e.getWheelRotation();
	}

}
