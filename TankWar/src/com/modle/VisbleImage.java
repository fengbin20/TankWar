package com.modle;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class VisbleImage {

		public int x;//图像横坐标
		public int y;//图像纵坐标
		int width;//图像宽
		int height;//图像高
		BufferedImage image;//图像对象

		
		//构造方法
		public VisbleImage(int x,int y,int width,int height){
			this.x=x;
			this.y=y;
			this.width=width;
			this.height=height;
			image= new BufferedImage(width, height,BufferedImage.TYPE_INT_BGR );
			
			
		}
		
		//构造方法
		public VisbleImage(int x,int y,String url){
			this.x=x;
			this.y=y;
			try {
				image=ImageIO.read(new File(url));//获取此路径的图片对象
				this.width=image.getWidth();
				this.height=image.getHeight();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//获取图片  
		public BufferedImage getImage(){
			return image;
		}
//		设置图片 所显示的图片
		public void setImage(BufferedImage image) {
			this.image=image;
		}
		
//		设置图片
		public void setImage(String url) {
			try {
				this.image= ImageIO.read(new File(url));//读取指定位置的图片
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		hit判断是否发生了碰撞  相交trui 否者false  目标图片
		public boolean hit(VisbleImage v) {
			return hit(v.getBounds());
		}
		
		/**目标的边界 是否发生碰撞
		 * @param Rectangle 矩形边界类是否发生碰撞
		 * @return
		 */
		public boolean hit(Rectangle r){//矩形空间类Rectangle
			if(r==null){
				return false;
			}else{
				return getBounds().intersects(r);//返回两者的边界是否发生了相交
			}
		}
		public Rectangle getBounds(){//Rectangle 矩形的边界对象
			return new Rectangle(x, y, width, height);//创建一个坐标为xy的位置宽高为wh的矩形边界对象并返回
			
		}
		public int getWidth(){
			return width;
		}
		public void setWidth(int width) {
			this.width=width;
		}
		public int getHeight() {
			return height;
			
		}
		public void setHeight(int height) {
			this.height=height;
			
		}
}
