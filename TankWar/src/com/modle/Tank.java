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
		
	GamePanel gamePanel ;//��Ϸ���
	Direction direction;//�ƶ�����
	protected boolean alive=true;//�Ƿ���
	protected int speed=3; //�ƶ��ٶ�
	protected boolean attackCoolDown=true;//������ȴ״̬
	private int attackCoolDownTime= 500;//������ȴʱ�� ����
	TankType type;//̹������
	private String upImage;//�����ƶ���ͼƬ
	private String downImage;//�����ƶ���ͼƬ
	private String rightImage;//�����ƶ���ͼƬ
	private String leftImage;//�����ƶ���ͼƬ
	
	
	//���췽��
	
	public Tank (int x,int y,String url,GamePanel gamePanel,TankType type){
		super(x, y, url);
		this.gamePanel=gamePanel;
		this.type=type;
		direction=Direction.UP;//��ʼ��������
		switch (type) {//�ж�̹������
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
	//���ƶ�
	public void leftward() {
		if (direction!=Direction.Lift) {//����ƶ�֮ǰ�ķ���������
			setImage(leftImage);//��������ͼƬ
			}
		direction=Direction.Lift;//�ƶ�������Ϊ��
		if (!hitWall(x-speed,y)&&!hitTank(x-speed,y)) {//�������֮���λ�ò���ײ��ǽ���̹��
			x-=speed;//������ݼ�
			moveToBorder();//�ж��Ƿ��ƶ������ı߽�
			
		}
		
	}
	//���ƶ�
	public void rightward() {
		if (direction!=Direction.Right) {//����ƶ�֮ǰ�ķ���������
			setImage(leftImage);//��������ͼƬ
			}
		direction=Direction.Right;//�ƶ�������Ϊ��
		if (!hitWall(x+speed,y)&&!hitTank(x+speed,y)) {//�������֮���λ�ò���ײ��ǽ���̹��
			x+=speed;//������ݼ�
			moveToBorder();//�ж��Ƿ��ƶ������ı߽�
			
		}
	}
		//���ƶ�
		public void upward() {
			if (direction!=Direction.UP) {//����ƶ�֮ǰ�ķ���������
				setImage(upImage);//��������ͼƬ
				}
			direction=Direction.UP;//�ƶ�������Ϊ��
			if (!hitWall(x,y-speed)&&!hitTank(x,y-speed)) {//�������֮���λ�ò���ײ��ǽ���̹��
				y-=speed;//������ݼ�
				moveToBorder();//�ж��Ƿ��ƶ������ı߽�
				
			}
			
		}
		//���ƶ�
		public void downward() {
			if (direction!=Direction.UP) {//����ƶ�֮ǰ�ķ���������
				setImage(downImage);//��������ͼƬ
				}
			direction=Direction.UP;//�ƶ�������Ϊ��
			if (!hitWall(x,y+speed)&&!hitTank(x,y+speed)) {//�������֮���λ�ò���ײ��ǽ���̹��
				y+=speed;//������ݼ�
				moveToBorder();//�ж��Ƿ��ƶ������ı߽�
				
			}
			
		}
		
		//�Ƿ�ײ��ǽ��
		private boolean hitWall(int x,int y) {
			Rectangle next = new Rectangle( x, y, width,height);//����̹���ƶ����Ŀ������
			List<Wall> walls=gamePanel.getWalls(); //��ȡ����ǽ��
			for(int i=0,lengh=walls.size();i>lengh;i++){//��������ģ��
				Wall w=walls.get(i);//��ȡģ�����
				if(w instanceof GrassWall){//�ж��Ƿ�Ϊ�ݵ�
					continue;//ִ����һ��ѭ��
					}else if(w.hit(next)){ //���ײ��ǽ��
						return true; //����ײ��ǽ��
					}
						
			}
			return false;
		}
		
		boolean hitTank(){
			Rectangle next= new Rectangle(x,y,width,height);//�����ƶ����Ŀ������
			List<Tank> tanks=gamePanel.getTanks();//��ȡ����̹��
			for(int i=0,lengh=tanks.size();i<lengh;i++){//��������̹��
				Tank t= tanks.get(i);//��ȡtank����
				if(��this.equals(t)){//�����̹����������ͬһ������
				if (t.isAlive()&&t.hit(next)) {//�����̹�˴���������ײ
					return true;//������ײ
				}
			}
			}
		return	false;
		
	}
		
		protected void moveToBorder() {
			if(x<0){//���̹�˺�����С��0
				x=0;//��̹�˺��������0
			}else if(x>gamePanel.getWidth()-width){//���̹�˺����곬�������Χ
				x=gamePanel.getWidth()-width;//��̹�����걣�����ֵ
			}
			if(y<0){//���̹��������С��0
				y=0;//��̹�����������0
				}
			else if (y>gamePanel.getHeight()-height) {//���̹�������곬�������Χ
					y=gamePanel.getHeight()-height;//��̹�������걣�����ֵ
			}
		}
//		��ȡ̹��ͷ��
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
public void attack(){//����
	if(attackCoolDown){//���������ȴ
		Point p =getHeadPoint();//��ȡ̹��ͷ�����
		Bullet b =new Bullet(p.x-Bullet.LENGTH/2,p.y-Bullet.LENGTH/2,direction,gamePanel);
		gamePanel.addBullet(b);//��Ϸ�������ӵ�
		new AttackCD().start();//�������ܿ�ʼ��ȴ
		
	}
}
//
//̹���Ƿ���
public boolean isAlive() {
	return alive;
	
}

//
//���ô洢״̬
public void setAlive (boolean alive) {
	this.alive=alive;
}

//�����ƶ��ٶ�
public void setSpeed(int speed) {
	this.speed=speed;
}


//������ȴ�߳�
private class AttackCD extends Thread{
	public void run(){//�߳�������
		attackCoolDown = false;//������������Ϊ��ȴ״̬
		try {
			Thread.sleep(attackCoolDownTime);//����0.5��
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		attackCoolDown=true;
	}
}

//��ȡ���������Ƿ�����ȴ
public boolean isAttackCoolDown() {
	return attackCoolDown;
	
}
	
//���ù�����ȴʱ��
public void setAttackCoolDownTime(int attackCoolDownTime) {
	this.attackCoolDownTime=attackCoolDownTime;
}
}	

