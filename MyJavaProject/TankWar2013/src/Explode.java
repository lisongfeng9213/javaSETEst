import java.awt.Color;
import java.awt.Graphics;

public class Explode extends Objects {
	int[] diameter = {4, 7, 12, 18, 26, 32, 49, 30, 14, 6};
	public boolean bLive = true;
	TankClient tc = null;
	int step = 0;

	public Explode(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public void draw(Graphics g) {
		if(!bLive) {
			tc.explodes.remove(this);
			return;
		}
		
		if(step == diameter.length) {
			bLive = false;
			step = 0;
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		
		step ++;
	}

}
