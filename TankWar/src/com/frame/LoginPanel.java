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
	
	private MainPanel farme;//������
	private GameType type;//��Ϸģʽ
	private Image backgroud;//����ͼƬ
	private Image tank;//̹��ͼƬ
	private int y1=370,y2=430;//̹�˿�ѡ�������y����
	private int tankY=y1;//̹��ͼ��Y����

	public LoginPanel(MainPanel mainPanel) {
		// TODO Auto-generated constructor stub
		this.farme=farme;
		addListener();//����������
			try {
				backgroud=ImageIO.read(new File(ImageUtil.LOGIN_IMAGE_URL));//��ȡ����ͼƬ
				tank=ImageIO.read(new File(ImageUtil.PLAYER1_UP_IMAGE_URL));//ѡ��С̹�˵�ͼ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(backgroud, 0, 0,getWidth(),getHeight(), this);//���Ʊ���ͼƬ
		Font font=new Font("����", Font.BOLD, 35);
		g.setFont(font);//ʹ������
		g.setColor(Color.WHITE);//ʹ�ð�ɫ
		g.drawString("1 PLAYER", 350, 400);
		g.drawString("2 PLAYER", 350, 460);
		g.drawImage(tank, 280, tankY, this);//����̹��ͼ��
	}
	
	/**
	 * ��ת�ؿ����
	 */
	private void gotoLevelPanel() {
		// TODO Auto-generated method stub
		farme.removeKeyListener(this);//ɾ�����������
		farme.setPanel(new LevelPanel(Level.nextLevel(),frame,tyPe));
	}
	
	
	/**
	 * ����������
	 */
	private void addListener() {
		// TODO Auto-generated method stub
		farme.addKeyListener(this);//������������̼�����������ʵ��KeyListener�ӿ�
		
	}

	/* (non-Javadoc)��������״̬
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code=e.getKeyCode();//��ȡ���µİ���ֵ
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
			repaint();//�ػ����
			break;
		case KeyEvent.VK_NUMPAD1:
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_Y:
			if (tankY==y1) {
				type=GameType.One_Player;//��ϷģʽΪ����ģʽ
			} else {
				type=GameType.Two_Player;
			}
		default:
			break;
		}
	}
	
	/* (non-Javadoc)̧�𰴼�
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)����ĳЩ�����¼�
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
