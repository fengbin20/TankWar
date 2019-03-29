package com.modle.wall;

import java.awt.Rectangle;

import com.modle.VisbleImage;

public abstract class Wall extends VisbleImage {
	
	private boolean alive=true;//墙块是否存活
	
	public Wall(int x,int y,String url) {
		super(x,y,url);//调用父类构造方法
	}

	
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	
	//重写判断方法，如果两个模块的坐标相同，则认为两个墙块同一个墙块
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
