import java.awt.Color;
import java.awt.Graphics;


public class Wall extends Objects{

	public Wall(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.yellow);
		g.fillRect(x, y, width,height);
		g.setColor(c);
	}
}
