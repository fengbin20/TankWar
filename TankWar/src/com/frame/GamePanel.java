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

public class GamePanel extends JPanel implements KeyListener{//keylistener 键盘事件监听
	public static final int FRESH=20;
	private BufferedImage image;//面板中显示的主图片
	private Graphics2D g2;//图片的绘图对象
	private MainPanel frame;//主窗体
	private GameType gameType;//游戏模式
	private Tank play1,play2;//玩家1，玩家2
	private boolean y_key,s_key,w_key,a_key,d_key,up_key,down_key,left_key,right_key,num_key;//按下按键的标志
	private int level;//关卡值
	private List<Bullet> bullets;//所有子弹集合
	private volatile List<Tank> allTanks;//所有坦克集合
	private List<Tank> botTanks;//所有敌方坦克集合
	private final int botCount =20;//电脑坦克总数
	private int botReadyCount= botCount;//准备出场的电脑坦克总数
	private int botSurplusCount= botCount;//电脑坦克的剩余数量
	private void botMaxInMap= 6;//场上最大坦克数量
	private int botX[]={10,367,754};//出生位置坐标
	private List<Tank> playerTanks;//玩家坦克集合
	private volatile boolean finish= false;//游戏是否结束
	private Base base;//基地
	private List<Wall> walls;//所有墙块
	private List<Boom> boomImage;//坦克阵亡后的爆炸效果集合
	private Random r =new Random();//随机数对象
	private int createBotTimer=0;//生产电脑计时器
	private Tank survivor;//玩家 幸存者，用于绘制最后一个爆炸效果
	
	
//	构造方法
	
	public GamePanel(MainPanel frame,int level,GameType gameType) {
		this.frame=frame;
		this.level=level;
		this.gameType=gameType;
		setBackground(Color.WHITE);//设置背景
		init();//初始化组件
		Thread t=new FreshThead();//创建游戏刷新帧
		t.start();//启动线程
		addListener();//开启监听
		// TODO Auto-generated constructor stub
	}




private void init() {
	bullets =new ArrayList<Bullet>();//实例化子弹合集
	allTanks =new ArrayList<>();//实例化所有坦克合集
	walls= new ArrayList<>();//实例化所有墙块的集合
	boomImage= new ArrayList<>();//实例化所有爆炸效果的集合
	
	image= new BufferedImage(794, 572, BufferedImage.TYPE_INT_BGR);//实例化主图片，采用面板实际大小
	g2= image.createGraphics();//获取主图片绘图对象
	
	playerTanks=new ArrayList<>();//实例化坦克集合
	play1=new Tank(278, 537, ImageUtil.PLAYER1_UP_IMAGE_URL,this, TankType.Palyer1);
	if (gemeType==GameType.Two_Player) {//判断游戏模式-如果是双人模式
		play2= new Tank(448, 537, ImageUtil.PLAYER2_UP_IMAGE_URL, this, TankType.Palyer2);
		playerTanks.add(play2);//	玩家坦克集合添加玩家2
				
	}
	playerTanks.add(play1);//	玩家坦克集合添加玩家1
	
	botTanks=new Vector<>();//实例化坦克集合
	botTanks.add(new Bot(botX[0],1,this,TankType.Bot));//在第一个位置添加电脑
	botTanks.add(new Bot(botX[1],1,this,TankType.Bot));//在第二个位置添加电脑
	botTanks.add(new Bot(botX[2],1,this,TankType.Bot));//在第三个位置添加电脑
	botReadyCount -=3;//准备出场的坦克总数减去初始化数量
	allTanks.addAll(playerTanks);//所有坦克集合添加玩家坦克集合
	
	allTanks.addAll(botTanks);//所有坦克集合添加电脑坦克集合
	base = new Base(367,532);//实例基地
	initWalls();//初始化地图中的墙块
	
	
	// TODO Auto-generated method stub
	
}
	




//	组件监听


private void addListener() {
	frame.addKeyListener(this);//主窗体载入键盘监听，本类已实现Keylistener接口
	// TODO Auto-generated method stub
	
}

//初始化地图墙块
	
private void initWalls() {
	Map map=Map.getMap(level);//获取当前关卡的地图对象
	walls.addAll(map.getWalls());
	walls.add(base);
	// TODO Auto-generated method stub
	
}


	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		paintTankAction();//执行坦克动作
		CreateBot();//循环创建电脑坦克	
		paintImage();//绘制主图片
		g.drawImage(image, 0, 0, this);//将主图片绘制到面板上
		
	}
	





//	绘制主图片
//	绘制顺序为页面图片展示层
	
	private void paintImage() {
		// TODO Auto-generated method stub
		g2.setColor(Color.WHITE);//使用白色
		g2.fillRect(0, 0, image.getWidth(), image.getHeight());//填充一个覆盖整个图片的白色矩形
		paintBoom();//绘制爆炸效果
		paintBotCount();//在屏幕顶部绘制剩余坦克数量
		paintBotTanks();//绘制电脑坦克
		paintPlayerTanks();//绘制玩家坦克
		allTanks.addAll(playerTanks);//坦克集合添加玩家坦克集合
		allTanks.addAll(botTanks);
		panitWall();//绘制墙块
		panitBullets();//绘制子弹
		
		if (botSurplusCount==0) {//如果所有电脑都被消灭
			stopThread();//结束游戏帧刷新线程
			painBotCount();//在屏幕顶部绘制剩余坦克数量
			g2.setFont(new Font("楷体", Font.BOLD, 50));//设置绘图字体
			g2.setColor(Color.GREEN);//设置颜色
			g2.drawString("WIN", 250, 400);//指定坐标位置绘制文字
			gotoNextLevel();//进入下一关
		}
		if (gameType==GameType.One_Player) {
			if(!play1.isAlive()){//如果玩家阵亡
				stopThread();//结束游戏帧的刷新
				boomImage.add(new Boom(play1.x,play2.y));//添加玩家1爆炸效果
				panitBoom();//绘制爆炸效果
				panitGameOver();//在屏幕中间绘制 game over
				gotoPrevisousLevel();//重新进入本关卡
			}
			
		}else   {
			if (play1.isAlive()&& !play2.isAlive()) {//如果玩家1是幸存
				survivor =play1;//幸存是玩家1
				}else if (!play1.isAlive()&&play2.isAlive()) {//如果玩家2是幸存
					survivor=play2;//幸存是玩家2
					
				}else if (!(play1.isAlive()||play2.isAlive())) {//如果两个玩家坦克全部灭亡
					stopThread();//结束游戏帧刷新线程
					boomImage.add(new Boom(survivor.x,survivor.y));//添加幸存者爆炸效果
					panitBoom();//绘制爆照效果
					panitGameOver();//在屏幕中间绘制 game over
					gotoPrevisousLevel();//重新进入本关卡
					
				}
			
		}
		
		if (!base.isAlive()) {
			stopThread();//结束游戏帧刷新线程
			panitGameOver();//在屏幕中间绘制 game over
			base.setImage(ImageUtil.BASEBOO_IMAGE_URL);
			gotoPrevisousLevel();//重新进入本关卡
		}
		g2.drawImage(base.getImage(), base.x, base.y, this);//绘制基地
	}	
	
//	在屏幕顶部绘制剩余坦克数量
	private void paintBotCount() {
		// TODO Auto-generated method stub
		g2.setColor(Color.BLUE);//使用蓝色
		g2.drawString("敌方坦克剩余"+botSurplusCount, 337, 15);//在指定位置坐标绘制字符串
	}
	
//	屏幕中央绘制game over
	private void paintGameOver() {
		// TODO Auto-generated method stub
		g2.setFont(new Font("楷体", Font.BOLD, 50));//设置绘图字体和大小
		g2.setColor(Color.RED);//颜色为红色
		g2.drawString("GAME OVER", 250, 400);//指定位置绘制文字
	}
//	绘制爆炸效果
	private void panitBoom() {
		// TODO Auto-generated method stub
		for(int i;i<boomImage.get();i++){//循环遍历爆炸效果集合
			Boom boom=boomImage.get(i);//获取爆炸对象
			if (boom.isAlive()) {//如果爆炸效果有效
				boom.show(g2);//展示爆炸效果
				
			}else{
				boomImage.remove(i);//在集合中删除此爆炸
				i--;//循环变量-1，保证下次循环的i的值不会变成i+1，一遍有效遍历集合，且防止下标越界
			}
			
			
		}
	}
	
//	绘制墙块
	private void panitWalls() {
		// TODO Auto-generated method stub
		for(int i=0;i<walls.size();i++){
			Wall w=walls.get(i);//获取墙块对象
			if (w.isAlive()) {//如果墙块存在
				g2.drawImage(w.getImage(), w.x, w.y, this);
				
			}else{//如果墙块无效
				walls.remove(i);
				i--;
			}
		}
	}
	//绘制子弹
	private void panitBullets() {
		// TODO Auto-generated method stub
		for(int i=0;i<bullets.size();i++){//循环遍历子弹集合
			Bullet b=bullest.get(i);//获取子弹对象
			if (b.isAlive()) {//如果子弹有效
				b.move();//子弹移动操作
				b.hitBase();//子弹执行集中基地半段
				b.hitWall();//子弹执行集中墙壁判断
				b.hitTank();//自动执行击中坦克判断
				g2.drawImage(b.getImage(),b.x,b.y,this);//绘制子弹
			}else{//如果子弹无效
				bullets.remove(i);//在集合中删除此子弹
				i--;//循环变量-1，保证下次循环I的值不会变成i+1，以便有效遍历集合，防止下标越界
			}
			
		}
	}
//	绘制电脑坦克
	
	private void panitBotTanks() {
		// TODO Auto-generated method stub
		for(int i=0;i<botTanks.size;i++){//循环遍历电脑坦克
			Bot t=botTanks.get(i);//获取电脑坦克对象
			if(t.isAlive()){//如果坦克存活
				t.go();//电脑干咳展开行动
				g2.drawImage(t.getImage(), t.x, t.y, this);//绘制坦克
			}else{//如果坦克阵亡
				botTanks.remove(i);//集中删除此坦克
				i--;//循环变量-1，保证下次循环i的值不会变成i+1，以便有效遍历集合，且防止下标越界
				boomImage.add(new Boom(t.x,t.y));//坦克位置创建爆炸效果
				decreaseBot();//剩余坦克数量-1
			}
			
		}
	}
//	绘制玩家坦克
	private void painplayerTanks() {
		// TODO Auto-generated method stub
		for(int i=0;i<playerTanks.size();i++){//循环遍历玩家坦克
			Tank t=playerTanks.get(i);//获取玩家坦克
			if (t.isAlive()) {//如果坦克存活
				g2.drawImage(t.getImage(), t.x, t.y, this);//绘制坦克
				
			}else{//坦克阵亡
				playerTanks.remove(i);//集合中删除此坦克
				i--;//循环变量-1，保证下次循环i的值不会变成i+1，以便于有效遍历集合，防止下标越界
				boomImage.add(new Boom(t.x,t.y));
			}
		}
	
	
	}
//	结束游戏刷洗
	
	private synchronized void stopThread() {
		// TODO Auto-generated method stub
		frame.removeKeyListener(this);//主窗体删除本类键盘事件监听对象
		finish=true;//游戏停止标志
	}
//	游戏帧刷新线程
	private class FreshThread extends Thread {
		// TODO Auto-generated method stub
		public void run() {//线程主方法
			// TODO Auto-generated method stub
		while(!finish){//如果游戏未停止
			repaint();//执行本类重载方法
			try {
				Thread.sleep(FRESH);//指定时间后重载
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
	}
	
//	添加电脑坦克，如果场上坦克未达到最大值，每4秒之后在三个出生位置随机选择其一，创建电脑坦克
	private void Createbot() {
		// TODO Auto-generated method stub
		createBotTimer+=FRESH;//计时器按照刷新时间递增
		//当场上电脑小于场上最大数时，并且准备上场的坦克数大于0，并且计时器记录已经过去4秒
		if (botTanks.size()<botMaxInMap&&botReadyCount>0&&createBotTimer>=4000) {
			int index=r.nextInt(3);//随机获取0或者1或者2其中一个值
			Rectangle bornRect= new Rectangle(botX[index], 1, 35, 35);//创建坦克随机出生区域
			for(int i=0,lengh=allTanks.size();i<lengh;i++){//循环遍历所有坦克集合
				Tank t=allTanks.get(i);//获取坦克对象
				if(t.isAlive()&&t.hit(bornRect)){//如果厂商存在与随机位置重合并且存活的坦克
					return;//结束方法
				}
			}
			botTanks.add(new Bot(boX[index],1,GameType.this,TankType.Bot));//随机位置创造电脑坦克
			botReadyCount--;//准备上床电脑数量-1
			createBotTimer=0;//产生电脑计时器重新计时
		}
	}
//	进入下一个关卡
	private void gotoNextLevel() {
		// TODO Auto-generated method stub
		Thread jump =new JumpPageThead(Level.nextLevel());
		jump.start();
	}
//	重新进入关卡
	private void gotoPrevisousLevel() {
		// TODO Auto-generated method stub
		Thread jump =new JumpPageThead(Level.previsousLevel());
		jump.start();
	}
	
//	剩余坦克数量减去1
	private void decreaseBot() {
		// TODO Auto-generated method stub
			botSurplusCount--;
	}
	
	
	
	
//	按键按下
	
	@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
		switch (e.getKeyCode()) {//判断按下的按键值
		case KeyEvent.VK_Y://如果按下是“Y”
			y_key=true;//Y按下标志位true
			break;
		case KeyEvent.VK_W:
			w_key=true;//w按下标志位true
			a_key =false;//a按下标志位false
			s_key=false;//s按下标志位false
			d_key=false;//d按下标志位false
			break;
		case KeyEvent.VK_A:
			w_key=false;//w按下标志位true
			a_key = true;//a按下标志位false
			s_key=false;//s按下标志位false
			d_key=false;//d按下标志位false
		case KeyEvent.VK_S:
			w_key=false;//w按下标志位true
			a_key =false ;//a按下标志位false
			s_key=true;//s按下标志位false
			d_key=false;//d按下标志位false
		case KeyEvent.VK_D:
			w_key=false;//w按下标志位true
			a_key =false ;//a按下标志位false
			s_key=false;//s按下标志位false
			d_key=true;//d按下标志位false
		case KeyEvent.VK_HOME:
		case KeyEvent.VK_NUMPAD1:
			 num_key =true;
			 
		case KeyEvent.VK_UP:
			up_key=true;//w按下标志位true
			down_key =false ;//a按下标志位false
			right_key=false;//s按下标志位false
			left_key=false;//d按下标志位false
		case KeyEvent.VK_DOWN:
			up_key=false;//w按下标志位true
			down_key =true ;//a按下标志位false
			right_key=false;//s按下标志位false
			left_key=false;//d按下标志位false
		case KeyEvent.VK_RIGHT:
			up_key=false;//w按下标志位true
			down_key =false ;//a按下标志位false
			right_key=true;//s按下标志位false
			left_key=false;//d按下标志位false
		case KeyEvent.VK_LEFT:
			up_key=false;//w按下标志位true
			down_key =false ;//a按下标志位false
			right_key=false;//s按下标志位false
			left_key=true;//d按下标志位false
		default:
			break;
		}	
		}
//	根据按下状态，让坦克执行相应动作
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
	
	
//	按键抬起
	
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

//	向子弹集合中添加子弹
	private void addBullet(Bullet b) {
		Bullet.add(b);
		// TODO Auto-generated method stub

	}
//	获取所有墙块集合
	public List<Wall> getWalls() {
		return walls;
	}
	

	//	获取基地对象
	public Base getBase() {
		return base;
	}
//	获取所有坦克集合
	public List<Tank> getTanks() {
		return allTanks;
	}
	
//	跳转的关卡
	public  JumpPageThead(int level) {
		// TODO Auto-generated method stub
		this.level=level;
	}
//	线程主方法
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(1000);//1秒后
			frame.sePanel(new LevelPanel(level,frame,gameType));//主窗体跳转到指定关卡
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
