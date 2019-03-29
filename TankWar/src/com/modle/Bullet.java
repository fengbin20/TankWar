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

	
	Direction direction;//�ӵ��ķ���
	static final int LENGTH=8;//�ӵ��ı߳�
	private GamePanel gamePanel;//��Ϸ���
	private int speed=7;//�ƶ��ٶ�
	private boolean alive=true;//�ӵ��Ƿ���
	Color color= Color.ORANGE;//�ӵ���ɫ����ɫ
	TankType owner;//�����ӵ���̹������
	
	
	public Bullet(int x, int y, Direction direction,GamePanel gamePanel,TankType owner) {
		super(x, y, LENGTH, LENGTH);//���ø��๹�췽��
		this.gamePanel=gamePanel;
		this.direction=direction;
		this.owner=owner;
		init();//��ʼ�����
		// TODO Auto-generated constructor stub
	}


	/**
	 * ��ʼ�����
	 */
	private void init() {
		// TODO Auto-generated method stub
		Graphics g= image.getGraphics();//��ȡͼƬ�Ļ�ͼ����
		g.setColor(color.WHITE);//ʹ�ð�ɫ��ͼ
		g.fillRect(0, 0, LENGTH, LENGTH);//����һ����������ͼƬ�İ�ɫʵ�ľ���
		g.setColor(color);//ʹ���ӵ���ɫ
		g.fillOval(0, 0, LENGTH, LENGTH);//����һ����������ͼƬ��ʵ��ԭ��
		g.setColor(Color.BLACK);
		g.drawOval(0, 0, LENGTH-1, LENGTH-1);//��Բ����һ����ɫ�߿򣬷��ӻ���磬��߼�С1����
	}
	
	/**
	 *�ӵ��ƶ� 
	 */
	private void move() {
		// TODO Auto-generated method stub
		switch (direction) {//����
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
		x-=speed;//���������
		moveToBorder();//�ƶ������߽�ʱ�����ӵ�
	}


	private void rightward() {
		// TODO Auto-generated method stub
		x+=speed;
		moveToBorder();//�ƶ������߽�ʱ�����ӵ�
	}


	private void downward() {
		// TODO Auto-generated method stub
		y+=speed;
		moveToBorder();//�ƶ������߽�ʱ�����ӵ�
	}


	private void upward() {
		// TODO Auto-generated method stub
		y-=speed;
		moveToBorder();//�ƶ������߽�ʱ�����ӵ�
	}

	
	
	/**
	 *����̹�� 
	 */
	private void hitTank() {
		// TODO Auto-generated method stub
		List<Tank> tanks=gamePanel.getTanks();//��ȡ����̹�˵ļ���
		for(int i=0,lengh=tanks.size();i<lengh;i++){//����̹�˼���
			Tank t=tanks.get(i);
			if(t.isAlive()&&this.hit(t)){//���̹�˴��ڣ����һ�����̹��
				switch (owner) {//�ж�̹������ʲô�ӵ�
				case Palyer1://��������1��̹�ˣ�Ч������
				case Palyer2://��������2��̹��
					if (t instanceof Bot) {//������е�̹���ǵ���
						alive=false;//�ӵ�����
						t.setAlive(false);//����̹������
					}else if (t instanceof Tank) {//������е������
						alive=true;//�ӵ�����.
					}
					
					break;
				case Bot://����ǵ��Ե��ӵ�
					if (t instanceof Bot) {//���ŷ���е�̹���ǵ���
						alive=false;//�ӵ�С��

					}else if (t instanceof Tank) {//������е������
						alive=false;//�ӵ�����
						t.setAlive(false);//���̹������
					}
				default://Ĭ��
					alive=false;//�ӵ�����
					t.setAlive(false);//̹������
				}
				
			}
			
		}
	}

	/**
	 * ���л���
	 */
	private void hitBase() {
		// TODO Auto-generated method stub
		Base b=gamePanel.getBase();//��ȡ����
		if (this.hit(b)) {//����ӵ����л���
			alive=false;//�ӵ�����
			b.setAlive(false);//��������
		}
	}
	
/**
 *����ǽ�� 
 */
private void hitWall() {
	// TODO Auto-generated method stub
	List<Wall> walls=gamePanel.getWalls();//��ȡ����ǽ��
	for(int i=0,lengh=walls.size();i<lengh;i++){//��������ǽ��
		Wall w=walls.get(i);//��ȡǽ�����
		if(this.hit(w)){//����ӵ�����ǽ��
			if (w instanceof BrickWall) {//�����שǽ
				alive=false;//�ӵ�����
				w.setAlive(false);//שǽ����
			}
			if(w instanceof IronWall){
				alive=false;//�ӵ�����
			}
		}
	}
}	

	/**
	 * ����ƶ����߽磬�����ӵ�
	 */
	private void moveToBorder() {
		// TODO Auto-generated method stub
		if (x<0||x>gamePanel.getWidth()-getWidth()||y<0||y>gamePanel.getHeight()-getHeight()) {//����ӵ������뿪��Ϸ���
			dispose();//�����ӵ�
		}
	}


	/**
	 * �����ӵ�
	 */
	private synchronized void dispose() {
		// TODO Auto-generated method stub
		alive=false;//���״̬��Ϊfalse
	}
	
	
	/**
	 * @return��ȡ�ӵ����״̬
	 */
	public boolean isAlive() {
		return alive;
	}
}
