import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;


public class Missile extends Objects {
	private static final int w = 10;
	private static final int h = 10;
	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	

	
	private TankClient tc = null;
	public boolean bLive = true;
	public boolean good;

	Tank.Direction dir;
	public  Missile(int _x,int _y,Tank.Direction dir,boolean good,TankClient tc){
		super(_x,_y,w,h);
		this.dir = dir;
		this.good = good;
		this.tc = tc;
	}
	public void draw(Graphics g){
		if(this.getRect().intersects(tc.w.getRect())){
			bLive = false;			
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, width,height);
//		g.fillRect(x, YSPEED, w, h)
		g.setColor(c);
		move();
	}
	
	public boolean hitTank(Tank t){
		if(t.isGood() != this.good){
			if(bLive&&this.getRect().intersects(t.getRect())&&t.bLive){
				if(t.isGood()){
					t.life -=20;
					if(t.life<=0){
						t.bLive = false;
					}
				}
				this.bLive = false;
				return true;
			}
		}
		return false;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, w, h);
	}


	void move() {
		switch(dir) {
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
		if(x>800||y>600)
			bLive = false;
		if(x<0||y<0)
			bLive = false;		
	}
	
	public void hitTanks(List<Tank> tanks){
		for(int i = 0;i<tanks.size();i++){
			Tank tank = tanks.get(i);
			if(!tank.bLive){
				tanks.remove(tank);
			}
			if(bLive&&tank.getRect().intersects(getRect())&&tank.isGood()!=this.good){
				tank.bLive = false;
				this.bLive = false;
				Explode e = new Explode(x, y, w, h);
				tc.explodes.add(e);
			}
		}
	}

}
