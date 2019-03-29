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
	private Random random=new Random();//�����
	private Direction dir;//�ƶ�����
	private int fresh= GamePanel.FRESH;//ˢ��ʱ�䣬������Ϸ����ˢ��ʱ��
	private int MoveTimer=0;//�ƶ���ʱ��
	/**
	 * @param x
	 * @param y
	 * @param gamePanel
	 * @param type
	 */
	public Bot(int x,int y,GamePanel gamePanel,TankType type) {
		// TODO Auto-generated constructor stub
		super(x, y, ImageUtil.BOT_D_IMAGE_URL, gamePanel, type);
		dir= Direction.Down;//�ƶ�����Ĭ������
		setAttackCoolDownTime(1000);//���ù�����ȴʱ��
		//setSpeed(2);���û������ƶ��ٶ�
		
		}
	/**
	 * ̹��չ���ж�
	 */
	private void go() {
		// TODO Auto-generated method stub
		if (isAttackCoolDown()) {//���������ȴ����
			attack();//����
		}
		if (MoveTimer>=3000) {//����ƶ���ʱ������3��
			dir=randomDirection();//��������ƶ�����
			MoveTimer=0;//�����ƶ���ʱ��
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
 * ��������ƶ�����
 */
private Direction randomDirection(){
	int rnum= random.nextInt(4);//��ȡ���������Χ0-3
	switch (rnum) {//�ж������
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
 * @see com.modle.Tank#moveToBorder()��д���ƶ������ı߽��¼�
 */
@Override
protected void moveToBorder() {
	// TODO Auto-generated method stub
	if (x<0) {//���̹�˺�����С��0
		x=0;//��̹�˺��������0
		dir=randomDirection();//�����ƶ�����
	}else if (x>gamePanel.getWidth()-width) {//���̹�˺����곬�������Χ
		x=gamePanel.getWidth()-width;//��̹�˺����걣�����ֵ
		dir=randomDirection();//��������ƶ�����
	}
	if (y<0) {//���̹��������С��0
		y=0;//��̹�����������0
		dir=randomDirection();//�����ƶ�����
		
	}else if (y>gamePanel.getHeight()-height) {//���̹�������곬�������Χ
		y=gamePanel.getHeight()-height;//��̹�������걣�����ֵ
		dir=randomDirection();//��������ƶ�����
		
	}
}


@Override
boolean hitTank() {
	// TODO Auto-generated method stub
	Rectangle next=new Rectangle(x,y,width,height);//������ײλ��
	List<Tank> tanks=gamePanel.getTanks();//��ȡ����̹�˼���
	for(int i=0,lengh=tanks.size();i<lengh;i++){//����̹�˼���
		Tank t=tanks.get(i);//��ȡ̹�˶���
		if (!this.equals(t)) {//�����̹�˶����뱾̹�˶�����ͬһ��
			if (t.isAlive()&&t.hit(next)) {//����Է�˵�Ǵ��ģ������뱾��������ײ
				if (t instanceof Bot) {//����Է�Ҳ�ǵ���
					dir= randomDirection();//��������ƶ�����
					
				}
				return true;//������ײ
			}
		}
	
	}
	return false;//δ������ײ
}

/* (non-Javadoc)
 * @see com.modle.Tank#attack()
 * ��д����������ÿ�ι���ֻ��4%���ʻᴥ�����๥��������
 */
@Override
public void attack() {
	// TODO Auto-generated method stub
	int rnum=random.nextInt(100);//�������������Χ��0-99
	if (rnum<4) {
		super.attack();//ִ�и��๥������
	}
}
}	
