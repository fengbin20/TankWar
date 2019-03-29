package com.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.type.GameType;

public class LevelPanel extends JPanel {
	private int level;//关卡值
	private GameType type;//游戏模式
	private MainPanel frame;//主窗体
	private String levelStr;//面板中央闪烁
	private String ready="";//准备提示
	
	public LevelPanel(int level,MainPanel fram,GameType type) {
		// TODO Auto-generated constructor stub
		this.frame=fram;
		this.level=level;
		this.type=type;
		levelStr="level"+level;//初始化关卡字符串
		Thread t =new levelPanelThread();//创建关卡面板动画线程
		t.start();//开启线程
		
	}
	/* (non-Javadoc)重写了绘图
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());//填充一个覆盖整个面板的白色矩形
		g.setFont(new Font("Consolas", Font.BOLD, 50));
		g.setColor(Color.black);
		g.drawString(levelStr, 260, 300);
		g.setColor(Color.RED);
		g.drawString(ready, 270, 400);
		}
	
	/**
	 *跳转游戏面板 
	 */
	private void gotoGamePanel() {
		// TODO Auto-generated method stub
		frame.setPanel(new GamePanel(frame, level, type));//主窗体跳转到此关卡游戏面板
		
	}
	
	
	/**重创建关卡面板动画线程
	 * @author Administrator
	 *
	 */
	private class  levelPanelThread extends Thread{
		// TODO Auto-generated method stub
		@Override
		public void run() {
			// TODO Auto-generated method stub
			for(int i=0;i<6;i++){//循环6次
				if(i%2==0){//如果循环变量是偶数
					levelStr="level"+level;//关卡字符串正常显示
					}else{
						levelStr="";//关卡字符串不显示任务内容
					}
				if (i==4) {//如果循环变量等于
					ready="Ready!";//准备提示显示文字
				}
				repaint();//重绘组件
				try {
					Thread.sleep(500);//休眠0.5
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				gotoGamePanel();//跳转到游戏面板 
		}
	}

}
