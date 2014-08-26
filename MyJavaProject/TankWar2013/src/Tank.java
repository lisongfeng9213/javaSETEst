import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tank extends Objects {
	TankClient tc = null;
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public boolean bLive = true;
	public int step = 0;
	private static Random r = new Random();
	private boolean good;

	public int life = 100;

	private static final int ptLong = 20;

	private Tank.Direction ptDir = Direction.D;

	boolean bL = false, bU = false, bR = false, bD = false;

	enum Direction {
		L, LU, U, RU, R, RD, D, LD, STOP
	};

	private Direction dir = Direction.STOP;
	private int oldX, oldY;

	public Tank(int x, int y, int width, int height, TankClient tc,
			boolean tag, Direction dir) {
		super(x, y, width, height);
		this.tc = tc;
		this.good = tag;
		this.dir = dir;
		this.oldX = x;
		this.oldY = y;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	void move() {

		this.oldX = x;
		this.oldY = y;
		switch (dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;
		}
		if (dir != Direction.STOP) {
			ptDir = dir;
		}

		if (x < 0)
			x = 0;
		if (y < 30)
			y = 30;
		int TankWIDTH = 35;
		int TankHEIGHT = 35;
		int TankClientGAME_WIDTH = 800;
		int TankClientGAME_HEIGHT = 600;
		if (x + TankWIDTH > TankClientGAME_WIDTH)
			x = TankClientGAME_WIDTH - TankWIDTH;
		if (y + TankHEIGHT > TankClientGAME_HEIGHT)
			y = TankClientGAME_HEIGHT - TankHEIGHT;

		if (!good) {
			Direction[] dirs = Direction.values();
			if (step == 0) {
				step = r.nextInt(12) + 3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
			}
			step--;

			if (r.nextInt(40) > 38)
				this.fire();
		}
	}

	public void KeyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		Direction[] dirs = Direction.values();
		switch (key) {
		case KeyEvent.VK_A:
			if (bLive == true && isGood()) {
				for (int i = 0; i < dirs.length - 1; i++) {
					Missile m = new Missile(x + width / 2 - 3, y + height / 2
							- 3, dirs[i], good, this.tc);
					tc.missiles.add(m);
				}
			}
			break;
		case KeyEvent.VK_F2:
			if (bLive == false && isGood()) {
				tc.tank.bLive = true;
				tc.tank.life = 100;
			}
			break;
		case KeyEvent.VK_CONTROL:
			if (bLive == true && isGood()) {
				fire();
			}
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		locateDirection();
	}

	public void draw(Graphics g) {
		if (bLive) {
			Color c = g.getColor();
			if (good == false) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.PINK);
				g.drawRect(x, y - 5, 35, 5);
				g.fillRect(x, y - 5, 35 * life / 100, 5);
			}
			g.fillOval(x, y, width, height);
			g.setColor(c);
			drawPt(g);
			move();
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {

		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		locateDirection();

	}

	private void fire() {
		Missile m = new Missile(x + width / 2 - 3, y + height / 2 - 3, ptDir,
				good, this.tc);
		tc.missiles.add(m);
	}

	void locateDirection() {
		if (bL && !bU && !bR && !bD)
			dir = Direction.L;
		else if (bL && bU && !bR && !bD)
			dir = Direction.LU;
		else if (!bL && bU && !bR && !bD)
			dir = Direction.U;
		else if (!bL && bU && bR && !bD)
			dir = Direction.RU;
		else if (!bL && !bU && bR && !bD)
			dir = Direction.R;
		else if (!bL && !bU && bR && bD)
			dir = Direction.RD;
		else if (!bL && !bU && !bR && bD)
			dir = Direction.D;
		else if (bL && !bU && !bR && bD)
			dir = Direction.LD;
		else if (!bL && !bU && !bR && !bD)
			dir = Direction.STOP;
	}

	/**
	 * ÏÂÂúÊÇÊÇ»­ÅÚÍ²
	 */

	public void drawPt(Graphics g) {
		int ptW = 0, ptH = 0;
		switch (ptDir) {
		case L:
			ptW = x + width / 2 - ptLong;
			ptH = y + height / 2;
			break;
		case LU:
			ptW = x + width / 2 - ptLong;
			ptH = y + height / 2 - ptLong;
			break;
		case U:
			ptW = x + width / 2;
			ptH = y + height / 2 - ptLong;
			break;
		case RU:
			ptW = x + width / 2 + ptLong;
			ptH = y + height / 2 - ptLong;
			break;
		case R:
			ptW = x + width / 2 + ptLong;
			ptH = y + height / 2;
			break;
		case RD:
			ptW = x + width / 2 + ptLong;
			ptH = y + height / 2 + ptLong;
			break;
		case D:
			ptW = x + width / 2;
			ptH = y + height / 2 + ptLong;
			break;
		case LD:
			ptW = x + width / 2 - ptLong;
			ptH = y + height / 2 + ptLong;
			break;
		}
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.drawLine(x + width / 2, y + height / 2, ptW, ptH);
		g.setColor(c);
	}

	public void stay() {
		this.x = oldX;
		this.y = oldY;
	}

	public boolean collidesWithWall(Wall w) {
		if (this.bLive && this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}

	public boolean collidesWithBlood(Blood b) {
		if (this.bLive && good && this.getRect().intersects(b.getRect())) {
			life = 100;
			b.good = false;
			return true;
		}
		return false;
	}

	public boolean collidesWithTanks(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			Tank tank = tanks.get(i);
			if (tank != this && this.bLive
					&& this.getRect().intersects(tank.getRect())) {
				this.stay();
				return true;
			}
		}
		return false;
	}

}