import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame {
	public static final int WIDTH = 800;
	public static final int HIGHT = 600;
	Tank tank = new Tank(50, 50, 35, 35, this, true, Tank.Direction.STOP);
	Image offScreenImage = null;

	List<Missile> missiles = new ArrayList<Missile>();
	List<Explode> explodes = new ArrayList<Explode>();
	List<Tank> tanks = new ArrayList<Tank>();
	Blood bd = new Blood(240, 240, 15, 15);
	Wall w = new Wall(300, 100, 300, 40);

	public static void main(String[] args) {
		new TankClient().launchFrame();
	}

	public void launchFrame() {

		setBackground(Color.GRAY);
		setSize(WIDTH, HIGHT);
		setResizable(false);
		setVisible(true);
		setTitle("2014版坦克大战");
		/**
		 * 增加了一个内部类
		 */
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		/**
		 * 新建线程，把其加入到Thread其中
		 */
		addKeyListener(new KeyMonitor());
		new Thread(new TankClientThread()).start();
	}

	public void paint(Graphics g) {

		g.drawString("missiles count:" + missiles.size(), 10, 50);
		g.drawString("explodes count:" + explodes.size(), 10, 70);
		g.drawString("tanks    count:" + tanks.size(), 10, 90);
		g.drawString("tanks     life:" + tank.life, 10, 110);
		if (tanks.size() == 0) {
			for (int i = 0; i < 10; i++) {
				tanks.add(new Tank(50 + 40 * (i + 1), 50, 35, 35, this, false,
						Tank.Direction.D));
			}
		}

		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.collidesWithWall(w);
			t.collidesWithTanks(tanks);
			t.draw(g);
		}
		if (bd.good) {
			bd.draw(g);
		}
		tank.draw(g);
		tank.collidesWithTanks(tanks);
		tank.collidesWithWall(w);
		tank.collidesWithBlood(bd);
		w.draw(g);
		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			if (m.hitTank(tank)) {
				Explode e = new Explode(tank.x, tank.y, tank.width, tank.height);
				explodes.add(e);
			}
			m.hitTanks(tanks);
			if (m.bLive == true) {
				m.draw(g);
			} else
				missiles.remove(m);
		}

		for (int i = 0; i < explodes.size(); i++) {
			Explode e = explodes.get(i);
			if (e.bLive == true) {
				e.draw(g);
			} else
				explodes.remove(e);
		}

	}

	public void update(Graphics g) {

		if (offScreenImage == null) {
			offScreenImage = this.createImage(WIDTH, HIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GRAY);
		gOffScreen.fillRect(0, 0, WIDTH, HIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);

	}

	public class TankClientThread implements Runnable {
		@Override
		public void run() {
			while (true) {

				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public class KeyMonitor extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			tank.KeyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			tank.keyPressed(e);
		}

	}

}