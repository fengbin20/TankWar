package com.modle;

import java.awt.List;
import java.awt.Point;
import java.awt.Rectangle;

import javax.print.attribute.standard.RequestingUserName;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.frame.GamePanel;
import com.modle.wall.GrassWall;
import com.modle.wall.Wall;
import com.type.Direction;
import com.type.TankType;
import com.util.ImageUtil;

public class Tank extends VisbleImage{
		
	GamePanel gamePanel ;//游戏面板
	Direction direction;//移动方向
	protected boolean alive=true;//是否存活
	protected int speed=3; //移动速度
	protected boolean attackCoolDown=true;//攻击冷却状态
	private int attackCoolDownTime= 500;//攻击冷却时间 毫秒
	TankType type;//坦克类型
	private String upImage;//向上移动的图片
	private String downImage;//向下移动的图片
	private String rightImage;//向右移动的图片
	private String leftImage;//向左移动的图片
	
	
	//构造方法
	
	public Tank (int x,int y,String url,GamePanel gamePanel,TankType type){
		super(x, y, url);
		this.gamePanel=gamePanel;
		this.type=type;
		direction=Direction.UP;//初始化方向上
		switch (type) {//判断坦克类型
		case Palyer1:
			upImage=ImageUtil.PLAYER1_UP_IMAGE_URL;
			downImage=ImageUtil.PLAYER1_D_IMAGE_URL;
			rightImage=ImageUtil.PLAYER1_R_IMAGE_URL;
			leftImage=ImageUtil.PLAYER1_L_IMAGE_URL;
			break;

		case Palyer2:
			upImage=ImageUtil.PLAYER2_UP_IMAGE_URL;
			downImage=ImageUtil.PLAYER2_D_IMAGE_URL;
			rightImage=ImageUtil.PLAYER2_R_IMAGE_URL;
			leftImage=ImageUtil.PLAYER2_L_IMAGE_URL;
			break;
		case Bot:
			upImage=ImageUtil.BOT_UP_IMAGE_URL;
			downImage=ImageUtil.BOT_D_IMAGE_URL;
			rightImage=ImageUtil.BOT_R_IMAGE_URL;
			leftImage=ImageUtil.BOT_L_IMAGE_URL;
			break;
		default:
			break;
		}
		
		
	}
	//左移动
	public void leftward() {
		if (direction!=Direction.Lift) {//如果移动之前的方向不是左移
			setImage(leftImage);//更换左移图片
			}
		direction=Direction.Lift;//移动方向设为左
		if (!hitWall(x-speed,y)&&!hitTank(x-speed,y)) {//如果左移之后的位置不会撞到墙块和坦克
			x-=speed;//横坐标递减
			moveToBorder();//判断是否移动到面板的边界
			
		}
		
	}
	//右移动
	public void rightward() {
		if (direction!=Direction.Right) {//如果移动之前的方向不是左移
			setImage(leftImage);//更换右移图片
			}
		direction=Direction.Right;//移动方向设为右
		if (!hitWall(x+speed,y)&&!hitTank(x+speed,y)) {//如果左移之后的位置不会撞到墙块和坦克
			x+=speed;//横坐标递减
			moveToBorder();//判断是否移动到面板的边界
			
		}
	}
		//上移动
		public void upward() {
			if (direction!=Direction.UP) {//如果移动之前的方向不是左移
				setImage(upImage);//更换上移图片
				}
			direction=Direction.UP;//移动方向设为上
			if (!hitWall(x,y-speed)&&!hitTank(x,y-speed)) {//如果左移之后的位置不会撞到墙块和坦克
				y-=speed;//纵坐标递减
				moveToBorder();//判断是否移动到面板的边界
				
			}
			
		}
		//下移动
		public void downward() {
			if (direction!=Direction.UP) {//如果移动之前的方向不是左移
				setImage(downImage);//更换上移图片
				}
			direction=Direction.UP;//移动方向设为上
			if (!hitWall(x,y+speed)&&!hitTank(x,y+speed)) {//如果左移之后的位置不会撞到墙块和坦克
				y+=speed;//纵坐标递减
				moveToBorder();//判断是否移动到面板的边界
				
			}
			
		}
		
		//是否撞到墙块
		private boolean hitWall(int x,int y) {
			Rectangle next = new Rectangle( x, y, width,height);//创建坦克移动后的目标区域
			List<Wall> walls=gamePanel.getWalls(); //获取所有墙块
			for(int i=0,lengh=walls.size();i>lengh;i++){//遍历所有模块
				Wall w=walls.get(i);//获取模块对象
				if(w instanceof GrassWall){//判断是否为草地
					continue;//执行下一次循环
					}else if(w.hit(next)){ //如果撞到墙块
						return true; //返回撞到墙块
					}
						
			}
			return false;
		}
		
		boolean hitTank(){
			Rectangle next= new Rectangle(x,y,width,height);//创建移动后的目标区域
			List<Tank> tanks=gamePanel.getTanks();//获取所有坦克
			for(int i=0,lengh=tanks.size();i<lengh;i++){//遍历所有坦克
				Tank t= tanks.get(i);//获取tank对象
				if(！this.equals(t)){//如果此坦克与自身不是同一个对象
				if (t.isAlive()&&t.hit(next)) {//如果次坦克存活并与自身相撞
					return true;//返回相撞
				}
			}
			}
		return	false;
		
	}
		
		protected void moveToBorder() {
			if(x<0){//如果坦克横坐标小于0
				x=0;//让坦克横坐标等于0
			}else if(x>gamePanel.getWidth()-width){//如果坦克横坐标超出了最大范围
				x=gamePanel.getWidth()-width;//让坦克坐标保持最大值
			}
			if(y<0){//如果坦克纵坐标小于0
				y=0;//让坦克纵坐标等于0
				}
			else if (y>gamePanel.getHeight()-height) {//如果坦克纵坐标超出了最大范围
					y=gamePanel.getHeight()-height;//让坦克纵坐标保持最大值
			}
		}
//		获取坦克头点
		private Point getHeadPoint(){
			Point p= new Point();
			switch (direction) {
			case UP:
				p.x=x+width/2;
				p.y=y;
				break;
			case Down:
				p.x=x+width/2;
				p.y=y+height;
				break;
			case Right:
				p.x=x+width;
				p.y=y+height/2;
				break;
			case Lift:
				p.x=x;
				p.y=y+height/2;
				break;
				
					

			default:
				p=null;
			}
		return p;
		}
public void attack(){//攻击
	if(attackCoolDown){//如果攻击冷却
		Point p =getHeadPoint();//获取坦克头点对象
		Bullet b =new Bullet(p.x-Bullet.LENGTH/2,p.y-Bullet.LENGTH/2,direction,gamePanel);
		gamePanel.addBullet(b);//游戏面板添加子弹
		new AttackCD().start();//攻击功能开始冷却
		
	}
}
//
//坦克是否存活
public boolean isAlive() {
	return alive;
	
}

//
//设置存储状态
public void setAlive (boolean alive) {
	this.alive=alive;
}

//设置移动速度
public void setSpeed(int speed) {
	this.speed=speed;
}


//攻击冷却线程
private class AttackCD extends Thread{
	public void run(){//线程主方法
		attackCoolDown = false;//将攻击功能设为冷却状态
		try {
			Thread.sleep(attackCoolDownTime);//休眠0.5秒
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		attackCoolDown=true;
	}
}

//获取攻击功能是否处于冷却
public boolean isAttackCoolDown() {
	return attackCoolDown;
	
}
	
//设置攻击冷却时间
public void setAttackCoolDownTime(int attackCoolDownTime) {
	this.attackCoolDownTime=attackCoolDownTime;
}
}	

