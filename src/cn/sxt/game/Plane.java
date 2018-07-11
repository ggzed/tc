package cn.sxt.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;







public class Plane extends GameObject {
	boolean left,right,up,down;
	boolean live=true;
	
	
	public 	void addDierection(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left=true;
			break;
		case KeyEvent.VK_RIGHT:
			right=true;
			break;
		case KeyEvent.VK_UP:
			up=true;
			break;
		case KeyEvent.VK_DOWN:
			down=true;
			break;

		default:
			break;
		}
	}
		public 	void minusDierection(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				left=false;
				break;
			case KeyEvent.VK_RIGHT:
				right=false;
				break;
			case KeyEvent.VK_UP:
				up=false;
				break;
			case KeyEvent.VK_DOWN:
				down=false;
				break;

			default:
				break;
			}
		
		
	}
	@Override
	public void drawself(Graphics g) {
		if(live) {
			super.drawself(g);
			int speed=3;
			if(left) {
				x-=speed;
				
			}

			if(right) {
				x+=speed;
			}

			if(up) {
				y-=speed;
			}

			if(down) {
				y+=speed;
			}
		}
		
	}
	public Plane(Image img,double x,double y) {
		this.img=img;
		this.x=x;
		this.y=y;
		this.speed=3;
		this.width=img.getWidth(null);
		this.height=img.getHeight(null);
		
	}

}
