package com.modle;

import java.awt.Graphics2D;

import com.frame.GamePanel;
import com.util.ImageUtil;

public class Boom extends VisbleImage{

	private int timer =0;//计时器
	private int fresh=GamePanel.FRESH;//刷新时间
	private boolean alive= true;//是否存活
	
	public Boom(int x, int y) {
		super(x, y, ImageUtil.BOOM_IMAGE_URL);//调用父类的构造方法，使用默认爆炸效果
		// TODO Auto-generated constructor stub
	}	
	/**
	 * @param 展示爆炸图片，此图片只显示0.5秒
	 */
	private void show(Graphics2D g2) {
		// TODO Auto-generated method stub
		if(timer>=500){//当计时器记录0.5秒
			alive=false;//爆炸效果失效
		}else{
			g2.drawImage(getImage(), x, y, null);//绘制爆炸效果
			timer+=fresh;//计时器按照刷新时间递增
		}
	}
	
	
	/**爆炸图片是否有效
	 * @return
	 */
	public boolean isAlive() {
		return alive;
	}
}
