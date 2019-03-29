package com.modle;

import java.awt.Graphics2D;

import com.frame.GamePanel;
import com.util.ImageUtil;

public class Boom extends VisbleImage{

	private int timer =0;//��ʱ��
	private int fresh=GamePanel.FRESH;//ˢ��ʱ��
	private boolean alive= true;//�Ƿ���
	
	public Boom(int x, int y) {
		super(x, y, ImageUtil.BOOM_IMAGE_URL);//���ø���Ĺ��췽����ʹ��Ĭ�ϱ�ըЧ��
		// TODO Auto-generated constructor stub
	}	
	/**
	 * @param չʾ��ըͼƬ����ͼƬֻ��ʾ0.5��
	 */
	private void show(Graphics2D g2) {
		// TODO Auto-generated method stub
		if(timer>=500){//����ʱ����¼0.5��
			alive=false;//��ըЧ��ʧЧ
		}else{
			g2.drawImage(getImage(), x, y, null);//���Ʊ�ըЧ��
			timer+=fresh;//��ʱ������ˢ��ʱ�����
		}
	}
	
	
	/**��ըͼƬ�Ƿ���Ч
	 * @return
	 */
	public boolean isAlive() {
		return alive;
	}
}
