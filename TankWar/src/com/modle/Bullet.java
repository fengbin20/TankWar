package com.modle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;

import com.frame.GamePanel;
import com.modle.wall.BrickWall;
import com.modle.wall.IronWall;
import com.modle.wall.Wall;
import com.type.Direction;
import com.type.TankType;

public class Bullet extends VisbleImage{

	
	Direction direction;//子弹的方向
	static final int LENGTH=8;//子弹的边长
	private GamePanel gamePanel;//游戏面板
	private int speed=7;//移动速度
	private boolean alive=true;//子弹是否存活
	Color color= Color.ORANGE;//子弹颜色，橙色
	TankType owner;//发出子弹的坦克类型
	
	
	public Bullet(int x, int y, Direction direction,GamePanel gamePanel,TankType owner) {
		super(x, y, LENGTH, LENGTH);//调用父类构造方法
		this.gamePanel=gamePanel;
		this.direction=direction;
		this.owner=owner;
		init();//初始化组件
		// TODO Auto-generated constructor stub
	}


	/**
	 * 初始化组件
	 */
	private void init() {
		// TODO Auto-generated method stub
		Graphics g= image.getGraphics();//获取图片的绘图方法
		g.setColor(color.WHITE);//使用白色绘图
		g.fillRect(0, 0, LENGTH, LENGTH);//绘制一个铺满挣个图片的白色实心矩形
		g.setColor(color);//使用子弹颜色
		g.fillOval(0, 0, LENGTH, LENGTH);//绘制一个铺满整个图片的实心原型
		g.setColor(Color.BLACK);
		g.drawOval(0, 0, LENGTH-1, LENGTH-1);//给圆绘制一个黑色边框，房子会出界，宽高减小1像素
	}
	
	/**
	 *子弹移动 
	 */
	private void move() {
		// TODO Auto-generated method stub
		switch (direction) {//方向
		case UP:
			upward();
			break;
		case Down:
			downward();
			break;
		case Right:
			rightward();
			break;
		case Lift:
			liftward();
			break;
		default:
			break;
		}
	}


	private void liftward() {
		// TODO Auto-generated method stub
		x-=speed;//横坐标减少
		moveToBorder();//移动出面板边界时销毁子弹
	}


	private void rightward() {
		// TODO Auto-generated method stub
		x+=speed;
		moveToBorder();//移动出面板边界时销毁子弹
	}


	private void downward() {
		// TODO Auto-generated method stub
		y+=speed;
		moveToBorder();//移动出面板边界时销毁子弹
	}


	private void upward() {
		// TODO Auto-generated method stub
		y-=speed;
		moveToBorder();//移动出面板边界时销毁子弹
	}

	
	
	/**
	 *击中坦克 
	 */
	private void hitTank() {
		// TODO Auto-generated method stub
		List<Tank> tanks=gamePanel.getTanks();//获取所有坦克的集合
		for(int i=0,lengh=tanks.size();i<lengh;i++){//遍历坦克集合
			Tank t=tanks.get(i);
			if(t.isAlive()&&this.hit(t)){//如果坦克存在，并且击中了坦克
				switch (owner) {//判断坦克属于什么子弹
				case Palyer1://如果是玩家1的坦克，效果如下
				case Palyer2://如果是玩家2的坦克
					if (t instanceof Bot) {//如果击中的坦克是电脑
						alive=false;//子弹销毁
						t.setAlive(false);//电脑坦克阵亡
					}else if (t instanceof Tank) {//如果击中的是玩家
						alive=true;//子弹销毁.
					}
					
					break;
				case Bot://如果是电脑的子弹
					if (t instanceof Bot) {//入股欧继中的坦克是电脑
						alive=false;//子弹小鬼

					}else if (t instanceof Tank) {//如果击中的是玩家
						alive=false;//子弹销毁
						t.setAlive(false);//玩家坦克阵亡
					}
				default://默认
					alive=false;//子弹销毁
					t.setAlive(false);//坦克阵亡
				}
				
			}
			
		}
	}

	/**
	 * 击中基地
	 */
	private void hitBase() {
		// TODO Auto-generated method stub
		Base b=gamePanel.getBase();//获取基地
		if (this.hit(b)) {//如果子弹击中基地
			alive=false;//子弹销毁
			b.setAlive(false);//基地阵亡
		}
	}
	
/**
 *击中墙块 
 */
private void hitWall() {
	// TODO Auto-generated method stub
	List<Wall> walls=gamePanel.getWalls();//获取所有墙块
	for(int i=0,lengh=walls.size();i<lengh;i++){//遍历所有墙块
		Wall w=walls.get(i);//获取墙块对象
		if(this.hit(w)){//如果子弹击中墙块
			if (w instanceof BrickWall) {//如果是砖墙
				alive=false;//子弹销毁
				w.setAlive(false);//砖墙销毁
			}
			if(w instanceof IronWall){
				alive=false;//子弹销毁
			}
		}
	}
}	

	/**
	 * 如果移动出边界，销毁子弹
	 */
	private void moveToBorder() {
		// TODO Auto-generated method stub
		if (x<0||x>gamePanel.getWidth()-getWidth()||y<0||y>gamePanel.getHeight()-getHeight()) {//如果子弹坐标离开游戏面板
			dispose();//销毁子弹
		}
	}


	/**
	 * 销毁子弹
	 */
	private synchronized void dispose() {
		// TODO Auto-generated method stub
		alive=false;//存活状态变为false
	}
	
	
	/**
	 * @return获取子弹存活状态
	 */
	public boolean isAlive() {
		return alive;
	}
}
