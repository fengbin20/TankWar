package com.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.type.GameType;

public class LevelPanel extends JPanel {
	private int level;//�ؿ�ֵ
	private GameType type;//��Ϸģʽ
	private MainPanel frame;//������
	private String levelStr;//���������˸
	private String ready="";//׼����ʾ
	
	public LevelPanel(int level,MainPanel fram,GameType type) {
		// TODO Auto-generated constructor stub
		this.frame=fram;
		this.level=level;
		this.type=type;
		levelStr="level"+level;//��ʼ���ؿ��ַ���
		Thread t =new levelPanelThread();//�����ؿ���嶯���߳�
		t.start();//�����߳�
		
	}
	/* (non-Javadoc)��д�˻�ͼ
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());//���һ�������������İ�ɫ����
		g.setFont(new Font("Consolas", Font.BOLD, 50));
		g.setColor(Color.black);
		g.drawString(levelStr, 260, 300);
		g.setColor(Color.RED);
		g.drawString(ready, 270, 400);
		}
	
	/**
	 *��ת��Ϸ��� 
	 */
	private void gotoGamePanel() {
		// TODO Auto-generated method stub
		frame.setPanel(new GamePanel(frame, level, type));//��������ת���˹ؿ���Ϸ���
		
	}
	
	
	/**�ش����ؿ���嶯���߳�
	 * @author Administrator
	 *
	 */
	private class  levelPanelThread extends Thread{
		// TODO Auto-generated method stub
		@Override
		public void run() {
			// TODO Auto-generated method stub
			for(int i=0;i<6;i++){//ѭ��6��
				if(i%2==0){//���ѭ��������ż��
					levelStr="level"+level;//�ؿ��ַ���������ʾ
					}else{
						levelStr="";//�ؿ��ַ�������ʾ��������
					}
				if (i==4) {//���ѭ����������
					ready="Ready!";//׼����ʾ��ʾ����
				}
				repaint();//�ػ����
				try {
					Thread.sleep(500);//����0.5
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				gotoGamePanel();//��ת����Ϸ��� 
		}
	}

}
