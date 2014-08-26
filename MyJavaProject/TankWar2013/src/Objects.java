import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public abstract class Objects {
	public int x;
	public int y;
	public int width;
	public int height;
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public Objects(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.GREEN);
		g.fillOval(x, y, width,height);
		g.setColor(c);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}
}
