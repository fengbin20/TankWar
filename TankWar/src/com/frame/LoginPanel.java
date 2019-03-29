package com.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.type.GameType;
import com.util.ImageUtil;

public class LoginPanel extends JPanel implements KeyListener{
	
	private MainPanel farme;//主窗体
	private GameType type;//游戏模式
	private Image backgroud;//背景图片
	private Image tank;//坦克图片
	private int y1=370,y2=430;//坦克可选择的两个y坐标
	private int tankY=y1;//坦克图标Y坐标

	public LoginPanel(MainPanel mainPanel) {
		// TODO Auto-generated constructor stub
		this.farme=farme;
		addListener();//添加组件监听
			try {
				backgroud=ImageIO.read(new File(ImageUtil.LOGIN_IMAGE_URL));//读取背景图片
				tank=ImageIO.read(new File(ImageUtil.PLAYER1_UP_IMAGE_URL));//选择小坦克的图标
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(backgroud, 0, 0,getWidth(),getHeight(), this);//绘制背景图片
		Font font=new Font("黑体", Font.BOLD, 35);
		g.setFont(font);//使用字体
		g.setColor(Color.WHITE);//使用白色
		g.drawString("1 PLAYER", 350, 400);
		g.drawString("2 PLAYER", 350, 460);
		g.drawImage(tank, 280, tankY, this);//绘制坦克图标
	}
	
	/**
	 * 跳转关卡面板
	 */
	private void gotoLevelPanel() {
		// TODO Auto-generated method stub
		farme.removeKeyListener(this);//删除主窗体监听
		farme.setPanel(new LevelPanel(Level.nextLevel(),frame,tyPe));
	}
	
	
	/**
	 * 添件监听组件
	 */
	private void addListener() {
		// TODO Auto-generated method stub
		farme.addKeyListener(this);//主窗体载入键盘监听，本类已实现KeyListener接口
		
	}

	/* (non-Javadoc)按键按下状态
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code=e.getKeyCode();//获取按下的按键值
		switch (code) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			if (tankY==y1) {
				tankY=y2;
				
			}else {
				tankY=y1;
			}
			repaint();//重绘这个
			break;
		case KeyEvent.VK_NUMPAD1:
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_Y:
			if (tankY==y1) {
				type=GameType.One_Player;//游戏模式为单人模式
			} else {
				type=GameType.Two_Player;
			}
		default:
			break;
		}
	}
	
	/* (non-Javadoc)抬起按键
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)进入某些按键事件
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
