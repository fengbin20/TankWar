package com.modle;

import java.awt.List;
import java.awt.Rectangle;
import java.util.Random;

import com.frame.GamePanel;
import com.type.Direction;
import com.type.GameType;
import com.type.TankType;
import com.util.ImageUtil;

public class Bot extends Tank{
	private Random random=new Random();//随机类
	private Direction dir;//移动方向
	private int fresh= GamePanel.FRESH;//刷新时间，采用游戏面板的刷新时间
	private int MoveTimer=0;//移动计时器
	/**
	 * @param x
	 * @param y
	 * @param gamePanel
	 * @param type
	 */
	public Bot(int x,int y,GamePanel gamePanel,TankType type) {
		// TODO Auto-generated constructor stub
		super(x, y, ImageUtil.BOT_D_IMAGE_URL, gamePanel, type);
		dir= Direction.Down;//移动方向默认向下
		setAttackCoolDownTime(1000);//设置攻击冷却时间
		//setSpeed(2);设置机器人移动速度
		
		}
	/**
	 * 坦克展开行动
	 */
	private void go() {
		// TODO Auto-generated method stub
		if (isAttackCoolDown()) {//如果攻击冷却结束
			attack();//攻击
		}
		if (MoveTimer>=3000) {//如果移动计时器超过3秒
			dir=randomDirection();//随机调整移动方向
			MoveTimer=0;//重置移动计时器
		}else{
			MoveTimer+=fresh;
		}
		switch (dir) {
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
			leftward();
			break;
		default:
			break;
		}
	}

/**
 * @return
 * 随机调整移动方向
 */
private Direction randomDirection(){
	int rnum= random.nextInt(4);//获取随机数，范围0-3
	switch (rnum) {//判断随机数
	case 0:
		return Direction.UP;
	case 1:
		return Direction.Down;
	case 2:
		return Direction.Right;
	default:
		return Direction.Lift;

	}
	
	}

/* (non-Javadoc)
 * @see com.modle.Tank#moveToBorder()重写了移动到面板的边界事件
 */
@Override
protected void moveToBorder() {
	// TODO Auto-generated method stub
	if (x<0) {//如果坦克横坐标小于0
		x=0;//让坦克横坐标等于0
		dir=randomDirection();//调整移动方向
	}else if (x>gamePanel.getWidth()-width) {//如果坦克横坐标超出了最大范围
		x=gamePanel.getWidth()-width;//让坦克横坐标保持最大值
		dir=randomDirection();//随机调整移动方向
	}
	if (y<0) {//如果坦克纵坐标小于0
		y=0;//让坦克纵坐标等于0
		dir=randomDirection();//调整移动方向
		
	}else if (y>gamePanel.getHeight()-height) {//如果坦克纵坐标超出了最大范围
		y=gamePanel.getHeight()-height;//让坦克纵坐标保持最大值
		dir=randomDirection();//随机调整移动方向
		
	}
}


@Override
boolean hitTank() {
	// TODO Auto-generated method stub
	Rectangle next=new Rectangle(x,y,width,height);//创建碰撞位置
	List<Tank> tanks=gamePanel.getTanks();//获取所有坦克集合
	for(int i=0,lengh=tanks.size();i<lengh;i++){//遍历坦克集合
		Tank t=tanks.get(i);//获取坦克对象
		if (!this.equals(t)) {//如果次坦克对象与本坦克对象不是同一个
			if (t.isAlive()&&t.hit(next)) {//如果对方说是存活的，并且与本对象发生碰撞
				if (t instanceof Bot) {//如果对方也是电脑
					dir= randomDirection();//随机调整移动方向
					
				}
				return true;//发生碰撞
			}
		}
	
	}
	return false;//未发生碰撞
}

/* (non-Javadoc)
 * @see com.modle.Tank#attack()
 * 重写攻击方法，每次攻击只有4%概率会触发父类攻击方方法
 */
@Override
public void attack() {
	// TODO Auto-generated method stub
	int rnum=random.nextInt(100);//创建随机数，范围在0-99
	if (rnum<4) {
		super.attack();//执行父类攻击方法
	}
}
}	
