package com.modle.wall;

import java.awt.Rectangle;

import com.modle.VisbleImage;

public abstract class Wall extends VisbleImage {
	
	private boolean alive=true;//ǽ���Ƿ���
	
	public Wall(int x,int y,String url) {
		super(x,y,url);//���ø��๹�췽��
	}

	
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	
	//��д�жϷ������������ģ���������ͬ������Ϊ����ǽ��ͬһ��ǽ��
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Wall){
			Wall w=(Wall) obj;
			if(w.x==x && w.y==y){
				return true;
			}
		}
		return super.equals(obj);
	}
	
	
}
