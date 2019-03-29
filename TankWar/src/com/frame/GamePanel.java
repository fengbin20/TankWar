package com.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

import javax.naming.InitialContext;
import javax.swing.Box;
import javax.swing.JPanel;

import com.modle.Base;
import com.modle.Boom;
import com.modle.Bot;
import com.modle.Bullet;
import com.modle.Map;
import com.modle.Tank;
import com.modle.wall.Wall;
import com.type.GameType;
import com.type.TankType;
import com.util.ImageUtil;

public class GamePanel extends JPanel implements KeyListener{//keylistener �����¼�����
	public static final int FRESH=20;
	private BufferedImage image;//�������ʾ����ͼƬ
	private Graphics2D g2;//ͼƬ�Ļ�ͼ����
	private MainPanel frame;//������
	private GameType gameType;//��Ϸģʽ
	private Tank play1,play2;//���1�����2
	private boolean y_key,s_key,w_key,a_key,d_key,up_key,down_key,left_key,right_key,num_key;//���°����ı�־
	private int level;//�ؿ�ֵ
	private List<Bullet> bullets;//�����ӵ�����
	private volatile List<Tank> allTanks;//����̹�˼���
	private List<Tank> botTanks;//���ез�̹�˼���
	private final int botCount =20;//����̹������
	private int botReadyCount= botCount;//׼�������ĵ���̹������
	private int botSurplusCount= botCount;//����̹�˵�ʣ������
	private void botMaxInMap= 6;//�������̹������
	private int botX[]={10,367,754};//����λ������
	private List<Tank> playerTanks;//���̹�˼���
	private volatile boolean finish= false;//��Ϸ�Ƿ����
	private Base base;//����
	private List<Wall> walls;//����ǽ��
	private List<Boom> boomImage;//̹��������ı�ըЧ������
	private Random r =new Random();//���������
	private int createBotTimer=0;//�������Լ�ʱ��
	private Tank survivor;//��� �Ҵ��ߣ����ڻ������һ����ըЧ��
	
	
//	���췽��
	
	public GamePanel(MainPanel frame,int level,GameType gameType) {
		this.frame=frame;
		this.level=level;
		this.gameType=gameType;
		setBackground(Color.WHITE);//���ñ���
		init();//��ʼ�����
		Thread t=new FreshThead();//������Ϸˢ��֡
		t.start();//�����߳�
		addListener();//��������
		// TODO Auto-generated constructor stub
	}




private void init() {
	bullets =new ArrayList<Bullet>();//ʵ�����ӵ��ϼ�
	allTanks =new ArrayList<>();//ʵ��������̹�˺ϼ�
	walls= new ArrayList<>();//ʵ��������ǽ��ļ���
	boomImage= new ArrayList<>();//ʵ�������б�ըЧ���ļ���
	
	image= new BufferedImage(794, 572, BufferedImage.TYPE_INT_BGR);//ʵ������ͼƬ���������ʵ�ʴ�С
	g2= image.createGraphics();//��ȡ��ͼƬ��ͼ����
	
	playerTanks=new ArrayList<>();//ʵ����̹�˼���
	play1=new Tank(278, 537, ImageUtil.PLAYER1_UP_IMAGE_URL,this, TankType.Palyer1);
	if (gemeType==GameType.Two_Player) {//�ж���Ϸģʽ-�����˫��ģʽ
		play2= new Tank(448, 537, ImageUtil.PLAYER2_UP_IMAGE_URL, this, TankType.Palyer2);
		playerTanks.add(play2);//	���̹�˼���������2
				
	}
	playerTanks.add(play1);//	���̹�˼���������1
	
	botTanks=new Vector<>();//ʵ����̹�˼���
	botTanks.add(new Bot(botX[0],1,this,TankType.Bot));//�ڵ�һ��λ����ӵ���
	botTanks.add(new Bot(botX[1],1,this,TankType.Bot));//�ڵڶ���λ����ӵ���
	botTanks.add(new Bot(botX[2],1,this,TankType.Bot));//�ڵ�����λ����ӵ���
	botReadyCount -=3;//׼��������̹��������ȥ��ʼ������
	allTanks.addAll(playerTanks);//����̹�˼���������̹�˼���
	
	allTanks.addAll(botTanks);//����̹�˼�����ӵ���̹�˼���
	base = new Base(367,532);//ʵ������
	initWalls();//��ʼ����ͼ�е�ǽ��
	
	
	// TODO Auto-generated method stub
	
}
	




//	�������


private void addListener() {
	frame.addKeyListener(this);//������������̼�����������ʵ��Keylistener�ӿ�
	// TODO Auto-generated method stub
	
}

//��ʼ����ͼǽ��
	
private void initWalls() {
	Map map=Map.getMap(level);//��ȡ��ǰ�ؿ��ĵ�ͼ����
	walls.addAll(map.getWalls());
	walls.add(base);
	// TODO Auto-generated method stub
	
}


	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		paintTankAction();//ִ��̹�˶���
		CreateBot();//ѭ����������̹��	
		paintImage();//������ͼƬ
		g.drawImage(image, 0, 0, this);//����ͼƬ���Ƶ������
		
	}
	





//	������ͼƬ
//	����˳��Ϊҳ��ͼƬչʾ��
	
	private void paintImage() {
		// TODO Auto-generated method stub
		g2.setColor(Color.WHITE);//ʹ�ð�ɫ
		g2.fillRect(0, 0, image.getWidth(), image.getHeight());//���һ����������ͼƬ�İ�ɫ����
		paintBoom();//���Ʊ�ըЧ��
		paintBotCount();//����Ļ��������ʣ��̹������
		paintBotTanks();//���Ƶ���̹��
		paintPlayerTanks();//�������̹��
		allTanks.addAll(playerTanks);//̹�˼���������̹�˼���
		allTanks.addAll(botTanks);
		panitWall();//����ǽ��
		panitBullets();//�����ӵ�
		
		if (botSurplusCount==0) {//������е��Զ�������
			stopThread();//������Ϸ֡ˢ���߳�
			painBotCount();//����Ļ��������ʣ��̹������
			g2.setFont(new Font("����", Font.BOLD, 50));//���û�ͼ����
			g2.setColor(Color.GREEN);//������ɫ
			g2.drawString("WIN", 250, 400);//ָ������λ�û�������
			gotoNextLevel();//������һ��
		}
		if (gameType==GameType.One_Player) {
			if(!play1.isAlive()){//����������
				stopThread();//������Ϸ֡��ˢ��
				boomImage.add(new Boom(play1.x,play2.y));//������1��ըЧ��
				panitBoom();//���Ʊ�ըЧ��
				panitGameOver();//����Ļ�м���� game over
				gotoPrevisousLevel();//���½��뱾�ؿ�
			}
			
		}else   {
			if (play1.isAlive()&& !play2.isAlive()) {//������1���Ҵ�
				survivor =play1;//�Ҵ������1
				}else if (!play1.isAlive()&&play2.isAlive()) {//������2���Ҵ�
					survivor=play2;//�Ҵ������2
					
				}else if (!(play1.isAlive()||play2.isAlive())) {//����������̹��ȫ������
					stopThread();//������Ϸ֡ˢ���߳�
					boomImage.add(new Boom(survivor.x,survivor.y));//����Ҵ��߱�ըЧ��
					panitBoom();//���Ʊ���Ч��
					panitGameOver();//����Ļ�м���� game over
					gotoPrevisousLevel();//���½��뱾�ؿ�
					
				}
			
		}
		
		if (!base.isAlive()) {
			stopThread();//������Ϸ֡ˢ���߳�
			panitGameOver();//����Ļ�м���� game over
			base.setImage(ImageUtil.BASEBOO_IMAGE_URL);
			gotoPrevisousLevel();//���½��뱾�ؿ�
		}
		g2.drawImage(base.getImage(), base.x, base.y, this);//���ƻ���
	}	
	
//	����Ļ��������ʣ��̹������
	private void paintBotCount() {
		// TODO Auto-generated method stub
		g2.setColor(Color.BLUE);//ʹ����ɫ
		g2.drawString("�з�̹��ʣ��"+botSurplusCount, 337, 15);//��ָ��λ����������ַ���
	}
	
//	��Ļ�������game over
	private void paintGameOver() {
		// TODO Auto-generated method stub
		g2.setFont(new Font("����", Font.BOLD, 50));//���û�ͼ����ʹ�С
		g2.setColor(Color.RED);//��ɫΪ��ɫ
		g2.drawString("GAME OVER", 250, 400);//ָ��λ�û�������
	}
//	���Ʊ�ըЧ��
	private void panitBoom() {
		// TODO Auto-generated method stub
		for(int i;i<boomImage.get();i++){//ѭ��������ըЧ������
			Boom boom=boomImage.get(i);//��ȡ��ը����
			if (boom.isAlive()) {//�����ըЧ����Ч
				boom.show(g2);//չʾ��ըЧ��
				
			}else{
				boomImage.remove(i);//�ڼ�����ɾ���˱�ը
				i--;//ѭ������-1����֤�´�ѭ����i��ֵ������i+1��һ����Ч�������ϣ��ҷ�ֹ�±�Խ��
			}
			
			
		}
	}
	
//	����ǽ��
	private void panitWalls() {
		// TODO Auto-generated method stub
		for(int i=0;i<walls.size();i++){
			Wall w=walls.get(i);//��ȡǽ�����
			if (w.isAlive()) {//���ǽ�����
				g2.drawImage(w.getImage(), w.x, w.y, this);
				
			}else{//���ǽ����Ч
				walls.remove(i);
				i--;
			}
		}
	}
	//�����ӵ�
	private void panitBullets() {
		// TODO Auto-generated method stub
		for(int i=0;i<bullets.size();i++){//ѭ�������ӵ�����
			Bullet b=bullest.get(i);//��ȡ�ӵ�����
			if (b.isAlive()) {//����ӵ���Ч
				b.move();//�ӵ��ƶ�����
				b.hitBase();//�ӵ�ִ�м��л��ذ��
				b.hitWall();//�ӵ�ִ�м���ǽ���ж�
				b.hitTank();//�Զ�ִ�л���̹���ж�
				g2.drawImage(b.getImage(),b.x,b.y,this);//�����ӵ�
			}else{//����ӵ���Ч
				bullets.remove(i);//�ڼ�����ɾ�����ӵ�
				i--;//ѭ������-1����֤�´�ѭ��I��ֵ������i+1���Ա���Ч�������ϣ���ֹ�±�Խ��
			}
			
		}
	}
//	���Ƶ���̹��
	
	private void panitBotTanks() {
		// TODO Auto-generated method stub
		for(int i=0;i<botTanks.size;i++){//ѭ����������̹��
			Bot t=botTanks.get(i);//��ȡ����̹�˶���
			if(t.isAlive()){//���̹�˴��
				t.go();//���Ըɿ�չ���ж�
				g2.drawImage(t.getImage(), t.x, t.y, this);//����̹��
			}else{//���̹������
				botTanks.remove(i);//����ɾ����̹��
				i--;//ѭ������-1����֤�´�ѭ��i��ֵ������i+1���Ա���Ч�������ϣ��ҷ�ֹ�±�Խ��
				boomImage.add(new Boom(t.x,t.y));//̹��λ�ô�����ըЧ��
				decreaseBot();//ʣ��̹������-1
			}
			
		}
	}
//	�������̹��
	private void painplayerTanks() {
		// TODO Auto-generated method stub
		for(int i=0;i<playerTanks.size();i++){//ѭ���������̹��
			Tank t=playerTanks.get(i);//��ȡ���̹��
			if (t.isAlive()) {//���̹�˴��
				g2.drawImage(t.getImage(), t.x, t.y, this);//����̹��
				
			}else{//̹������
				playerTanks.remove(i);//������ɾ����̹��
				i--;//ѭ������-1����֤�´�ѭ��i��ֵ������i+1���Ա�����Ч�������ϣ���ֹ�±�Խ��
				boomImage.add(new Boom(t.x,t.y));
			}
		}
	
	
	}
//	������Ϸˢϴ
	
	private synchronized void stopThread() {
		// TODO Auto-generated method stub
		frame.removeKeyListener(this);//������ɾ����������¼���������
		finish=true;//��Ϸֹͣ��־
	}
//	��Ϸ֡ˢ���߳�
	private class FreshThread extends Thread {
		// TODO Auto-generated method stub
		public void run() {//�߳�������
			// TODO Auto-generated method stub
		while(!finish){//�����Ϸδֹͣ
			repaint();//ִ�б������ط���
			try {
				Thread.sleep(FRESH);//ָ��ʱ�������
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
	}
	
//	��ӵ���̹�ˣ��������̹��δ�ﵽ���ֵ��ÿ4��֮������������λ�����ѡ����һ����������̹��
	private void Createbot() {
		// TODO Auto-generated method stub
		createBotTimer+=FRESH;//��ʱ������ˢ��ʱ�����
		//�����ϵ���С�ڳ��������ʱ������׼���ϳ���̹��������0�����Ҽ�ʱ����¼�Ѿ���ȥ4��
		if (botTanks.size()<botMaxInMap&&botReadyCount>0&&createBotTimer>=4000) {
			int index=r.nextInt(3);//�����ȡ0����1����2����һ��ֵ
			Rectangle bornRect= new Rectangle(botX[index], 1, 35, 35);//����̹�������������
			for(int i=0,lengh=allTanks.size();i<lengh;i++){//ѭ����������̹�˼���
				Tank t=allTanks.get(i);//��ȡ̹�˶���
				if(t.isAlive()&&t.hit(bornRect)){//������̴��������λ���غϲ��Ҵ���̹��
					return;//��������
				}
			}
			botTanks.add(new Bot(boX[index],1,GameType.this,TankType.Bot));//���λ�ô������̹��
			botReadyCount--;//׼���ϴ���������-1
			createBotTimer=0;//�������Լ�ʱ�����¼�ʱ
		}
	}
//	������һ���ؿ�
	private void gotoNextLevel() {
		// TODO Auto-generated method stub
		Thread jump =new JumpPageThead(Level.nextLevel());
		jump.start();
	}
//	���½���ؿ�
	private void gotoPrevisousLevel() {
		// TODO Auto-generated method stub
		Thread jump =new JumpPageThead(Level.previsousLevel());
		jump.start();
	}
	
//	ʣ��̹��������ȥ1
	private void decreaseBot() {
		// TODO Auto-generated method stub
			botSurplusCount--;
	}
	
	
	
	
//	��������
	
	@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
		switch (e.getKeyCode()) {//�жϰ��µİ���ֵ
		case KeyEvent.VK_Y://��������ǡ�Y��
			y_key=true;//Y���±�־λtrue
			break;
		case KeyEvent.VK_W:
			w_key=true;//w���±�־λtrue
			a_key =false;//a���±�־λfalse
			s_key=false;//s���±�־λfalse
			d_key=false;//d���±�־λfalse
			break;
		case KeyEvent.VK_A:
			w_key=false;//w���±�־λtrue
			a_key = true;//a���±�־λfalse
			s_key=false;//s���±�־λfalse
			d_key=false;//d���±�־λfalse
		case KeyEvent.VK_S:
			w_key=false;//w���±�־λtrue
			a_key =false ;//a���±�־λfalse
			s_key=true;//s���±�־λfalse
			d_key=false;//d���±�־λfalse
		case KeyEvent.VK_D:
			w_key=false;//w���±�־λtrue
			a_key =false ;//a���±�־λfalse
			s_key=false;//s���±�־λfalse
			d_key=true;//d���±�־λfalse
		case KeyEvent.VK_HOME:
		case KeyEvent.VK_NUMPAD1:
			 num_key =true;
			 
		case KeyEvent.VK_UP:
			up_key=true;//w���±�־λtrue
			down_key =false ;//a���±�־λfalse
			right_key=false;//s���±�־λfalse
			left_key=false;//d���±�־λfalse
		case KeyEvent.VK_DOWN:
			up_key=false;//w���±�־λtrue
			down_key =true ;//a���±�־λfalse
			right_key=false;//s���±�־λfalse
			left_key=false;//d���±�־λfalse
		case KeyEvent.VK_RIGHT:
			up_key=false;//w���±�־λtrue
			down_key =false ;//a���±�־λfalse
			right_key=true;//s���±�־λfalse
			left_key=false;//d���±�־λfalse
		case KeyEvent.VK_LEFT:
			up_key=false;//w���±�־λtrue
			down_key =false ;//a���±�־λfalse
			right_key=false;//s���±�־λfalse
			left_key=true;//d���±�־λfalse
		default:
			break;
		}	
		}
//	���ݰ���״̬����̹��ִ����Ӧ����
	private void paintTankActoin(){
		if(y_key){
			play1.attack();
			
		}if (w_key) {
			play1.upward();
			
		}if (d_key) {
			play1.rightward();
			
		}if (a_key) {
			play1.leftward();
			
		}if(s_key){
			play1.downward();
			
		}
		if (gameType==GameType.Two_Player) {
			if(num_key)
				play2.attack();
			
		}if (up_key) {
			play2.upward();
			
		}if (right_key) {
			play2.rightward();
			
		}if (left_key) {
			play2.leftward();
			
		}if(down_key){
			play2.downward();
			
		}
	}
	
	
//	����̧��
	
	@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		case KeyEvent.VK_Y:
			y_key= false;
			break;
		case KeyEvent.VK_W:
			w_key=false;
			break;
		case KeyEvent.VK_A:
			a_key=false;
			break;
		case KeyEvent.VK_S:
			s_key=false;
			break;
		case KeyEvent.VK_D:
			d_key=false;
			break;
		case KeyEvent.VK_HOME:
		case KeyEvent.VK_NUMPAD1:
			num_key=false;
			break;
		case KeyEvent.VK_UP:
			up_key=false;
			break;
		case KeyEvent.VK_DOWN:
			down_key=false;
			break;
		case KeyEvent.VK_LEFT:
			left_key=false;
			break;
		case KeyEvent.VK_RIGHT:
			right_key=false;
			break;
		default:
			break;
		}	
		}

//	���ӵ�����������ӵ�
	private void addBullet(Bullet b) {
		Bullet.add(b);
		// TODO Auto-generated method stub

	}
//	��ȡ����ǽ�鼯��
	public List<Wall> getWalls() {
		return walls;
	}
	

	//	��ȡ���ض���
	public Base getBase() {
		return base;
	}
//	��ȡ����̹�˼���
	public List<Tank> getTanks() {
		return allTanks;
	}
	
//	��ת�Ĺؿ�
	public  JumpPageThead(int level) {
		// TODO Auto-generated method stub
		this.level=level;
	}
//	�߳�������
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(1000);//1���
			frame.sePanel(new LevelPanel(level,frame,gameType));//��������ת��ָ���ؿ�
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	
}
